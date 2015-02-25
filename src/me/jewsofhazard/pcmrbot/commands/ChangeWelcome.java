package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.TOptions;

public class ChangeWelcome extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "changewelcome";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String message = parameters[0];
		Database.setWelcomeMessage(channel.substring(1), TOptions.welcomeMessage, message);
		if(!message.equalsIgnoreCase("none")) {
			return "The welcome message has been changed to: %message%".replace("%message%", message);
		}
		return "Welcome messages have been DISABLED! You can re-enable them by using !changewelcome at a later time";
	}

}
