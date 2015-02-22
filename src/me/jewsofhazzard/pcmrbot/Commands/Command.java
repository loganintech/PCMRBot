package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public interface Command {
	
	public abstract String execute(String channel, String sender, String... parameters);
	public abstract CommandLevel getCommandLevel();
}
