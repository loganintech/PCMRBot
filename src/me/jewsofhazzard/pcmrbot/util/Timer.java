/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.MyBotMain;


/**
 *
 * @author Hazard
 */
public class Timer implements Runnable {

	private boolean screenSwitch;
	private String channel;
	
	private static final Logger logger = Logger.getLogger(Timer.class+"");
	
	public Timer(String channel) {
		this.channel=channel;
		new Thread(this).start();
	}

	public void run() {

		try {
			Thread.sleep(30000);
		} catch (InterruptedException ex) {
			logger.log(Level.SEVERE, "An error occurred while sleping!", ex);
		}

		MyBotMain.getConnectedchannel(channel).setTimer(true);
		MyBotMain.getConnectedchannel(channel).setVoteCall(false);

		if (screenSwitch) {
			MyBotMain.getConnectedchannel(channel).switchScreen();
			return;
		}
		MyBotMain.getConnectedchannel(channel).voteCounter();
		screenSwitch = false;

	}

	public void setScreenSwitch(boolean set) {

		screenSwitch = set;

	}

}
