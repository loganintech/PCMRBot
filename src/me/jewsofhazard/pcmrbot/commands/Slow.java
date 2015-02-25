package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Slow extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "slow";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (!MyBotMain.getBot().getSlowMode(channel)) {
			MyBotMain.getBot().setSlowMode(channel, true);
			try {
				int time = Integer.valueOf(parameters[0]);
				return "/slow "+time;
			} catch (NumberFormatException e) {
				return "/slow";
			}
		} else {
			MyBotMain.getBot().setSlowMode(channel, false);
			return "/slowoff";
		}
	}

}
