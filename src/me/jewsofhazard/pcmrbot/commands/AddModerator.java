/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Saso, James Wolff, Kyle Nabinger
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class AddModerator extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "addmoderator";
	}
	
	@Override
	/**
	 * Add's a moderator to the channel's Mod's table in the database if they are not already in it.
	 * 
	 * @return a formatted message with the results of the method
	 */
	public String execute(String channel, String sender, String...parameters){
		String moderator=parameters[0];
		if (Database.isMod(moderator, channel.substring(1))) {
			return "%mod% is already a moderator!".replace("%mod%", moderator);
		} else {
			Database.addMod(moderator, channel.substring(1));
			return String.format("Successfully added %s to the bots mod list for %s!", moderator, channel);
		}
		
	}
	
}
