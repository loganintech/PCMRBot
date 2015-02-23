package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class KO extends Command implements ICommand {

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
