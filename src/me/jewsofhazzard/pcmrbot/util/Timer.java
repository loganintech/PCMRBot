/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Ssaso, James Wolff, Angablade
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

	private String channel;
	private long time;
	private String type;

	private static final Logger logger = Logger.getLogger(Timer.class + "");

	/**
	 * Creates a new instance of the Timer class.
	 * 
	 * @param channel - The channel this timer is for
	 * @param time - The time (in seconds) until this Timer will expire
	 */
	public Timer(String channel, long time, String type) {
		this.time = time;
		this.type = type;
		this.channel = channel;
		new Thread(this).start();
	}

	/**
	 * Sleeps for time seconds then tells the IRCBot to tally the votes.
	 */
	public void run() {

		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException ex) {
			logger.log(Level.SEVERE, "An error occurred while sleping!", ex);
		}
		if(type.equals("vote")){
		MyBotMain.getBot().setVoteCall(false);
		MyBotMain.getBot().voteCounter(channel);
		}
		else if(type.equals("raffle")){
			
			MyBotMain.getBot().setRaffle(false);
			MyBotMain.getBot().raffleCount(channel);
			
		}
		else if(type.equals("kick")){
			
			MyBotMain.getBot().setVoteKickActive(true);
			MyBotMain.getBot().voteKickCount(channel);
			
		}
		
	}
	
}
