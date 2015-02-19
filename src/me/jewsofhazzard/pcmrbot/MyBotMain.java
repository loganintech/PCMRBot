/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.util.TFileWriter;

import org.jibble.pircbot.IrcException;

public class MyBotMain implements Runnable {

	private String channel;
	private static final HashMap<String, IRCBot> connectedChannels = new HashMap<>();
	private static final String botChannel="#pcmrbot";
	private static String oAuth="";
	
	private static final Logger logger = Logger.getLogger(MyBotMain.class+"");

	public MyBotMain(String channel) {

		this.channel = channel;
		new Thread(this).start();

	}

	public static void main(String[] args) {
		oAuth=args[0];
		getConnectedChannels().put(getBotChannel(), new IRCBot(getBotChannel()));
		if (!getConnectedChannels().get(getBotChannel()).checkMods()) {
			TFileWriter.writeFile(new File(getBotChannel()+"Mods.txt"), getBotChannel().substring(1), "donald10101", "j3wsofhazard", "angablade");
		}

		getConnectedChannels().get(getBotChannel()).setVerbose(true);
		try {
			getConnectedChannels().get(getBotChannel())
					.connect("irc.twitch.tv", 6667, oAuth);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), e);
		}
		getConnectedChannels().get(getBotChannel()).joinChannel(getBotChannel());

	}

	public void run() {

		try {
			getConnectedChannels().put(channel, new IRCBot(channel));
			if (!getConnectedChannels().get(channel).checkMods()) {
				TFileWriter.writeFile(new File(channel+"Mods.txt"), getBotChannel().substring(1), channel.substring(1), "donald10101", "j3wsofhazard", "angablade");
			}

			getConnectedChannels().get(channel).setVerbose(true);
			try {
				getConnectedChannels().get(channel).connect("irc.twitch.tv", 6667,
						oAuth);
			} catch (IrcException e) {
				logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), e);
			}
			getConnectedChannels().get(channel).joinChannel(channel);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), ex);
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
