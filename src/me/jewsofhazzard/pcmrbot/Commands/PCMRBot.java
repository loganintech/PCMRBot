package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class PCMRBot implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
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
