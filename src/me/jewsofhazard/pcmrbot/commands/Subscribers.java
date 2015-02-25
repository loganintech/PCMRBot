package me.jewsofhazard.pcmrbot.commands;

import java.util.HashMap;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Subscribers extends Command implements ICommand {
	
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
