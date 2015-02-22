package me.jewsofhazzard.pcmrbot.Commands;

public class Slow implements Command {

	private String[] parameters;

	private String reply;

	public Slow(String... params) {

		parameters = params;

	}

	private boolean isSlow = false;

	public String getReply() {
		return reply;
	}

	@Override
	public void execute() {
		if (parameters[0].equalsIgnoreCase("true")) {

			if (!isSlow) {

				reply = "/slow";

			} else {

				reply = "/slowoff";

			}

		} else {

			reply = "I am sorry but the bot is not moderator in your channel and cannot run slowmode.";

		}
	}

}
