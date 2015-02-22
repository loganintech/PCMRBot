package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.Options;

public class ChangeWelcome implements Command {

	@Override
	public String execute(String... parameters) {
		String channel = parameters[0];
		String message = parameters[1];
		Database.setOption(channel, Options.welcomeMessage, message);
		return "The welcome message has been changed to: %message%".replace("%message%", message);
	}

}
