package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class SlowClap extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "slowclap";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "%user% claps his hands slowly while walking off into the sunset."
				.replace("%user%", sender);
	}

}
