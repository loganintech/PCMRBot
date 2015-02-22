package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Slow implements Command {

	private boolean isSlow = false;
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (parameters[0].equalsIgnoreCase("true")) {

			if (!isSlow) {

				return "/slow";

			} else {

				return "/slowoff";

			}

		} else {

			return "I am sorry but the bot is not moderator in your channel and cannot run slowmode.";

		}
	}

}
