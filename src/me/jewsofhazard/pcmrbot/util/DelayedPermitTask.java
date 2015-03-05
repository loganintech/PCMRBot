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

import java.util.Timer;
import java.util.TimerTask;

import me.jewsofhazard.pcmrbot.Main;

public class DelayedPermitTask extends TimerTask{

	private String user;
	private String channel;
	
	private static final Timer timer = new Timer();
	
	/**
	 * @param u - user being permitted
	 * @param c - channel the permit is in
	 */
	public DelayedPermitTask(String u, String c) {
		this.user = u;
		this.channel = c;
		timer.schedule(this, 180000L);
	}
	
	/**
	 * removes the user from the permit list
	 */
	@Override
	public void run() {
		Main.getBot().removePermit(this, user);
	}
	
	/**
	 * @return the channel this permit is for
	 */
	public String getChannel() {
		return channel;
	}
}
