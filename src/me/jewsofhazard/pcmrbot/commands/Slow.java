package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Slow extends Command implements ICommand {

	private boolean isSlow = false;
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "slow";
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
