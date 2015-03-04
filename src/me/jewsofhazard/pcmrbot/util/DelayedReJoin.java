package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.Main;

public class DelayedReJoin extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String channel;
	
	/**
	 * @param channel - the channel to not welcome in
	 */
	public DelayedReJoin(String channel) {
		this.channel = channel;
		timer.schedule(this, 480000);
	}
	
	/**
	 * Remove the channel from join notification blocking
	 */
	@Override
	public void run() {
		Main.getBot().removeReJoin(channel);
	}

}
