/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Saso, James Wolff, Kyle Nabinger
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

package me.jewsofhazard.pcmrbot.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.Main;

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
		Main.getBot().removePermit(this, user);
	}
	
	public void removePermit() {
		Main.getBot().removePermit(this, user);
	}
	
	public String getChannel() {
		return channel;
	}
}
