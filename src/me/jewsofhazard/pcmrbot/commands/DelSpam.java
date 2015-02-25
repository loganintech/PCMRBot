package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DelSpam extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "delspam";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String word = parameters[0];
		Database.delSpam(channel.substring(1), word);
		return "Removed %word% from the spam database!".replace("%word%", word);
	}

}
