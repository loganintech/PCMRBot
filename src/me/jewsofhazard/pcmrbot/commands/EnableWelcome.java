package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class EnableWelcome extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "enablewelcome";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		MyBotMain.getBot().setWelcomeEnabled(channel, true);
		return "Welcome messages have been enabled.";
	}

}
