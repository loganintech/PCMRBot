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

import java.util.HashMap;
import java.util.Random;

public enum TType {

	CAPS("No need to yell buddy! (Too many Capital letters)"), SYMBOLS(
			"Hey! Watch it! (Too many symbols)"), EMOTE(
			"We get it, you like using that emote! (Too many emotes)"), LINK(
			"You can't just go posting things williy nilly (Link)"), SPAM(
			"You sure do like to say that, huh? (Spam)", "Do you kiss you're mother with that mouth? (Spam)"), PARAGRAPH(
			"A bit long winded aren't we? (Too many characters in your message)");

	private final String[] messages;
	private final HashMap<String, HashMap<String,Integer>> offenders;

	/**
	 * @return a random message for this type of timeout
	 */
	public String getRandomMessage() {
		Random rand = new Random();
		return messages[rand.nextInt(messages.length)];
	}

	/**
	 * @param u - user who might be a previous offender
	 * @param c - channel the user is in
	 * @return true if they are a previous offender, false otherwise
	 */
	public boolean previousOffender(String u, String c) {
		return offenders.containsKey(u) && offenders.get(u).containsKey(c);
	}

	/**
	 * @param u - user who is an offender
	 * @param c - channel the user is in
	 * @param n - number of offenses
	 */
	public void updateOffender(String u, String c, int n) {
		HashMap<String, Integer> o = offenders.get(u);
		if(o == null) {
			o = new HashMap<>();
		}
		o.put(c, n);
		offenders.put(u, o);
	}

	/**
	 * @param u - user who is an offender
	 * @param c - channel the user is in
	 * @return number of offenses, -1 if none
	 */
	public int getOffender(String u, String c) {
		HashMap<String, Integer> o = offenders.get(u);
		if(o == null) {
			return -1;
		}
		return o.get(c);
	}

	/**
	 * @param u - user to be removed
	 * @param c - channel to be removed from the users list
	 */
	public void removeOffender(String u, String c) {
		HashMap<String, Integer> o = offenders.get(u);
		if(o == null) {
			return;
		}
		o.remove(c);
		if(o.size()==0) {
			offenders.remove(u);
		}
	}

	/**
	 * @param m - Array of messages
	 */
	TType(String... m) {
		messages = m;
		offenders = new HashMap<>();
	}
}
