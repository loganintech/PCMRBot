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

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Poll extends Command {
	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "poll";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String[] answers = new String[parameters.length - 2];
		if (answers.length < 2) {
			return	"You must provide at least two answers!";
		} else if (Main.getBot().hasPoll(channel)) {
			return "There is already a poll happenning in your channel, wait for it to complete first!";
		}
		for (int i = 2; i < parameters.length; i++) {
			answers[i - 2] = parameters[i];
		}
		Main.getBot().addPoll(channel, new me.jewsofhazard.pcmrbot.util.Poll(channel, parameters[1], answers, Integer.valueOf(parameters[0])).start());
		return null;
	}

}
