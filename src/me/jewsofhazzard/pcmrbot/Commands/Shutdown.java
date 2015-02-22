package me.jewsofhazzard.pcmrbot.Commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.TFileWriter;

public class Shutdown implements Command {
	
	@Override
	public String getReply() {
		return null;
	}

	@Override
	public void execute() {
		ArrayList<String> channels = new ArrayList<>();
		MyBotMain.getBot().broadcast("I am shutting down, I will automatically rejoin your channel when I restart!");
		TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
		System.exit(0);
	}

}
