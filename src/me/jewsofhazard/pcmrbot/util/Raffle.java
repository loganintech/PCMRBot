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
import java.util.Random;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;

public class Raffle {

	// Let it be known, Mr_chris is the
	// first user to ever win the raffle.
	private ArrayList<String> participants;
	private String channel;
	private ULevel type;
	
	public Raffle(String c, ULevel t) {
		channel = c;
		type = t;
		participants = new ArrayList<>();
                start();
	}
	
	public Raffle start() {
		new Timer(channel, 300, this);
		return this;
	}
	
	public void enter(String sender) {
      //      System.out.println(sender);
		if (participants.contains(sender)) {
			Main.getBot().sendMessage(channel,"%sender% is a dirty cheater and tried to join the raffle more than once, may he be smiten.".replace("%sender%", sender));
			return;
		}

		if ((type.equals(ULevel.Follower)) && (TwitchUtilities.isFollower(channel.substring(1), sender) || sender.equalsIgnoreCase(channel.substring(1)))) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else if (type.equals(ULevel.Subscriber) && (TwitchUtilities.isSubscriber(channel.substring(1), sender) || sender.equalsIgnoreCase(channel.substring(1)))) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else if (type.equals(ULevel.Normal)) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else {
			Main.getBot().sendMessage(channel, "I am sorry %sender% you are not allowed to join this raffle.".replace("%sender%", sender));
		}
                
	}

	public void selectWinner() {
		String winner = participants.get(new Random().nextInt(participants.size()));
		Main.getBot().sendMessage(channel, "There raffle has closed. There are %amt% users in the raffle.".replace("%amt%", participants.size()+""));
		Main.getBot().sendMessage(channel,	"The raffle winner is %winner%.".replace("%winner%", winner));
		Main.getBot().removeRaffle(channel);
	}
}
