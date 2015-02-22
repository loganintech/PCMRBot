package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;

public class ClearAutoReplies implements Command {

	@Override
	public String execute(String...parameters) {
		Database.clearAutoRepliesTable(parameters[0]);
		return parameters[1] + " has cleared the auto replies.";
	}

}
