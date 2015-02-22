package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class LeaveMe implements Command {
	
	
	public String execute(String...parameters){
		
		String channel = parameters[0];
		
		if (channel != "#pcmrbot") {
			MyBotMain.getBot().partChannel(channel);
				return "I have disconnected from %channel%'s channel.".replace("%channel%", channel);
		} else {
			return "Sorry %user%, I cannot allow you to disconnect me from my home channel.".replace("%user%", channel.substring(1));
		}
		
		
	}
	
	
}





