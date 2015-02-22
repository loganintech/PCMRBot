package me.jewsofhazzard.pcmrbot.Commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;
import me.jewsofhazzard.pcmrbot.util.TFileWriter;

public class Shutdown implements Command {

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
