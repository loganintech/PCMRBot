package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Join extends Command  implements ICommand{
	
	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "join";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters){
		if(channel.equalsIgnoreCase(MyBotMain.getBotChannel())){
			MyBotMain.joinChannel("#" + sender);
			return "I have joined %user%'s channel.".replace("%user%", sender);
		}
		return "Sorry %user%, but I can't join your channel from here! Visit http://twitch.tv/pcmrbot and try again!".replace("%user%", sender);
		
	}
}