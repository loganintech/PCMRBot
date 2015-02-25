package me.jewsofhazard.pcmrbot.commands;

import java.util.HashMap;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Subscribers extends Command implements ICommand {
	
	private HashMap<String, Boolean> subscriberModes;
	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
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
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (!MyBotMain.getBot().getSubscribersMode(channel)) {
			MyBotMain.getBot().setSubMode(channel, true);
			return "/subscribers";
		}
		MyBotMain.getBot().setSubMode(channel, false);
		return "/subscribersoff";
	}
}
