package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;
import me.jewsofhazzard.pcmrbot.util.Options;

public class ChangeWelcome implements Command {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String message = parameters[0];
		Database.setOption(channel, Options.welcomeMessage, message);
		return "The welcome message has been changed to: %message%".replace("%message%", message);
	}

}
