/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Ssaso, James Wolff, Angablade
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
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.db.Database;
import me.jewsofhazard.pcmrbot.util.GenerateCommandsHTML;
import me.jewsofhazard.pcmrbot.util.Options;
import me.jewsofhazard.pcmrbot.util.TFileReader;

import org.jibble.pircbot.IrcException;

public final class Main implements Runnable {

	/**
	 * Performs all of the setup for the bot, both on first run, and all
	 * subsequent runs.
	 * 
	 * @param args
	 *            - the oAuth for the bot is passed on the command-line
	 */
	public static void main(String[] args) throws Exception {
		Main.args = args;
		new Main();
		/*try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String message = scanner.nextLine();
				String params = "";
				try {
					params = message.substring(message.indexOf(' ') + 1);
				} catch (StringIndexOutOfBoundsException e) {

				}

				String command = message.substring(1, message.length());
				try {
					command = message.substring(1, message.indexOf(' '));
				} catch (StringIndexOutOfBoundsException e) {

				}
				CommandParser.parse(command, getBotChannel().substring(1),
						getBotChannel(), params);
			}
		}*/
	}

	private static IRCBot bot;
	private static String[] args;
	private static final String botChannel = "#pcmrbot";
	private static final Logger logger = Logger.getLogger(Main.class + "");

	public Main() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Database.initDBConnection(args[1]);
		bot = new IRCBot();

		bot.setVerbose(true);
		try {
			bot.connect("irc.twitch.tv", 6667, args[0]);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE,
					"An error occurred while connecting to Twitch IRC", e);
		}
		joinChannel(getBotChannel());

		File f = new File("connectedChannels.txt");
		if (f.exists()) {
			for (String s : TFileReader.readFile(f)) {
				joinChannel(s);
			}
			f.delete();
		}
	}

	/**
	 * Performs all of the setup for the bot in the channel specified, both on
	 * first run, and all subsequent runs.
	 */
	public static void joinChannel(String channel) {

		if (bot.isWatchingChannel(channel)) {
			return;
		}

		boolean firstTime = false;
		if (Database.getChannelTables(channel.substring(1))) {
			firstTime = true;
			Database.addMod("pcmrbot", channel.substring(1));
			Database.addMod("j3wsofhazard", channel.substring(1));
			Database.addMod("donald10101", channel.substring(1));
			Database.addMod("angablade", channel.substring(1));
			if (!Database.isMod(channel.substring(1), channel.substring(1))) {
				Database.addMod(channel.substring(1), channel.substring(1));
			}
			Database.addOption(
					channel.substring(1),
					Options.welcomeMessage,
					"Welcome %user% to our channel, may you find it entertaining or flat out enjoyable.");
			Database.addOption(channel.substring(1), Options.numCaps, "10");
			Database.addOption(channel.substring(1), Options.numEmotes, "10");
			Database.addOption(channel.substring(1), Options.numSymbols, "10");
			Database.addOption(channel.substring(1), Options.paragraphLength,
					"250");
		}

		bot.joinChannel(channel);
		bot.setWelcomeEnabled(channel, true);
		bot.setConfirmationEnabled(channel, true);
		bot.setSlowMode(channel, false);
		bot.setSubscribersMode(channel, false);
		GenerateCommandsHTML.createCommandsHTML(channel.substring(1));
		if (firstTime) {
			bot.onFirstJoin(channel);
		}
	}

	public static void partChannel(String channel) {
		bot.partChannel(channel);
		bot.removeWelcomeEnabled(channel);
		bot.removeConfirmationReplies(channel);
		bot.removeSlowMode(channel);
		bot.removeSubMode(channel);
	}

	public static IRCBot getBot() {
		return bot;
	}

	public static String getBotChannel() {
		return botChannel;
	}

}
