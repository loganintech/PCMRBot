package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class LMGTFY extends Command implements ICommand {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "lmgtfy";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		return "http://lmgtfy.com?q=" + parameters[0].replace(' ', '+');
	}

}