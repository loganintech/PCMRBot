package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Fight implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("%s puts up his digs in preparation to punch %s.", sender, parameters[0]);
	}

}
