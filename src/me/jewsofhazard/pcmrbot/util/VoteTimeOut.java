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

package me.jewsofhazard.pcmrbot.util;

import java.util.ArrayList;

import me.jewsofhazard.pcmrbot.Main;

public class VoteTimeOut {

	private String channel;
	private String kickee;
	private ArrayList<String> kickers;
	
	/**
	 * @param c - channel this vote is in
	 * @param k - person to be timed out
	 */
	public VoteTimeOut(String c, String k) {
		channel = c;
		kickee = k;
		kickers= new ArrayList<>();
		new DelayedVoteTask(120000, this);
	}

	/**
	 * Counts the votes and decides whether or not to time them out
	 */
	public void count() {
		if (((double)kickers.size() / Main.getBot().getUsers(channel).length) >= .55) {
			Main.getBot().sendMessage(channel, "The community has chosen to kick %out%.".replace("%out%", kickee));
			Main.getBot().sendMessage(channel, "/timeout "+kickee+" 1200");
		} else {
			Main.getBot().sendMessage(channel, "The community has chosen to spare %safe%.".replace("%safe%", kickee));
		}
	}

	/**
	 * @param sender - user who voted to kick
	 * @return a formatted message to send to the channel
	 */
	public String addVote(String sender) {
		if (kickers.contains(sender)) {
			return String.format("%s tried to kick %s twice. Do they have a personal vendetta? That is for me to know.", sender, channel);
		}
		kickers.add(sender);
		if (Main.getBot().getConfirmationReplies(channel)) {
			return String.format("%s has has voted to kick %s.", sender, channel);
		}
		return null;
	}
	
}
