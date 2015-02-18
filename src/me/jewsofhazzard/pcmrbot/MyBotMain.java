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

	// public static IRCBot bot ;
	private String channel;
	private static final HashMap<String, IRCBot> connectedChannels = new HashMap<>();
	private static final String botChannel="#botduck";

	public MyBotMain(String channel) {

		this.channel = channel;
		new Thread(this).start();

	}

	public static void main(String[] args) throws IrcException, IOException {
		getConnectedchannels().put(getBotChannel(), new IRCBot(getBotChannel()));
		if (!getConnectedchannels().get(getBotChannel()).checkMods()) {
			TFileWriter.writeFile(new File(getBotChannel()+"Mods.txt"), getBotChannel().substring(1));
		}

		getConnectedchannels().get(getBotChannel()).setVerbose(true);
		getConnectedchannels().get(getBotChannel())
				.connect("irc.twitch.tv", 6667, "");
		getConnectedchannels().get(getBotChannel()).joinChannel(getBotChannel());

	}

	public void run() {

		try {
			getConnectedchannels().put(channel, new IRCBot(channel));
			if (!getConnectedchannels().get(channel).checkMods()) {
				TFileWriter.writeFile(new File(getBotChannel()+"Mods.txt"), getBotChannel().substring(1), channel.substring(1));
			}

			getConnectedchannels().get(channel).setVerbose(true);
			try {
				getConnectedchannels().get(channel).connect("irc.twitch.tv", 6667,
						"oauth:f4cge65r3sq86886su3if7regvmvrp");
			} catch (IrcException e) {
				e.printStackTrace();
			}
			getConnectedchannels().get(channel).joinChannel(channel);
		} catch (IOException ex) {
			Logger.getLogger(MyBotMain.class.getName()).log(Level.SEVERE, null,
					ex);
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
