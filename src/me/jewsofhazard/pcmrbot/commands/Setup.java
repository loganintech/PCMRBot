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

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Setup extends Command {

	@Override
	public String execute(String channel, String sender, String... parameters) {
                try {
                System.out.println(parameters[0]);
                }
                catch(Exception e) {
                parameters[0] = "fish"; //It doesn't matter as long as it is not continue. We didn't check if there was no parameters.
                }
		if(!parameters[0].equalsIgnoreCase("continue")) {
			Main.getBot().sendMessage(
				channel,
				"To begin with, we use a two step system to define a few options. Let's begin with timing out a user.");
			Main.getBot().sendMessage(
				channel,
				"Users are timed out for excessive caps, symbols, emotes, long messages, links, blacklisted words (spam).");
			Main.getBot().sendMessage(
				channel,
				"We would like you to configure the ammount of capital letters, symbols, and emotes (all of which default to 20), and paragraphs(defaults to 250 characters) allowed in a message.");
			Main.getBot().sendMessage(
				channel,
				"To change this, please run !changeOption <caps, symbols, emotes, paragraph>|<new value>. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");
			Main.getBot().sendMessage(
					channel,
					"If you would like to disable any spam protection simply set its value to -1, except for links which only take \"enable\" or \"disable\".");
			Main.getBot().sendMessage(channel, "Now you need to set up how regulars are set up in your channel. Regulars are essentially people who have spent a certain ammount of time in your chat (which you define)");
			Main.getBot().sendMessage(channel, "To become a regular a user must earn a certain number of points which are earned by being in chat, 1 point for every 5 minutes.");
			Main.getBot().sendMessage(channel, "Set how many hours a user must be in the chat to become a regular by typing !changeOption regular|<time in hours>");
			Main.getBot().sendMessage(
				channel,
				"Once you are finished type \"!setup continue\" in chat!");
		} else {
			Main.getBot().sendMessage(
				channel,
				"Next we are going to configure the welcome message for when new users enter your channel. It has a default (which should have been said to you by now), but if you want to change it you can type !changewelcome <message>.");
			Main.getBot().sendMessage(
				channel,
				"If you want the users name to appear in the join message use %user%. This will cause the bot to replace that with the name of the person who is joining.");
			Main.getBot().sendMessage(
				channel,
				"If you would like to permanantely disable this feature type \"!changeWelcome none\" without the quotes! To temporarily disable it type !disableWelcome and to enable it type !enableWelcome (This only works if the message is something is something other than \"none\")!");
			Main.getBot().sendMessage(
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
