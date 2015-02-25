package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class Clear extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "clear";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "/clear";
	}

}
