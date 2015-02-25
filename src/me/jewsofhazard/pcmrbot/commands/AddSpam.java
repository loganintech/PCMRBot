package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class AddSpam extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "addspam";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String word = parameters[0];
		Database.addSpam(channel.substring(1), word);
		return "Added %word% to the spam database!".replace("%word%", word);
	}

}
