package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.Main;

public class DelayedWelcome extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String channel;
	private String user;
	
	public DelayedWelcome(String channel, String user) {
		this.channel = channel;
		this.user = user;
		
		timer.schedule(this, 1800000);
	}
	
	@Override
	public void run() {
		Main.getBot().removeWelcome(channel, user);
	}

}
