package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class SlowClap extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "slowclap";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "%user% claps his hands slowly while walking off into the sunset.".replace("%user%", sender);
	}

}
