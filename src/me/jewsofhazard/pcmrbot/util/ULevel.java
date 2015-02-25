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

public enum ULevel {
	Normal,Follower,Subscriber;

	public static ULevel getTypeFromString(String level) {
		level=level.toLowerCase();
		if(level.endsWith("s")) {
			level=level.substring(0, level.length()-1);
		}
		switch(level.toLowerCase()) {
		case "normal":
		case "everyone":
		case "all":
			return Normal;
		case "follower":
			return Follower;
		case "subscriber":
		case "sub":
			return Subscriber;
		default :
			return null;
		}
	}
}
