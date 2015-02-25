package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class AddCommand extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "addcom";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String[] params = parameters[0].split("[|]");
		try {
			params[0].substring(0, params[0].indexOf('|'));
		} catch (StringIndexOutOfBoundsException e) {}
		Database.addAutoReply(channel.substring(1), params[0], params[1]);
		return String.format("Added command to the database. When a user says %s in chat I will say %s", params[0], params[1]);
	}
}
