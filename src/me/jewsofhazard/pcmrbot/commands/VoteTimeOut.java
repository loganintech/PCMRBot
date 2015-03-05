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
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

import org.jibble.pircbot.User;

public class VoteTimeOut extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "voteto";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(Main.getBot().getVoteTimeOut(channel) != null) {
			return "There is already a vote to timeout a user happenning in this channel. If you were trying to vote just type \"!voteTO\"!";
		}
		String kickee=parameters[0];
		boolean exists = false;
		for(User u:Main.getBot().getUsers(channel)) {
			if(u.getNick().equalsIgnoreCase(kickee)) {
				exists=true;
			}
		}
		if(exists && Database.isMod(sender, channel.substring(1))) {
			Main.getBot().addVoteTimeOut(channel, new me.jewsofhazard.pcmrbot.util.VoteTimeOut(channel, kickee));
			return "The channel has begun a vote to timeout %toKick%. Type !voteTO to place your vote. To vote no just do not vote.".replace("%toKick%", kickee);
		}
		boolean startVote = true;
		if(getCommandText().equalsIgnoreCase(kickee.substring(1))) {
			startVote = false;
		}
		if(startVote && Database.isMod(sender, channel.substring(1))) {
			return "%kickee% is not in the channel!".replace("%kickee%", kickee);
		}
		return Main.getBot().getVoteTimeOut(channel).addVote(sender);
	}

}
