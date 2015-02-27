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
	
	public VoteTimeOut(String c, String k) {
		channel = c;
		kickee = k;
		kickers= new ArrayList<>();
		new Timer(channel, 120, this);
	}

	public void count() {

		if (((double)kickers.size() / Main.getBot().getUsers(channel).length) >= .55) {

			Main.getBot().sendMessage(channel, "The community has chosen to kick %out%.".replace("%out%", kickee));

		} else {

			Main.getBot().sendMessage(channel, "The community has chosen to spare %safe%.".replace("%safe%", kickee));

		}

	}

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
