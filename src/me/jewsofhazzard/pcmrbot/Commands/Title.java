package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.twitch.TwitchUtilities;

public class Title implements Command {

	private String[] parameters;
	
	private String reply;
	
	public Title(String... params) {
		parameters=params;
	}
	
	@Override
	public String getReply() {
		return reply;
	}

	@Override
	public void execute() {
		if (TwitchUtilities.updateTitle(parameters[0].substring(1),
				parameters[1])) {
			reply="Successfully changed the stream title to \""
							+ parameters[1] + "\"!";
		} else {
			reply = "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allows me to do this and so much more!";
		}
	}

}
