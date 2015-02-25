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

import me.jewsofhazard.pcmrbot.MyBotMain;

public class Poll {

	// ringazin, may he forever be known as the one who initially
	// tried to vote for an option out of the bounds of the choices
	private ArrayList<String> ringazinUsers;
	private ArrayList<ArrayList<String>> voting;
	
	private String channel;
	private String[] answers;
	int answerCount;
	private int length;
	
	public Poll(String c, String[] a, int l) {
		channel=c;
		answers=a;
		answerCount=answers.length;
		length=l;
	}
	
	public Poll start() {
		ringazinUsers = new ArrayList<>();
		voting = new ArrayList<>();

		for (int i = 0; i < answers.length; i++) {

			MyBotMain.getBot().sendMessage(channel, (i + 1) + ": " + answers[i]);
			voting.add(new ArrayList<String>());
			voting.get(i).add(answers[i]);

		}

		MyBotMain.getBot().sendMessage(channel, "Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

		for (int i = 0; i < answers.length; i++) {

			voting.add(new ArrayList<String>());
			voting.get(i).add(answers[i]);

		}
		MyBotMain.getBot().sendMessage(channel, "You have %length% seconds to vote.".replace("%length%", length+""));
		new Timer(channel, length, this);
		return this;
	}

	public void count() {
		int chosen = 0;
		for (int i = 0; i < voting.size(); i++) {
			if (voting.get(i).size() > voting.get(chosen).size()) {
				chosen = i;
			}
		}

		MyBotMain.getBot().sendMessage(channel, "The community chose %choice%".replace("%choice%", voting.get(chosen).get(0)));
		MyBotMain.getBot().removePoll(channel);
	}

	public void vote(String sender, String v) {
		boolean canVote = true;
		for (int i = 0; i < voting.size(); i++) {
			if (voting.get(i).contains(sender)) {
				if (MyBotMain.getBot().getConfirmationReplies(channel)) {
					MyBotMain.getBot().sendMessage(channel, "I am sorry %sender% you cannot vote more than once.".replace("%sender%", sender));
				}
				canVote = false;
			}
		}
		if (ringazinUsers.contains(sender)) {
			if (MyBotMain.getBot().getConfirmationReplies(channel)) {
				MyBotMain.getBot().sendMessage(channel, "I am sorry %sender% you cannot vote more than once.".replace("%sender%", sender));
			}
			canVote = false;
		}
		if (canVote) {
			int vote = 0;
			if (v.equalsIgnoreCase("random")) {
				vote = new Random().nextInt();
			}
			vote = Integer.valueOf(v);
			if (vote < answerCount + 1) {
				voting.get(vote - 1).add(sender);
			} else {
				if (MyBotMain.getBot().getConfirmationReplies(channel)) {
					MyBotMain.getBot().sendMessage(channel,"%sender% tried to break me, may hell forever reign upon him! (You cannot participate in this vote.)".replace("%sender%", sender));
				}
				ringazinUsers.add(sender);
				return;
			}
			if (MyBotMain.getBot().getConfirmationReplies(channel)) {
				MyBotMain.getBot().sendMessage(channel,String.format("%s has voted for %s", sender, voting.get(vote - 1).get(0)));
			}
		}
	}
}
