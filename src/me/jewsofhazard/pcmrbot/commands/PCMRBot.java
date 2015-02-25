package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class PCMRBot extends Command implements ICommand {

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "pcmrbot";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "I was made by J3wsOfHazard, Donald10101, and Angablade. Source at: http://github.com/jwolff52/PCMRBot";
	}

}
