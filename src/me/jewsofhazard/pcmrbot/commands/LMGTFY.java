package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class LMGTFY extends Command implements ICommand {

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
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
