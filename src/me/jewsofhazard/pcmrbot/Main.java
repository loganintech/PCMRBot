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

package me.jewsofhazard.pcmrbot;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.database.ReadScheduleTable;
import me.jewsofhazard.pcmrbot.util.CommandsPage;
import me.jewsofhazard.pcmrbot.util.TOptions;
import me.jewsofhazard.pcmrbot.util.TFileReader;

import org.jibble.pircbot.IrcException;

public class Main implements Runnable{
	
	private static IRCBot bot;
	private static String[] args;
	private static final String botChannel = "#pcmrbot";
	private static final Logger logger = Logger.getLogger(Main.class + "");
	
	/**
	 * Starts a new thread for the bot to exists in so we can pass
	 * bot commands on the command line.
	 */
	public Main() {
		new Thread(this).start();
	}
	
	/**
	 * 
	 * @param args
	 *            [0] - Twitch OAuth
	 *            [1] - Database Password
	 */
	public static void main(String[] args) {
		Main.args=args;
		new Main();
		try(Scanner scan=new Scanner(System.in)) {
			while(true) {
				String message = scan.nextLine();
				String[] params = message.substring(message.indexOf(' ') + 1).split(" ");
				String command;
				try {
					command = message.substring(1, message.indexOf(' '));
				} catch(StringIndexOutOfBoundsException e) {
					command = message.substring(1, message.length());
				}
				if(command.equalsIgnoreCase(params[0].substring(1))) {
					params = new String[0];
				}
				CommandParser.parse(command, getBotChannel().substring(1), getBotChannel(), params);
			}
		}
	}

	/** 
	 * Performs all of the setup for the bot on first run.
	 */
	@Override
	public void run() {
		Database.initDBConnection(args[1]);
		ReadScheduleTable.createDelayedTasks();
		bot = new IRCBot();

		bot.setVerbose(true);
		try {
			bot.connect(
					"irc.twitch.tv", 6667, args[0]);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "An error occurred while connecting to Twitch IRC", e);
		}
		joinChannel(getBotChannel(), false);
		CommandParser.init();
		
		File f = new File("connectedChannels.txt");
		if (f.exists()) {
			for (String s : TFileReader.readFile(f)) {
				joinChannel(s, true);
			}
			f.delete();
		}
	}

	/**
	 * Performs all of the setup for the bot in the channel specified.
	 */
	public static void joinChannel(String channel, boolean isReJoin) {

		if(bot.isWatchingChannel(channel)) {
			return;
		}

		boolean firstTime = false;
		if (Database.getChannelTables(channel.substring(1))) {
			firstTime = true;
			Database.addMod("pcmrbot", channel.substring(1));
			Database.addMod("j3wsofhazard", channel.substring(1));
			Database.addMod("donald10101", channel.substring(1));
			Database.addMod("angablade", channel.substring(1));
			if(!Database.isMod(channel.substring(1), channel.substring(1))) {
				Database.addMod(channel.substring(1), channel.substring(1));
			}
			Database.addOption(channel.substring(1), TOptions.welcomeMessage, "Welcome %user% to our channel, may you find it entertaining or flat out enjoyable.");
			Database.addOption(channel.substring(1), TOptions.numCaps, "20");
			Database.addOption(channel.substring(1), TOptions.numEmotes, "20");
			Database.addOption(channel.substring(1), TOptions.numSymbols, "20");
			Database.addOption(channel.substring(1), TOptions.link, "0");
			Database.addOption(channel.substring(1), TOptions.regular, "288");
			Database.addOption(channel.substring(1), TOptions.paragraphLength, "250");
		}
		
		bot.joinChannel(channel);
		bot.setWelcomeEnabled(channel, true);
		bot.setConfirmationEnabled(channel, true);
		bot.setSlowMode(channel, false);
		bot.setSubMode(channel, false);
		bot.setReJoin(channel, isReJoin);
		CommandsPage.createCommandsHTML(channel.substring(1));
		if (firstTime) {
			bot.onFirstJoin(channel);
		}
	}
	
	/**
	 * Makes the bot leave the channel specified
	 * 
	 * @param channel - channel to be left
	 */
	public static void partChannel(String channel) {
		bot.partChannel(channel);
		bot.removeWelcomeEnabled(channel);
		bot.removeConfirmationReplies(channel);
		bot.removeSlowMode(channel);
		bot.removeSubMode(channel);
	}
	
	/**
	 * @return - the instance of IRCBot
	 */
	public static IRCBot getBot() {
		return bot;
	}

	/**
	 * @return - the main channel we are running in
	 */
	public static String getBotChannel() {
		return botChannel;
	}

	/**
	 * @param moderator - name of the person to check
	 * @param channelNoHash - name of the channel without the
	 * leading #
	 * @return true if the person passed is a moderator added when
	 * the table is set up to begin with
	 */
	public static boolean isDefaultMod(String moderator, String channelNoHash) {
		return moderator.equalsIgnoreCase(channelNoHash) && moderator.equalsIgnoreCase("donald10101") && moderator.equalsIgnoreCase("j3wsofhazard") && moderator.equalsIgnoreCase("angablade") && moderator.equalsIgnoreCase("pcmrbot");
	}


}
