package me.jewsofhazard.pcmrbot.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.MyBotMain;

public class Permit implements Runnable{

	private String user;
	private String channel;
	
	private static final Logger logger = Logger.getLogger(Permit.class+""); 
	
	public Permit(String u, String c) {
		this.user = u;
		this.channel = c;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(180000L);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Unable to sleep", e);
		}
		MyBotMain.getBot().removePermit(this, user);
	}
	
	public void removePermit() {
		MyBotMain.getBot().removePermit(this, user);
	}
	
	public String getChannel() {
		return channel;
	}
}
