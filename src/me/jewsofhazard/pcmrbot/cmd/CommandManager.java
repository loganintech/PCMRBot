package me.jewsofhazard.pcmrbot.cmd;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jewsofhazard.pcmrbot.db.Database;

import org.apache.commons.io.FilenameUtils;

/**
 * 
 * @author Donald10101
 * @author Paramvir Phagura
 */
public class CommandManager {

	private Map<String, Command> cmdMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T extends Command> List<T> loadCommands(Path dirPath)
			throws IOException {
		dirPath = dirPath.normalize().toAbsolutePath();
		if (!Files.exists(dirPath))
			throw new IOException("dirPath does not exist");
		else if (!Files.isDirectory(dirPath))
			throw new IOException("dirPath is not a directory");
		List<Class<T>> cmdClassList = new ArrayList<>();
		try (DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(dirPath)) {
			URLClassLoader cl = new URLClassLoader(new URL[] { dirPath.toUri()
					.toURL() });
			for (Path path : dirStream)
				if (FilenameUtils.getExtension(path.toString()).equals("class"))
					try {
						String fileName = path.toFile().getName();
						fileName = fileName.substring(0, fileName.length()
								- ".class".length());
						Class<?> c = cl.loadClass(fileName);
						if (c.getSuperclass() == Command.class)
							cmdClassList.add((Class<T>) c);
					} catch (Exception checked) {
					}
			cl.close();
		} catch (IOException _IO_ex) {
			throw _IO_ex;
		}
		List<T> addedCmds = new ArrayList<>();
		synchronized (cmdMap) {
			for (Class<T> cmdClass : cmdClassList) {
				try {
					Command cmd = cmdClass.getDeclaredConstructor()
							.newInstance();
					bindCommand(cmd);
					addedCmds.add((T) cmd);
				} catch (Exception ex) {
					/* checked */
				}
			}
		}
		return addedCmds;
	}

	public CommandManager bindCommand(Command cmd) {
		String cmdID = cmd.getCommandText();
		if (!cmdMap.containsKey(cmdID))
			cmdMap.put(cmdID, cmd);
		return this;
	}

	public String parse(String command, String sender, String channel,
			String parameters) {
		Command c = cmdMap.get(command);

		if (c != null && hasAccess(c, sender, channel)) {
			return c.execute(channel, sender, parameters);
		}
		return null;
	}

	private boolean hasAccess(Command c, String sender, String channel) {
		switch (c.getCommandLevel()) {
		case Mod:
			return Database.isMod(sender, channel.substring(1));
		case Owner:
			return sender.equalsIgnoreCase(channel.substring(1));
		case Normal:
			return true;
		}
		return false;
	}

}
