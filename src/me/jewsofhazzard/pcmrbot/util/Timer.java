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

	public void run() {

		try {
			Thread.sleep(30000);
		} catch (InterruptedException ex) {

		}

		MyBotMain.getConnectedchannel(MyBotMain.getBotChannel()).setTimer(true);
		MyBotMain.getConnectedchannel(MyBotMain.getBotChannel()).setVoteCall(false);

		if (screenSwitch) {
			MyBotMain.getConnectedchannel(MyBotMain.getBotChannel()).switchScreen();
		}
		MyBotMain.getConnectedchannel(MyBotMain.getBotChannel()).voteCounter();
		screenSwitch = false;

	}

	public void setScreenSwitch(boolean set) {

		screenSwitch = set;

	}

}
