package me.jewsofhazard.pcmrbot.cmd;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public abstract class Command {

	public abstract CommandLevel getCommandLevel();

	public abstract String getCommandText();

	public abstract String execute(String channel, String sender,
			String... parameters);

}
