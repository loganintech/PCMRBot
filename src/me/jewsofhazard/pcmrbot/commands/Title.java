package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Title extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "title";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (TwitchUtilities.updateTitle(channel.substring(1),
				parameters[0])) {
			return "Successfully changed the stream title to \"%title%\"!".replace("%title%", parameters[0]);
		} else {
			return "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allow me to do this and so much more!";
		}
	}

}
