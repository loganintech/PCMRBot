package me.jewsofhazzard.pcmrbot.Commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.TFileWriter;

public class Shutdown implements Command {

	@Override
	public String execute(String...parameters) {
		ArrayList<String> channels = new ArrayList<>();
		for (String s : MyBotMain.getBot().getChannels()) {
			if(!s.equalsIgnoreCase("#pcmrbot")) {
				channels.add(s);
			}
		}
		new Broadcast().execute("I am shutting down, I will automatically rejoin your channel when I restart!");
		TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
		System.exit(0);
		return null;
	}

}
