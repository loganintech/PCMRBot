package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;

public class DelayedTitleTask extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String title;
	private String game;
	
	/**
	 * @param title - the title to set
	 * @param game - the game to set
	 * @param delay - the delay before the setting of the title and game
	 */
	public DelayedTitleTask(String title, String game, long delay) {
		this.title = title;
		this.game = game;
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
		//TwitchUtilities.updateGame("officialpcmasterrace", game);
	}

}
