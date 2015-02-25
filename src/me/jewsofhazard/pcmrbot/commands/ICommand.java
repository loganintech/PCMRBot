package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public interface ICommand { 
	String execute(String channel, String sender, String... parameters);
	CLevel getCommandLevel();
	String getCommandText();
}
