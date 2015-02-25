package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Leave extends Command implements ICommand {
	
	private CLevel level=CLevel.Owner;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "leave";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters){
		if (!channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			MyBotMain.partChannel(channel);
			return "I have disconnected from %channel%'s channel.".replace("%channel%", channel);
		}
		return "Sorry %user%, I cannot allow you to disconnect me from my home channel.".replace("%user%", sender);
		
		
	}
	
	
}





