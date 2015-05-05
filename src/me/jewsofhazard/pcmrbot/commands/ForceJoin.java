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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.jewsofhazard.pcmrbot.Main;
import static me.jewsofhazard.pcmrbot.Main.joinChannel;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.TFileReader;
import org.apache.commons.io.IOUtils;

public class ForceJoin extends Command {

    @Override
    public CLevel getCommandLevel() {
        return CLevel.Mod;
    }

    @Override
    public String getCommandText() {
        return "forcejoin";
    }

    @Override
    public String execute(String channel, String sender, String... parameters) {
        if (channel.equalsIgnoreCase(Main.getBotChannel())) {
            if (parameters[0].length() >= 1) {
                if (parameters[0].startsWith("#")) {
                    Main.joinChannel(parameters[0], false);
                } else {
                    Main.joinChannel("#" + parameters[0], false);
                }
                return "Forcefully joining %channel%".replace("%channel%", parameters[0]);
            }
            if (parameters[0].length() == 0) {
                File f = new File("connectedChannels.txt");
		if (f.exists()) {
			for (String s : TFileReader.readFile(f)) {
				joinChannel(s, true);
			}
		}
            }

            return "You can only preform this command from the main bot channel!";
        }
        return null;
    }
}
