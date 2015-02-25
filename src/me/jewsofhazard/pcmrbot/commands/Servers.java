package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class Servers extends Command implements ICommand{

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "servers";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "http://www.reddit.com/r/pcmasterrace/wiki/servers";
	}

}
