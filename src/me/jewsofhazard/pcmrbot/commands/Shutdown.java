package me.jewsofhazard.pcmrbot.commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.TFileWriter;

public class Shutdown extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "shutdown";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (channel.equalsIgnoreCase(Main.getBotChannel())) {
			ArrayList<String> channels = new ArrayList<>();
			for (String s : Main.getBot().getChannels()) {
				if (!s.equalsIgnoreCase(Main.getBotChannel())) {
					channels.add(s);
				}
			}
			Main.getBot()
					.sendMessage(
							Main.getBotChannel(),
							new Broadcast().execute(Main.getBotChannel(), Main
									.getBotChannel().substring(1),
									"I am shutting down, I will automatically rejoin your channel when I restart!"));
			TFileWriter.overWriteFile(new File("connectedChannels.txt"),
					channels);
			System.exit(0);
		}
		return null;
	}

}
