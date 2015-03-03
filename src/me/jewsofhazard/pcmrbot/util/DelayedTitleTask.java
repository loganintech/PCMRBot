package me.jewsofhazard.pcmrbot.util;

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;

public class DelayedTitleTask extends TimerTask {

	private static final Timer timer = new Timer();
	
	private String title;
	private String game;
	
	public DelayedTitleTask(String title, String game, long delay) {
		this.title = title;
		this.game = game;
		if(delay > 0) {
			timer.schedule(this, delay);
		}
	}
	
	@Override
	public void run() {
		System.out.println(title);
		TwitchUtilities.updateTitle("donald10101", title);
		TwitchUtilities.updateGame("donald10101", game);
	}

}
