package me.jewsofhazard.pcmrbot.commands;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class CommandParser {
	private static HashMap<String, Command> commands;
	private static final Logger logger=Logger.getLogger(CommandParser.class+"");
	
	public static void init() {
		commands=new HashMap<>();
		Reflections r = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
		
		// Find all commands
		Set<Class<? extends Command>> commandClassList = r.getSubTypesOf(Command.class);
		
		// Add command instances to the commands hash map
		for(Class<? extends Command> cClass: commandClassList){
			
			try {
				
				logger.info(cClass.getName());
				Command c = cClass.newInstance();
				commands.put(c.getCommandText(), c);
				
			} catch (Exception e) {
				logger.log(Level.WARNING, "An error occurred initializing a command", e);
			}

		}
	}
	
	public static String parse(String command, String sender, String channel, String parameters) {
		logger.info(command);
		logger.info(parameters);
		
		Command c=commands.get(command);
		
		if(c != null && hasAccess(c, sender, channel)) {
			logger.info("NOT NULL");
			return c.execute(channel, sender, parameters);
		}
		logger.info("NULL");
		return null;
	}
	
	private static boolean hasAccess(ICommand c, String sender, String channel) {
		switch(c.getCommandLevel()) {
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
