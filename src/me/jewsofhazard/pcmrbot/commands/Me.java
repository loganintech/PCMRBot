package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class Me extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "me";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "/me %message%".replace("%message%", parameters[0]);
	}

}
