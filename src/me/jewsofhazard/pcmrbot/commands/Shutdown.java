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

import java.io.File;
import java.util.ArrayList;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.TFileWriter;

public class Shutdown extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}
	
	@Override
	public String getCommandText() {
		return "shutdown";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if(channel.equalsIgnoreCase(Main.getBotChannel())) {
			ArrayList<String> channels = new ArrayList<>();
			for (String s : Main.getBot().getChannels()) {
				channels.add(s);
				Main.getBot().sendMessage(s, "I am shutting down, I will automatically rejoin your channel when I restart!");
			}
			TFileWriter.overWriteFile(new File("connectedChannels.txt"), channels);
			System.exit(0);
		}
		return null;
	}

}
