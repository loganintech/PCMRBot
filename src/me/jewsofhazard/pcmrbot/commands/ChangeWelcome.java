package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.Options;

public class ChangeWelcome extends Command  implements ICommand {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "changewelcome";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String message = parameters[0];
		Database.setOption(channel.substring(1), Options.welcomeMessage, message);
		if(!message.equalsIgnoreCase("none")) {
            return "The welcome message has been changed to: %message%".replace("%message%", message);
        }
        return "Welcome messages have been DISABLED! You can re-enable them by using !changewelcome at a later time";
	}

}
