package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;

public class AddModerator implements Command {


	/**
	 * Add's a moderator to the channel's Mod's table in the database if they are not already in it.
	 * 
	 * @return a formatted message with the results of the method
	 */
	public String execute(String...parameters){
		
		String moderator=parameters[0];
		String channel=parameters[1];
		if (Database.isMod(moderator, channel.substring(1))) {
			return "%mod% is already a moderator!".replace("%mod%", moderator);
		} else {
			Database.addMod(moderator, channel.substring(1));
			return String.format("Successfully added %s to the bots mod list for %s!", moderator, channel);
		}
		
	}
	
}
