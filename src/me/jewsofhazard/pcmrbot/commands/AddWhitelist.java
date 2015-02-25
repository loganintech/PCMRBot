package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class AddWhitelist extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "addwhitelist";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String target = parameters[0];
		if (Database.addToWhiteList(channel.substring(1), target)) {
			return "Added %user% to the whitelist".replace("%user%", target);
		}
		return "That user is already on the whitelist!";
	}

}
