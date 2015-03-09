package me.jewsofhazard.pcmrbot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;

public class PointsRunnable implements Runnable {
	static final Logger logger = Logger.getLogger(PointsRunnable.class+"");
	private String user;
	private String channelNoHash;
	private static HashMap<String, ArrayList<String>> currentUsers = new HashMap<>();

	/**
	 * @param user - user earning points
	 * @param channelNoHash - channel the user is in without the leading #
	 */
	public PointsRunnable(String user, String channelNoHash) {
		this.user = user;
		this.channelNoHash = channelNoHash;
		addChannel();
		new Thread(this).start();
	}

	/**
	 * adds a channel for the user specified
	 */
	private void addChannel() {
		ArrayList<String> c = currentUsers.get(user);
		if(c == null) {
			c = new ArrayList<>();
		}
		c.add(channelNoHash);
		currentUsers.put(user, c);
	}

	/**
	 * adds points to the user every 5 minutes
	 */
	public void run() {
		while (currentUsers.containsKey(this.user)) {
			if(TwitchUtilities.isLive(channelNoHash)) {
				Database.addPoints(this.user, this.channelNoHash, 1);
			}
			try {
				Thread.sleep(300000L);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Error adding points to user", e);
			}
		}
	}
	
	/**
	 * @param u - user to check
	 * @return checks if the user is in any channels
	 */
	public static boolean containsUser(String u) {
		return currentUsers.containsKey(u);
	}

	/**
	 * @param user - user to remove the channel from
	 * @param channelNoHash - channel to remove without the leading #
	 */
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
}