package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Gabe extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "gabe";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "You can find all of the official pcmasterrace links at http://www.reddit.com/r/pcmasterrace/comments/2verur/check_out_our_official_communities_on_steam/";
	}

}
