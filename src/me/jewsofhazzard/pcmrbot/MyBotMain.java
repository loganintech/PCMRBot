/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.database.Database;

import org.jibble.pircbot.IrcException;

public class MyBotMain implements Runnable {

	private String channel;
	private static final HashMap<String, IRCBot> connectedChannels = new HashMap<>();
	private static final String botChannel = "#pcmrbot";
	private static String oAuth = "";

	private static final Logger logger = Logger.getLogger(MyBotMain.class + "");

	/**
	 * Creates a new instance of MyBotMain (called whenever the bot joins a new channel).
	 * @param channel - the channel we are joining
	 */
	public MyBotMain(String channel) {
		this.channel = channel;
		new Thread(this).start();
	}

	/**
	 * Performs all of the setup for the bot, both on first run, and all subsequent runs.
	 * 
	 * @param args - the oAuth for the bot is passed on the command-line
	 */
	public static void main(String[] args) {
		Database.initDBConnection(args[1]);
		if(Database.getMainTables()) {
			Database.executeUpdate("INSERT INTO "+Database.DEFAULT_SCHEMA+"."+getBotChannel().substring(1)+"Mods VALUES(\'pcmrbot\')");
			Database.executeUpdate("INSERT INTO "+Database.DEFAULT_SCHEMA+"."+getBotChannel().substring(1)+"Mods VALUES(\'j3wsofhazard\')");
			Database.executeUpdate("INSERT INTO "+Database.DEFAULT_SCHEMA+"."+getBotChannel().substring(1)+"Mods VALUES(\'donald10101\')");
			Database.executeUpdate("INSERT INTO "+Database.DEFAULT_SCHEMA+"."+getBotChannel().substring(1)+"Mods VALUES(\'angablade\')");
		}
		oAuth = args[0];
		getConnectedChannels()
				.put(getBotChannel(), new IRCBot(getBotChannel()));

		getConnectedChannels().get(getBotChannel()).setVerbose(true);
		try {
			getConnectedChannels().get(getBotChannel()).connect(
					"irc.twitch.tv", 6667, oAuth);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "
					+ getBotChannel(), e);
		}
		getConnectedChannels().get(getBotChannel())
				.joinChannel(getBotChannel());

	}

	/**
	 * Performs all of the setup for the bot in the channel specified, both on first run, and all subsequent runs.
	 */
	public void run() {
		boolean firstTime=false;
			if(Database.getChannelTables(channel.substring(1))) {
				firstTime=true;
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + channel.substring(1) + "Mods VALUES(\'pcmrbot\')");
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + channel.substring(1) + "Mods VALUES(\'j3wsofhazard\')");
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + channel.substring(1) + "Mods VALUES(\'donald10101\')");
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + channel.substring(1) + "Mods VALUES(\'angablade\')");
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + channel.substring(1) + "Mods VALUES(\'"+ channel.substring(1) +"\')");
			}
			getConnectedChannels().put(channel, new IRCBot(channel));

			getConnectedChannels().get(channel).setVerbose(true);
			try {
				getConnectedChannels().get(channel).connect("irc.twitch.tv",
						6667, oAuth);
			} catch (IrcException|IOException e) {
				logger.log(Level.SEVERE,
						"An error occurred while connecting to "
								+ getBotChannel(), e);
			}
			getConnectedChannels().get(channel).joinChannel(channel);
			if(firstTime) {
				getConnectedChannels().get(channel).onFirstJoin();
			}

	}

	public static HashMap<String, IRCBot> getConnectedChannels() {
		return connectedChannels;
	}

	public static String getBotChannel() {
		return botChannel;
	}

	public static IRCBot getConnectedChannel(String channel) {
		return connectedChannels.get(channel);
	}

}
