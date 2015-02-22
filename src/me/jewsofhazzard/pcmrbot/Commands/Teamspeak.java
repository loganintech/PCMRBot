package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Teamspeak implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "teamspeak";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "You can download teamspeak here:  http://www.teamspeak.com/?page=downloads";
	}

}
