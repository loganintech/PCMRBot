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

public class Game extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "game";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
            StringBuilder sb = new StringBuilder();
            for(String s:parameters) {
            	sb.append(s + " ");
            }
		if (TwitchUtilities.updateGame(channel.substring(1),
				sb.toString())) {
			return "Successfully changed the stream game to \""
							+ sb.toString() + "\"!";
		} else {
			return "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allow me to do this and so much more!";
		}
	}

}
