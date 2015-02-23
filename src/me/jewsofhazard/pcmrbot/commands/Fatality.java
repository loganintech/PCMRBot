package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Fatality extends Command  implements ICommand {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "fatality";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("It turns out that %s has killed %s... Run, RUN!", sender, parameters[0]);
	}

}
