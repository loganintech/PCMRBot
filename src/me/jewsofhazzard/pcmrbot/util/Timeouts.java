package me.jewsofhazzard.pcmrbot.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Timeouts implements Runnable {

	private static final Logger logger = Logger.getLogger(Timeouts.class + "");

	private String channel;
	private String user;
	private int time;
	private TType type;

	public Timeouts(String c, String u, int t, TType type) {
		channel = c;
		user = u;
		time = t;
		this.type = type;
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Unable to timeout user: " + user, e);
		}
		if (type.previousOffender(user)) {
			if (type.getOffender(user) == 1) {
				MyBotMain.getConnectedChannel(channel).sendMessage(channel,
						"/timeout " + user + " 300");
				MyBotMain.getConnectedChannel(channel).sendMessage(
						channel,
						type.getRandomMessage()
								+ " Second warning, 5 minute timeout!");
				type.updateOffender(user, 2);
			} else if (type.getOffender(user) == 2) {
				MyBotMain.getConnectedChannel(channel).sendMessage(channel,
						"/timeout " + user + " 600");
				MyBotMain.getConnectedChannel(channel).sendMessage(
						channel,
						type.getRandomMessage()
								+ "Third warning, 10 minute timeout!");
				type.updateOffender(user, 3);
			} else if (type.getOffender(user) == 3) {
				MyBotMain.getConnectedChannel(channel).sendMessage(channel,
						"/timeout " + user + " 900");
				MyBotMain.getConnectedChannel(channel).sendMessage(
						channel,
						type.getRandomMessage()
								+ " Fourth warning, 15 minute timeout!");
				type.updateOffender(user, 4);
			} else if (type.getOffender(user) == 4) {
				MyBotMain.getConnectedChannel(channel).sendMessage(channel,
						"/timeout " + user + " 1200");
				MyBotMain.getConnectedChannel(channel).sendMessage(
						channel,
						type.getRandomMessage()
								+ " Fifth warning, 20 minute timeout!");
				type.updateOffender(user, 5);
			} else {
				MyBotMain.getConnectedChannel(channel).sendMessage(channel,
						"/timeout " + user + " 7200");
				MyBotMain
						.getConnectedChannel(channel)
						.sendMessage(
								channel,
								type.getRandomMessage()
										+ " Sixth warning, 2 hour timeout! You dun' goofed!");
				type.removeOffender(user);
			}
		} else {
			type.updateOffender(user, 1);
			MyBotMain.getConnectedChannel(channel).sendMessage(channel,
					"/timeout " + user + " " + time);
			MyBotMain.getConnectedChannel(channel).sendMessage(channel,
					type.getRandomMessage() + " First warning");
		}
	}
}
