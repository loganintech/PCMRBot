package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Teamspeak extends Command {

	private CommandLevel level = CommandLevel.Normal;

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
