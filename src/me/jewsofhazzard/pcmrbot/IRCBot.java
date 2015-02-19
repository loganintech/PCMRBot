package me.jewsofhazzard.pcmrbot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.util.TFileReader;
import me.jewsofhazzard.pcmrbot.util.TFileWriter;
import me.jewsofhazzard.pcmrbot.util.Timer;

import org.jibble.pircbot.PircBot;

/**
 *
 * @author JewsOfHazard, Donald10101, And peoples.
 */

public class IRCBot extends PircBot {

	@SuppressWarnings("unused")
	private boolean timer = false;
	private boolean voteCall;
	private ArrayList<ArrayList<String>> voting = new ArrayList<>();
	private ArrayList<String> ringazinUsers = new ArrayList<>();		//ringazin, may he forever be known as the one who initially tried to vote for an option out of the bounds of the choices
	private int optionCount;
	private String connectedChannel;
	@SuppressWarnings("unused")
	private Robot robot;
	
	private static final Logger logger = Logger.getLogger(IRCBot.class+"");

	public IRCBot(String channel) {
		connectedChannel = channel;
		this.setName(MyBotMain.getBotChannel().substring(1));
		try {
			this.robot = new Robot();
		} catch (AWTException ex) {
			logger.log(Level.SEVERE, "An error occurred initializing the Robot!", ex);
		}
	}
	
	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		if(channel.equalsIgnoreCase(connectedChannel)) {
			addModerator(recipient);
		}
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {

		if (message.equalsIgnoreCase("!helppcmr")) {

			sendMessage(
					connectedChannel,
					"Current commands: !join (ask pcmr bot nicely to join your chat) !pcmrbot (information) !vote {choice} (vote during active votes) !votestart (admin only) !addmod (admin only).");

		}

		if (message.toLowerCase().startsWith("!votestart ") && isMod(sender)) {

			voting = new ArrayList<>();
			ringazinUsers = new ArrayList<>();
			optionCount=0;

			message = message.substring(message.indexOf(" ") + 1);
			String[] voteOptions = message.split("[|]");
			String[] answers = new String[voteOptions.length-2];
			for(int i=2;i<voteOptions.length;i++) {
				answers[i-2]=voteOptions[i];
				optionCount++;
			}
			sendMessage(connectedChannel, voteOptions[1]);

			for (int i = 0; i < answers.length; i++) {

				sendMessage(connectedChannel, answers[i]);
				voting.add(new ArrayList<String>());
				voting.get(i).add(answers[i]);

			}

			sendMessage(
					connectedChannel,
					"Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

			for (int i = 0; i < answers.length; i++) {

				voting.add(new ArrayList<String>());
				voting.get(i).add(voteOptions[i]);

			}

			setTimer(false);
			setVoteCall(true);
			vote((long)Integer.valueOf(voteOptions[0]));

		}

		if (message.toLowerCase().startsWith("!vote ") && voteCall) {

			boolean canVote = true;

			for (int i = 0; i < voting.size(); i++) {

				if (voting.get(i).contains(sender)) {

					sendMessage(connectedChannel, "I am sorry " + sender
							+ " you cannot vote more than once.");
					canVote = false;
				}

			}
			
			if (ringazinUsers.contains(sender)) {

				sendMessage(connectedChannel, "I am sorry " + sender
						+ " you cannot vote more than once.");
				canVote = false;
			}

			if (canVote) {
				int vote = Integer.valueOf(message.substring(message
						.indexOf(" ") + 1));
				if(vote<optionCount) {
					voting.get(vote - 1).add(sender);
				} else {
					sendMessage(connectedChannel, sender + " tried to break me, may hell forever reign upon him! (You cannot participate in this vote.)");
					ringazinUsers.add(sender);
					return;
				}
				sendMessage(connectedChannel, sender + " has voted for "
						+ voting.get(vote - 1).get(0));

			}

		}

		if (message.toLowerCase().startsWith("!addmod ") && isMod(sender)) {

			message = message.substring(message.indexOf(" ") + 1);
			addModerator(message);

		}

		if (message.equalsIgnoreCase("!join")) {

			joinMe(sender);

		}
		
		if (message.equalsIgnoreCase("!leave")) {
			
			leaveMe(sender);
			
		}

		if (message.equalsIgnoreCase("!pcmrbot")) {

			sendMessage(
					connectedChannel,
					"I was made by J3wsOfHazard in free time during an AP Computer Science class in high school.");

		}

	}


	public void vote(long time) {

		sendMessage(connectedChannel, "You have " + time + " seconds to vote.");

		new Timer(connectedChannel, time);

	}

	public void voteCounter() {

		int chosen = 0;

		for (int i = 0; i < voting.size(); i++) {

			if (voting.get(i).size() > voting.get(chosen).size())

				chosen = i;

		}

		sendMessage(connectedChannel,
				"The community chose " + voting.get(chosen).get(0));

	}

	public void setTimer(boolean duh) {

		this.timer = duh;

	}

	public void setVoteCall(boolean set) {

		this.voteCall = set;

	}

	
	public void addModerator(String moderator) {
		if (!TFileReader.readFile(new File(connectedChannel + "Mods.txt"))
				.contains(moderator)) {
			TFileWriter.writeFile(new File(connectedChannel + "Mods.txt"),
					moderator);
			sendMessage(connectedChannel, "Successfully added " + moderator
					+ " to the bots mod list!");
		} else {
			sendMessage(connectedChannel, moderator
					+ " is already a moderator!");
		}
	}

	public boolean isMod(String sender) {

		ArrayList<String> mods = TFileReader.readFile(new File(connectedChannel
				+ "Mods.txt"));
		return mods.contains(sender);

	}

	public String getChannel() {

		return connectedChannel;

	}

	public boolean checkMods() {
		return new File(connectedChannel + "Mods.txt").exists();
	}

	public void joinMe(String sender) {

		if (connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			new MyBotMain("#" + sender);
			sendMessage(connectedChannel, "I have joined " + sender
					+ "'s channel.");
		}

	}
	
	public void leaveMe(String channel) {
		if (MyBotMain.getConnectedChannel(channel)!=null) {
			MyBotMain.getConnectedChannel(channel).partChannel(channel);
			MyBotMain.getConnectedChannels().remove(channel);
		}
	}

}
// add a general voting system using 2d array lists to ask questions. Also, I
// can do !callvote 