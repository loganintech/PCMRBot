package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Game extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "game";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (TwitchUtilities.updateGame(channel.substring(1),
				parameters[0])) {
			return "Successfully changed the stream game to \""
							+ parameters[0] + "\"!";
		} else {
			return "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allow me to do this and so much more!";
		}
	}

}
