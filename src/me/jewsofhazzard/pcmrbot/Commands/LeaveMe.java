package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class LeaveMe implements Command {
	
	private CommandLevel level=CommandLevel.Owner;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters){
		if (!channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			MyBotMain.getBot().partChannel(channel);
				return "I have disconnected from %channel%'s channel.".replace("%channel%", channel);
		} else {
			return "Sorry %user%, I cannot allow you to disconnect me from my home channel.".replace("%user%", sender);
		}
		
		
	}
	
	
}





