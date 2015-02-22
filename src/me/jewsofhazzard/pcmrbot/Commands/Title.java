package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.twitch.TwitchUtilities;

public class Title implements Command {

	@Override
	public String execute(String...parameters) {
		if (TwitchUtilities.updateTitle(parameters[0].substring(1),
				parameters[1])) {
			return "Successfully changed the stream title to \"%title%\"!".replace("%title%", parameters[0]);
		} else {
			return "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allows me to do this and so much more!";
		}
	}

}
