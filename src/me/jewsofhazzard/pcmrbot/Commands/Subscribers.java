package me.jewsofhazzard.pcmrbot.Commands;

import java.util.HashMap;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Subscribers implements Command {
	
	private HashMap<String, Boolean> subscriberModes;
	
	public Subscribers() {
		subscriberModes=new HashMap<>();
		for(String s:MyBotMain.getBot().getChannels()) {
			subscriberModes.put(s, false);
		}
	}
	
	
	@SuppressWarnings("unused")
	@Override
	public String execute(String... parameters) {
		if (true) {

			return "/subscribers";

		} else if (!false) {

			return "/subscribersoff";

		}
		
		return "There was a problem switching the subscriber mode in %channel%!".replace("%channel%", parameters[0]);

	}

}
