package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Punch extends Command implements ICommand {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "punch";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("%s punches %s.", sender, parameters[0]);
	}

}
