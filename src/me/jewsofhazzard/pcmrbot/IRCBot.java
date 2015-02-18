package me.jewsofhazzard.pcmrbot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
 * @author JewsOfHazard
 */

public class IRCBot extends PircBot {

	@SuppressWarnings("unused")
	private boolean timer = false;
	private boolean voteCall;
	private ArrayList<ArrayList<String>> voting = new ArrayList<>();
	private ArrayList<String> ringazinUsers = new ArrayList<>();		//ringazin, may he forever be known as the one who initially tried to vote for an option out of the bounds of the choices
	private String connectedChannel;
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

		// Task, find how to pass an FKey when someone says something specific
		// in the chat.

		/*
		 * 
		 * if(message.equalsIgnoreCase("!primary")){
		 * 
		 * //for purposes of testing, lets say this is gonna type somehthing
		 * 
		 * //sendMessage(channelConnected, "Hello old sport"); //
		 * robot.keyPress(KeyEvent.VK_ALT);
		 * //robot.keyPress(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_F4); robot.delay(10);
		 * robot.keyRelease(KeyEvent.VK_F4);
		 * //robot.keyRelease(KeyEvent.VK_CONTROL);
		 * //robot.keyRelease(KeyEvent.VK_ALT); sendMessage(channelConnected,
		 * "You have switched the screen to view the primary stream.");
		 * 
		 * } if(message.equalsIgnoreCase("!secondary")){
		 * 
		 * //robot.keyPress(KeyEvent.VK_ALT);
		 * //robot.keyPress(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_F3); robot.delay(10);
		 * robot.keyRelease(KeyEvent.VK_F3);
		 * //robot.keyRelease(KeyEvent.VK_CONTROL);
		 * //robot.keyRelease(KeyEvent.VK_ALT); sendMessage(channelConnected,
		 * "You have switched the screen to view the secondary stream.");
		 * 
		 * 
		 * }
		 * 
		 * if(message.equalsIgnoreCase("!main")){
		 * 
		 * //robot.keyPress(KeyEvent.VK_ALT);
		 * //robot.keyPress(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_F2); robot.delay(10);
		 * robot.keyRelease(KeyEvent.VK_F2);
		 * //robot.keyRelease(KeyEvent.VK_CONTROL);
		 * //robot.keyRelease(KeyEvent.VK_ALT); sendMessage(channelConnected,
		 * "You have switched the screen to view both streams.");
		 * 
		 * 
		 * }
		 */

		if (message.equalsIgnoreCase("!helppcmr")) {

			sendMessage(
					connectedChannel,
					"Current commands: !join (ask pcmr bot nicely to join your chat) !pcmrbot (information) !vote {choice} (vote during active votes) !votestart (admin only) !addmod (admin only).");

		}

		if (message.equalsIgnoreCase("!voteChangeScreen")) {

			if (connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
				setTimer(false);
				setVoteCall(true);
				voteChangeScreen();
			}

		}

		if (message.toLowerCase().startsWith("!votestart ") && isMod(sender)) {

			voting = new ArrayList<>();
			ringazinUsers = new ArrayList<>();

			message = message.substring(message.indexOf(" ") + 1);
			String[] voteOptions = message.split("[|]");
			sendMessage(connectedChannel, voteOptions[1]);

			for (int i = 2; i < voteOptions.length; i++) {

				sendMessage(connectedChannel, voteOptions[i]);
				voting.add(new ArrayList<String>());
				voting.get(i - 2).add(voteOptions[i]);

			}

			sendMessage(
					connectedChannel,
					"Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

			for (int i = 2; i < voteOptions.length; i++) {

				voting.add(new ArrayList<String>());
				voting.get(i-2).add(voteOptions[i]);

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
				try{
					voting.get(vote - 1).add(sender);
				} catch (IndexOutOfBoundsException e) {
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

		if (message.equalsIgnoreCase("!pcmrbot")) {

			sendMessage(
					connectedChannel,
					"I was made by J3wsOfHazard in free time during an AP Computer Science class in high school.");

		}

	}

	public void voteChangeScreen() {

		sendMessage(connectedChannel, "You have 30 seconds to vote.");

		new Timer(connectedChannel, 30).setScreenSwitch(true);

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

	public void switchScreen() {

		sendMessage(
				connectedChannel,
				"The voting has ended, please wait almost no seconds for the timer to complete.");

		if (voting.get(0).size() > voting.get(1).size()
				&& voting.get(0).size() > voting.get(2).size()) { // if
																	// secondary
																	// is the
																	// highest

			robot.keyPress(KeyEvent.VK_F3);
			robot.delay(10);
			robot.keyRelease(KeyEvent.VK_F3);
			sendMessage(connectedChannel,
					"You have switched the screen to view the secondary stream.");

		}

		else if (voting.get(1).size() > voting.get(0).size()
				&& voting.get(1).size() > voting.get(2).size()) { // if primary
																	// is the
																	// highest

			robot.keyPress(KeyEvent.VK_F4);
			robot.delay(10);
			robot.keyRelease(KeyEvent.VK_F4);
			sendMessage(connectedChannel,
					"You have switched the screen to view the primary stream.");

		}

		else {

			robot.keyPress(KeyEvent.VK_F2);
			robot.delay(10);
			robot.keyRelease(KeyEvent.VK_F2);
			sendMessage(connectedChannel,
					"You have switched the screen to view both streams.");

		}

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

}
// add a general voting system using 2d array lists to ask questions. Also, I
// can do !callvote 