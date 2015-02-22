package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.twitch.TwitchUtilities;

public class Game implements Command {

	@Override
	public String execute(String...parameters) {
		if (TwitchUtilities.updateGame(parameters[0].substring(1),
				parameters[1])) {
			return "Successfully changed the stream game to \""
							+ parameters[1] + "\"!";
		} else {
			return "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allows me to do this and so much more!";
		}
	}

}
