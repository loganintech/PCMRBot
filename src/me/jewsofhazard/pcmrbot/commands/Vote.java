package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Vote extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "vote";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		Main.getBot().getPoll(channel).vote(sender, parameters[0]);
		return null;
	}

}
