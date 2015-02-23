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

	public void countVotes() {
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
