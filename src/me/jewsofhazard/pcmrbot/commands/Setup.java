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

package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Setup extends Command {

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(!parameters[0].equalsIgnoreCase("continue")) {
			MyBotMain.getBot().sendMessage(
				channel,
				"To begin with, we use a two-part system to define a few options. Let's begin with timing out a user.");
			MyBotMain.getBot().sendMessage(
				channel,
				"Users are timed out for excessive caps, symbols, emotes, long messages, links, blacklisted words (spam).");
			MyBotMain.getBot().sendMessage(
				channel,
				"We would like you to configure the ammount of capital letters, symbols, and emotes (all of which default to 20), and paragraphs(defaults to 250 characters) allowed in a message.");
			MyBotMain.getBot().sendMessage(
				channel,
				"To change this, please run !changeOption <caps, symbols, emotes, paragraph>|<new value>. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");
			MyBotMain.getBot().sendMessage(
					channel,
					"If you would like to disable any spam protection simply set its value to -1, except for links which only takes \"enable\" or \"disable\".");
			MyBotMain.getBot().sendMessage(
				channel,
				"Once you are finished type \"!setup continue\" in chat!");
		} else {
			MyBotMain.getBot().sendMessage(
				channel,
				"Next we are going to configure the welcome message for when new users enter your channel. It has a default (which should have been said to you by now), but if you want to change it you can type !changewelcome <message>.");
			MyBotMain.getBot().sendMessage(
				channel,
				"If you want the users name to appear in the join message use %user%. This will cause the bot to replace that with the name of the person who is joining.");
			MyBotMain.getBot().sendMessage(
				channel,
				"If you would like to permanantely disable this feature type \"!changeWelcome none\" without the quotes! To temporarily disable it type !disableWelcome and to enable it type !enableWelcome (This only works if the message is something is something other than \"none\")!");
			MyBotMain.getBot().sendMessage(
				channel,
				"Also, if you would like to use subscriber raffles or change the stream title and game, please go to http://pcmrbot.no-ip.info/authorize to authorize the bot!");
		}
		return null;
	}

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Owner;
	}

	@Override
	public String getCommandText() {
		return "setup";
	}

}
