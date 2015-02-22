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

package me.jewsofhazzard.pcmrbot;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.Commands.CommandParser;
import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.Options;
import me.jewsofhazzard.pcmrbot.util.TFileReader;

import org.jibble.pircbot.IrcException;

public class MyBotMain {
	
	private static IRCBot bot;
	private static final String botChannel = "#pcmrbot";
	private static final Logger logger = Logger.getLogger(MyBotMain.class + "");

	/**
	 * Performs all of the setup for the bot, both on first run, and all
	 * subsequent runs.
	 * 
	 * @param args
	 *            - the oAuth for the bot is passed on the command-line
	 */
	public static void main(String[] args) {
		Database.initDBConnection(args[1]);
		bot = new IRCBot();

		bot.setVerbose(true);
		try {
			bot.connect(
					"irc.twitch.tv", 6667, args[0]);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "
					+ getBotChannel(), e);
		}
		joinChannel(getBotChannel());
		
		File f = new File("connectedChannels.txt");
		if (f.exists()) {
			for (String s : TFileReader.readFile(f)) {
				joinChannel(s);
			}
			f.delete();
		}
		CommandParser.init();
	}

	/**
	 * Performs all of the setup for the bot in the channel specified, both on
	 * first run, and all subsequent runs.
	 */
	public static void joinChannel(String channel) {

		if(bot.isWatchingChannel(channel)) {
			return;
		}

		boolean firstTime = false;
		if (Database.getChannelTables(channel.substring(1))) {
			firstTime = true;
			Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
					+ channel.substring(1) + "Mods VALUES(\'pcmrbot\')");
			Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
					+ channel.substring(1) + "Mods VALUES(\'j3wsofhazard\')");
			Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
					+ channel.substring(1) + "Mods VALUES(\'donald10101\')");
			Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
					+ channel.substring(1) + "Mods VALUES(\'angablade\')");
			if(!channel.equalsIgnoreCase(getBotChannel())) {
				Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
						+ channel.substring(1) + "Mods VALUES(\'"
						+ channel.substring(1) + "\')");
			}
			Database.setOption(
					channel.substring(1),
					Options.welcomeMessage,
					"Welcome %user% to our channel, may you find it entertaining or flat out enjoyable.");
			Database.setOption(channel.substring(1), Options.numCaps, "10");
			Database.setOption(channel.substring(1), Options.numEmotes, "10");
			Database.setOption(channel.substring(1), Options.numSymbols, "10");
			Database.setOption(channel.substring(1), Options.paragraphLength, "250");
		}
		
		bot.joinChannel(channel);
		bot.setWelcomeEnabled(channel, true);
		bot.setConfirmationEnabled(channel, true);
		if (firstTime) {
			bot.onFirstJoin(channel);
		}

	}
	
	public static IRCBot getBot() {
		return bot;
	}

	public static String getBotChannel() {
		return botChannel;
	}


}
