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

public class AddAutoReply extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "addautoreply";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters){
		StringBuilder keywords = new StringBuilder();
		for (int i = 0; i < parameters.length - 2; i++) {
			keywords.append(parameters[i] + ",");
		}
		if(parameters.length != 0) {
			keywords.append(parameters[parameters.length - 2]);
		} else {
			keywords.append(parameters[0]);
		}
		String reply = parameters[parameters.length - 1];
		Database.addAutoReply(channel.substring(1), keywords.toString(), reply);
		return String.format("Added auto reply: \"%s\"! Which will be said when all of the following key words are said in %s: %s", reply, channel.substring(1), keywords.toString());
	}
	
}
