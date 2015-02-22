package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class ClearAutoReplies implements Command {

	private CommandLevel level=CommandLevel.Owner;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "clearautoreplies";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters) {
		Database.clearAutoRepliesTable(channel);
		return parameters[1] + " has cleared the auto replies.";
	}

}
