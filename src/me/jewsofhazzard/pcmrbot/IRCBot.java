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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	private String connectedChannel;

	private Robot robot;

	public IRCBot(String channel) {
		connectedChannel = channel;
		this.setName("BotDuck");
		try {
			this.robot = new Robot();
		} catch (AWTException ex) {
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

			if(connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
				Timer t = new Timer(connectedChannel);
				setTimer(false);
				setVoteCall(true);
				voteChangeScreen();
				t.setScreenSwitch(true);
			}

		}

		if (message.toLowerCase().startsWith("!votestart ") && isMod(sender)) {

			voting = new ArrayList<>();

			message = message.substring(message.indexOf(" ") + 1);
			String[] voteOptions = message.split("[|]");
			sendMessage(connectedChannel, voteOptions[0]);

			for (int i = 1; i < voteOptions.length; i++) {

				sendMessage(connectedChannel, voteOptions[i]);
				voting.add(new ArrayList<String>());
				voting.get(i-1).add(voteOptions[i]);

			}
			
			sendMessage(
					connectedChannel,
					"Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

			for (int i = 1; i < voteOptions.length; i++) {

				voting.add(new ArrayList<String>());
				voting.get(i).add(voteOptions[i]);

			}

			setTimer(false);
			setVoteCall(true);
			vote();

		}

		if (message.toLowerCase().startsWith("!vote ") && voteCall) {

			boolean canVote = true;

			for (int i = 0; i < voting.size(); i++) {

				for (int x = 0; x < voting.get(i).size(); x++) {

					if (voting.get(i).get(x).equals(sender)) {

						sendMessage(connectedChannel, "I am sorry " + sender
								+ " you cannot vote more than once.");
						canVote = false;
					}

				}

			}

			if (canVote) {
				int vote = Integer.valueOf(message.substring(message
						.indexOf(" ") + 1));
				voting.get(vote - 1).add(sender);
				sendMessage(connectedChannel, sender + " has voted for "
						+ voting.get(vote - 1).get(0));

			}

		}

		if (message.toLowerCase().startsWith("!addmod ") && isMod(sender)) {

			message = message.substring(message.indexOf(" ") + 1);
			try {
				addModerator(message);
			} catch (UnsupportedEncodingException ex) {
				Logger.getLogger(IRCBot.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (IOException ex) {
				Logger.getLogger(IRCBot.class.getName()).log(Level.SEVERE,
						null, ex);
			}

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

		new Timer(connectedChannel);

	}

	public void vote() {

		sendMessage(connectedChannel, "You have 30 seconds to vote.");

		new Timer(connectedChannel);
		
	}

	public void voteCounter() {

		int chosen = 0;

		for (int i = 0; i < voting.size(); i++) {

			if (voting.get(i).size() > voting.get(chosen).size())

				chosen = i;

		}

		sendMessage(connectedChannel,
				"The the community chose " + voting.get(chosen).get(0));

	}

	public void voteTimner() {

		new Timer(connectedChannel);

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

	public void addModerator(String moderator) throws FileNotFoundException,
			UnsupportedEncodingException, IOException {
		TFileWriter.writeFile(new File(connectedChannel+"Mods.txt"), moderator);
		sendMessage(connectedChannel, ".mod " + moderator);
	}

	public boolean isMod(String sender) {
		
		ArrayList<String> mods=TFileReader.readFile(new File(connectedChannel+"Mods.txt"));
		return mods.contains(sender);

	}

	public String getChannel() {

		return connectedChannel;

	}

//	public void setChannel(String sender) {
//
//		channelConnected = "#" + sender;
//
//	}

	public boolean checkMods() throws FileNotFoundException, IOException {
		return new File(connectedChannel+"Mods.txt").exists();
	}

	public void joinMe(String sender) {

		if(connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			new MyBotMain("#" + sender);
			sendMessage(connectedChannel, "I have joined " + sender + "'s channel.");
		}

	}

}
// add a general voting system using 2d array lists to ask questions. Also, I
// can do !callvote 