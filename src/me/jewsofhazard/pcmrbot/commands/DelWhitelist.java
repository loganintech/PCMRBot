package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DelWhitelist extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "delwhitelist";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String target = parameters[0];
		if(Database.delWhitelist(channel.substring(1), target)) {
			return "Deleted %user% from the whitelist".replace("%user%", target);
		}
		return "That user is not on the whitelist";
	}

}
