package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class Fatality extends Command  implements ICommand {

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
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
