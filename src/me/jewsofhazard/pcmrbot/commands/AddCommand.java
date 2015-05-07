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

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.CommandsPage;

public class AddCommand extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "addcom";
	}
	
//	!addcom !welcome|{user}|Hey there, {user}! Welcome to the chat!

	@Override
        @SuppressWarnings("empty-statement")
	public String execute(String channel, String sender, String... parameters) {
       try {
           
                StringBuilder params = new StringBuilder();
		if (parameters.length > 2) {
			for(int i = 1;i < parameters.length - 1;i++) {
				params.append(parameters[i] + " ");
			}
		}
                
		Database.addCommand(channel, parameters[0], params.toString(), parameters[parameters.length - 1]);
		CommandsPage.createCommandsHTML(channel.substring(1));
		return "Added command ( " + parameters[0] + " ) to the database and generated you custom commands page located at http://pcmrbot.no-ip.info/commands/%channel%.html".replace("%channel%", channel.substring(1));
	}
       catch (IndexOutOfBoundsException e) {
           return "There was an issue processing the information!";
        }
            
    }
}
