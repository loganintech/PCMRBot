package me.jewsofhazzard.pcmrbot.Commands;

public class Slow implements Command {

	private boolean isSlow = false;

	@Override
	public String execute(String...parameters) {
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
