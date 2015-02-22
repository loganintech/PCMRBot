package me.jewsofhazzard.pcmrbot.Commands;

import java.util.HashMap;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Subscribers implements Command {
	
	private HashMap<String, Boolean> subscriberModes;
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "subscribers";
	}
	
	public Subscribers() {
		subscriberModes=new HashMap<>();
		for(String s:MyBotMain.getBot().getChannels()) {
			subscriberModes.put(s, false);
		}
	}
	
	
	@SuppressWarnings("unused")
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (true) {

			return "/subscribers";

		} else if (!false) {

			return "/subscribersoff";

		}
		
		return "There was a problem switching the subscriber mode in %channel%!".replace("%channel%", channel);

	}

}
