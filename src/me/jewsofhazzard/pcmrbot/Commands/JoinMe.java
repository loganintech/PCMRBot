package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class JoinMe implements Command{
	
	public String execute(String... parameters){
		
		String user=parameters[0];
		if(parameters[1].equalsIgnoreCase(MyBotMain.getBotChannel())){
			MyBotMain.joinChannel("#" + user);
			return "I have joined %user%'s channel.".replace("%user%", user);
		}
		return "Sorry %user%, but I can't join your channel from here! Visit http://twitch.tv/pcmrbot and try again!".replace("%user%", user);
		
	}
}