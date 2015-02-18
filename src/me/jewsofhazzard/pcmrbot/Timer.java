/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazzard.pcmrbot;


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

		MyBotMain.getConnectedchannel(MyBotMain.getBotchannel()).setTimer(true);
		MyBotMain.getConnectedchannel(MyBotMain.getBotchannel()).setVoteCall(false);

		if (screenSwitch) {
			MyBotMain.getConnectedchannel(MyBotMain.getBotchannel()).switchScreen();
		}
		MyBotMain.getConnectedchannel(MyBotMain.getBotchannel()).voteCounter();
		screenSwitch = false;

	}

	public void setScreenSwitch(boolean set) {

		screenSwitch = set;

	}

}
