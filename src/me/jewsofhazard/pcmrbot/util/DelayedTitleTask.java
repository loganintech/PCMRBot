package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.external.twitch.TwitchUtilities;

public class DelayedTitleTask extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String title;
	
	/**
	 * @param title - the title to set
	 * @param game - the game to set
	 * @param delay - the delay before the setting of the title and game
	 */
	public DelayedTitleTask(String title, long delay) {
		this.title = title;
		if(delay > 0) {
			timer.schedule(this, delay);
		}
	}
	
	/**
	 * Updates the title and the game
	 */
	@Override
	public void run() {
		TwitchUtilities.updateTitle("officialpcmasterrace", title);
	}

}
