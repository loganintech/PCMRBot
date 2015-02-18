/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot.util;

import me.jewsofhazzard.pcmrbot.MyBotMain;


/**
 *
 * @author Hazard
 */
public class Timer implements Runnable {

	private boolean screenSwitch;
	private String channel;
	
	public Timer(String channel) {
		this.channel=channel;
		new Thread(this).start();
	}

	public void run() {

		try {
			Thread.sleep(30000);
		} catch (InterruptedException ex) {

		}

		MyBotMain.getConnectedchannel(channel).setTimer(true);
		MyBotMain.getConnectedchannel(channel).setVoteCall(false);

		if (screenSwitch) {
			MyBotMain.getConnectedchannel(channel).switchScreen();
		}
		MyBotMain.getConnectedchannel(channel).voteCounter();
		screenSwitch = false;

	}

	public void setScreenSwitch(boolean set) {

		screenSwitch = set;

	}

}
