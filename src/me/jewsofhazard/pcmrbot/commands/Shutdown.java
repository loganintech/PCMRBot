package me.jewsofhazard.pcmrbot.commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.TFileWriter;

public class Shutdown extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
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
				channels.add(s);
				MyBotMain.getBot().sendMessage(s, "I am shutting down, I will automatically rejoin your channel when I restart!");
			}
			TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
			System.exit(0);
		}
		return null;
	}

}
