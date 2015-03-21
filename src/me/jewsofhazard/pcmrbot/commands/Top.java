package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Top extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "top";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(parameters.length == 0) {
			return Database.topPlayers(5, channel.substring(1));
		}
		int amnt = -1;
		try {
			amnt = Double.valueOf(parameters[0]).intValue();
		} catch (NumberFormatException e) {
			return parameters[0] + " isn't a number, silly!";
		}
		if (amnt > 15) {
			return "Hey! No spamming! (Number cannot be greater than 15)";
		}
		if (amnt <= 0) {
			return "Hey! Let's not break reality here! (Number cannot be less than 1)";
		}
		return Database.topPlayers(amnt, channel.substring(1));
	}

}
