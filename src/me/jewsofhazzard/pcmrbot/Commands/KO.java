package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class KO implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "ko";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("%s knocks out %s.", sender, parameters[0]);
	}

}
