package me.jewsofhazzard.pcmrbot.Commands;

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazzard.pcmrbot.IRCBot;
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
		for (IRCBot bot : MyBotMain.getConnectedChannels().values()) {

			if (!bot.getChannel().equalsIgnoreCase("#pcmrbot")) {
				channels.add(bot.getChannel());
				bot.sendMessage(bot.getChannel(),
						"I am shutting down, I will automatically rejoin your channel when I restart!");
			}

		}
		TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
		System.exit(0);
	}

}
