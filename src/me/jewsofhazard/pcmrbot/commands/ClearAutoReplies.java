package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class ClearAutoReplies extends Command  implements ICommand {

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
		Database.clearAutoRepliesTable(channel.substring(1));
		return sender + " has cleared the auto replies.";
	}

}
