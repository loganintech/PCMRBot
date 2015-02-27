package me.jewsofhazard.pcmrbot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

public class PointsRunnable implements Runnable {
	static final Logger logger = Logger.getLogger(PointsRunnable.class+"");
	private String user;
	private String channel;
	private static HashMap<String, ArrayList<String>> currentUsers = new HashMap<>();

	public PointsRunnable(String user, String channelNoHash) {
		this.user = user;
		this.channel = channelNoHash;
		addChannel();
		new Thread(this).start();
	}

	private void addChannel() {
		ArrayList<String> c = currentUsers.get(user);
		if(c == null) {
			c = new ArrayList<>();
		}
		c.add(channel);
		currentUsers.put(user, c);
	}

	public void run() {
		Database.addPoints(this.user, this.channel, 1);
		try {
			Thread.sleep(300000L);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Error adding points to user", e);
		}
		while (currentUsers.containsKey(this.user)) {
			Database.addPoints(this.user, this.channel, 1);
			try {
				Thread.sleep(300000L);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Error adding points to user", e);
			}
		}
	}
	
	public static boolean containsUser(String u) {
		return currentUsers.containsKey(u);
	}

	public static void removeChannelFromUser(String user, String channelNoHash) {
		ArrayList<String> c = currentUsers.get(user);
		if(c != null) {
			c.remove(channelNoHash);
			if(c.size() == 0) {
				currentUsers.remove(user);
				return;
			}
			currentUsers.put(user, c);
		}
	}
	
	public static void removeAllUsers() {
		currentUsers=new HashMap<>();
	}
}