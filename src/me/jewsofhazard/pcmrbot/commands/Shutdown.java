package me.jewsofhazard.pcmrbot.commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.TFileWriter;

public class Shutdown extends Command implements ICommand {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "shutdown";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if(channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			ArrayList<String> channels = new ArrayList<>();
			for (String s : MyBotMain.getBot().getChannels()) {
				if(!s.equalsIgnoreCase(MyBotMain.getBotChannel())) {
					channels.add(s);
				}
			}
			new Broadcast().execute(channel, sender, "I am shutting down, I will automatically rejoin your channel when I restart!");
			TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
			System.exit(0);
		}
		return null;
	}

}
