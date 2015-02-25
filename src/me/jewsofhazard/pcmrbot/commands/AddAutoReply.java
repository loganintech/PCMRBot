package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class AddAutoReply extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "addautoreply";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String[] cutUp = parameters[0].split("[|]");
		StringBuilder keywords = new StringBuilder();
		for (int i = 0; i < cutUp.length - 2; i++) {

			keywords.append(cutUp[i] + ",");

		}
		if (cutUp.length != 0) {
			keywords.append(cutUp[cutUp.length - 2]);
		} else {
			keywords.append(parameters[0]);
		}
		String reply = cutUp[cutUp.length - 1];
		Database.addAutoReply(channel.substring(1), keywords.toString(), reply);
		return String
				.format("Added auto reply: \"%s\"! Which will be said when all of the following key words are said in %s: %s",
						reply, channel.substring(1), keywords.toString());
	}

}
