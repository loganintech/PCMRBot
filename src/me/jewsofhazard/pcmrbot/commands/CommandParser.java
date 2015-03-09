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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class CommandParser {
	private static HashMap<String, Command> commands;
	private static final Logger logger=Logger.getLogger(CommandParser.class+"");
	
	/**
	 * Gets and stores all of the commands in a HashMap for easier use.
	 * @author Jared314
	 */
	public static void init() {
		commands=new HashMap<>();
		Reflections r = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
		
		// Find all commands
		Set<Class<? extends Command>> commandClassList = r.getSubTypesOf(Command.class);

		// Add command instances to the commands hash map
		for(Class<? extends Command> cClass: commandClassList){
			
			try {
				Command c = cClass.newInstance();
				commands.put(c.getCommandText(), c);
			} catch (Exception e) {
				logger.log(Level.WARNING, "An error occurred initializing a command", e);
			}
		}
	}
	
	/**
	 * @param command - command that was sent without the leading !
	 * @param sender - person who sent the command
	 * @param channel - Channel the command was sent in
	 * @param parameters - parameters sent along with the command
	 * @return {@link Command#execute(String, String, String[])} or null if the command does not exist
	 */
	public static String parse(String command, String sender, String channel, String[] parameters) {
		Command c=commands.get(command);
		if(c != null && hasAccess(c, sender, channel)) {
			ArrayList<String> passed = new ArrayList<>();
			for(String s:parameters) {
				logger.info(s);
			}
			int i=0;
			while(i < parameters.length) {
				if(parameters[i].startsWith("\"")) {
					String temp=parameters[i].replace("\"", "") + " ";
					if(parameters[i].endsWith("\"")) {
						passed.add(parameters[i].replace("\"", ""));
						continue;
					}
					i++;
					while(!parameters[i].endsWith("\"")) {
						temp+=parameters[i] + " ";
						i++;
					}
					temp+=parameters[i].replace("\"", "");
					i++;
					passed.add(temp);
					continue;
				}
				passed.add(parameters[i]);
				i++;
			}
			return c.execute(channel, sender, toStringArray(passed));
		}
		return null;
	}

	/**
	 * @param c - Command object that matches what was passed
	 * @param sender - user who sent the command
	 * @param channel - channel the command was sent in
	 * @return true if the user has valid access
	 */
	private static boolean hasAccess(Command c, String sender, String channel) {
		switch(c.getCommandLevel()) {
		case Mod:
			return Database.isMod(sender, channel.substring(1));
		case Owner:
			return sender.equalsIgnoreCase(channel.substring(1));
		case Normal:
			return true;
		}
		return false;
	}
	
	private static String[] toStringArray(ArrayList<String> passed) {
		String[] result = new String[passed.size()];
		for(int i=0;i<passed.size();i++) {
			result[i] = passed.get(i);
		}
		return result;
	}
}
