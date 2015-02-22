package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public interface Command { 
	String execute(String channel, String sender, String... parameters);
	CommandLevel getCommandLevel();
	String getCommandText();
}
