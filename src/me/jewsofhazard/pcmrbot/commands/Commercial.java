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

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Commercial extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Owner;
	}
	
	@Override
	public String getCommandText() {
		return "commercial";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(parameters.length == 0) {
			TwitchUtilities.runCommercial(channel.substring(1));
			return "Running a default length commercial on %channel%".replace("%channel%", channel);
		}
		String length=parameters[0];
		int time = 0;
		try {
			time = Integer.valueOf(length);
		} catch (NumberFormatException e) {
			TwitchUtilities.runCommercial(channel.substring(1));
			return	String.format("%s is not a valid time. Running a default length commercial on %s!", length, channel);
		}
		if (time <= 180 && time % 30 == 0) {
			TwitchUtilities.runCommercial(channel.substring(1), time);
			return String.format("Running a %s for %length% seconds on %s", length, channel);
		} else {
			TwitchUtilities.runCommercial(channel.substring(1));
			return	String.format("%s is not a valid time. Running a default length commercial on %s!", length, channel);
		}
	}

}
