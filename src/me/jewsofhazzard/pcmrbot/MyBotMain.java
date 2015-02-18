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
	private static final String oAuth="";
	
	private static final Logger logger = Logger.getLogger(MyBotMain.class+"");

	public MyBotMain(String channel) {

		this.channel = channel;
		new Thread(this).start();

	}

	public static void main(String[] args) {
		getConnectedchannels().put(getBotChannel(), new IRCBot(getBotChannel()));
		if (!getConnectedchannels().get(getBotChannel()).checkMods()) {
			TFileWriter.writeFile(new File(getBotChannel()+"Mods.txt"), getBotChannel().substring(1), "donald10101", "j3wsofhazard");
		}

		getConnectedchannels().get(getBotChannel()).setVerbose(true);
		try {
			getConnectedchannels().get(getBotChannel())
					.connect("irc.twitch.tv", 6667, oAuth);
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), e);
		}
		getConnectedchannels().get(getBotChannel()).joinChannel(getBotChannel());

	}

	public void run() {

		try {
			getConnectedchannels().put(channel, new IRCBot(channel));
			if (!getConnectedchannels().get(channel).checkMods()) {
				TFileWriter.writeFile(new File(getBotChannel()+"Mods.txt"), getBotChannel().substring(1), channel.substring(1), "donald10101", "j3wsofhazard");
			}

			getConnectedchannels().get(channel).setVerbose(true);
			try {
				getConnectedchannels().get(channel).connect("irc.twitch.tv", 6667,
						oAuth);
			} catch (IrcException e) {
				logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), e);
			}
			getConnectedchannels().get(channel).joinChannel(channel);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "An error occurred while connecting to "+getBotChannel(), ex);
		}

	}

	private static HashMap<String, IRCBot> getConnectedchannels() {
		return connectedChannels;
	}

	public static String getBotChannel() {
		return botChannel;
	}

	public static IRCBot getConnectedchannel(String channel) {
		return connectedChannels.get(channel);
	}

}
