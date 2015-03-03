package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.Main;

public class DelayedReJoin extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String channel;
	
	public DelayedReJoin(String channel) {
		this.channel = channel;
		timer.schedule(this, 480000);
	}
	
	@Override
	public void run() {
		Main.getBot().removeReJoin(channel);
	}

}
