package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DelCommand extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "delcom";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String[] params = parameters[0].split("[|]");
		try {
			params[0].substring(0, params[0].indexOf('|'));
		} catch (StringIndexOutOfBoundsException e) {}
		Database.delAutoReply(channel.substring(1), params[0]);
		return "Removed command from the database.";
	}

}
