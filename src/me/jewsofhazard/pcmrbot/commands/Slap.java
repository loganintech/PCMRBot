package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Slap extends Command implements ICommand {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "slap";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "/me slaps %target% with a raw fish".replace("%target%", parameters[0]);
	}

}
