package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Enter extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "enter";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		try {
			Main.getBot().getRaffle(channel).enter(sender);
		} catch (NullPointerException e) {
			return "There is not currently a raffle happenning in %channel%"
					.replace("%channel%", channel);
		}
		return null;
	}

}
