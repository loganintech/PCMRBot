package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class JoinMe implements Command{
	
	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "joinme";
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