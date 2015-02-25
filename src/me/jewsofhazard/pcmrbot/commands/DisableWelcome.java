package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DisableWelcome extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "disablewelcome";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		MyBotMain.getBot().setWelcomeEnabled(channel, false);
		return "Welcome messages have been disabled.";
	}

}
