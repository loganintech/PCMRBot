package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Commercial extends Command {

	private CommandLevel level = CommandLevel.Owner;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "commercial";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (parameters.length < 2) {
			TwitchUtilities.runCommercial(channel.substring(1));
			return "Running a default length commercial on %channel%".replace(
					"%channel%", channel);
		}
		String length = parameters[0];
		int time = 0;
		try {
			time = Integer.valueOf(length);
		} catch (NumberFormatException e) {
			TwitchUtilities.runCommercial(channel.substring(1));
			return String
					.format("%s is not a valid time. Running a default length commercial on %s!",
							length, channel);
		}
		if (time <= 180 && time % 30 == 0) {
			TwitchUtilities.runCommercial(channel.substring(1), time);
			return String.format("Running a %s for %length% seconds on %s",
					length, channel);
		} else {
			TwitchUtilities.runCommercial(channel.substring(1));
			return String
					.format("%s is not a valid time. Running a default length commercial on %s!",
							length, channel);
		}
	}

}
