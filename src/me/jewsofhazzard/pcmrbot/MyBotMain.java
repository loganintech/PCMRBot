/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jibble.pircbot.IrcException;

public class MyBotMain implements Runnable {

	// public static IRCBot bot ;
	private String channel;
	private static final HashMap<String, IRCBot> connectedChannels = new HashMap<>();
	private static final String botChannel="#botduck";

	public MyBotMain(String channel) {

		this.channel = channel;
		new Thread(this).start();

	}

	public static void main(String[] args) throws IrcException, IOException {
		getConnectedchannels().put(getBotchannel(), new IRCBot(getBotchannel()));

		// create moderator list or read it if it already exists
		// bot = new IRCBot();

		PrintWriter writer;
		if (getConnectedchannels().get(getBotchannel()).checkMods()) {
			writer = new PrintWriter("ModeratorList.txt", "UTF-8");
			writer.println("j3wsofhazard");
			writer.println("pcmrbot");
			writer.println("botduck");
			writer.close();
		} else {
			new File("ModeratorList.txt").createNewFile();
			writer = new PrintWriter("ModeratorList.txt", "UTF-8");
			writer.println("j3wsofhazard");
			writer.println("pcmrbot");
			writer.println("botduck");
			writer.close();
		}

		getConnectedchannels().get(getBotchannel()).setVerbose(true);
		getConnectedchannels().get(getBotchannel())
				.connect("irc.twitch.tv", 6667, "");
		getConnectedchannels().get(getBotchannel()).joinChannel(getBotchannel());

	}

	public void run() {

		try {
			getConnectedchannels().put(channel, new IRCBot(channel));
			
			PrintWriter writer;
			if (getConnectedchannels().get(channel).checkMods()) {
				writer = new PrintWriter("ModeratorList.txt", "UTF-8");
				writer.println("j3wsofhazard");
				writer.println("pcmrbot");
				writer.println("botduck");
				writer.close();
			} else {
				new File("ModeratorList.txt").createNewFile();
				writer = new PrintWriter("ModeratorList.txt", "UTF-8");
				writer.println("j3wsofhazard");
				writer.println("pcmrbot");
				writer.println("botduck");
				writer.close();
			}

			getConnectedchannels().get(channel).setVerbose(true);
			// worldDomination.get("#pcmrbot").connect("irc.twitch.tv", 6667,
			// "oauth:");
			getConnectedchannels().get(channel).joinChannel(channel);
		} catch (IOException ex) {
			Logger.getLogger(MyBotMain.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}

	private static HashMap<String, IRCBot> getConnectedchannels() {
		return connectedChannels;
	}

	public static String getBotchannel() {
		return botChannel;
	}

	public static IRCBot getConnectedchannel(String channel) {
		return connectedChannels.get(channel);
	}

}
