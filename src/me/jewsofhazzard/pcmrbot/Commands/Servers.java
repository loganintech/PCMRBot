package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Servers implements Command{

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
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
