package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DelModerator extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Owner;
	}

	@Override
	public String getCommandText() {
		return "delmoderator";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(Database.delModerator(parameters[0].toLowerCase(), channel.substring(1))) {
			return String.format("Successfully removed %s from the moderator list in %s", parameters[0], channel);
		}
		return String.format("Uanble to remove %s from the moderator list in %s! Please try again.", parameters[0], channel);
	}

}
