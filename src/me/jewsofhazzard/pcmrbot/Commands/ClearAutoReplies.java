package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;

public class ClearAutoReplies implements Command {

	private String[] parameters;
	
	private String reply;
	
	public ClearAutoReplies(String... params) {
		parameters=params;
		execute();
	}
	
	@Override
	public String getReply() {
		return reply;
	}

	@Override
	public void execute() {
		Database.clearAutoRepliesTable(parameters[0]);
		reply = parameters[1] + " has cleared the auto replies.";
	}

}
