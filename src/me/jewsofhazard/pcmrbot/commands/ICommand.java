package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public interface ICommand { 
	String execute(String channel, String sender, String... parameters);
	CommandLevel getCommandLevel();
	String getCommandText();
}
