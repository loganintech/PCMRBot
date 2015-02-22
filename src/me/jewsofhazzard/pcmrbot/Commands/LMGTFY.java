package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class LMGTFY implements Command {

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
