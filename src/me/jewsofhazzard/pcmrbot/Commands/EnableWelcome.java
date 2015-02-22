package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class EnableWelcome implements Command {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
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
