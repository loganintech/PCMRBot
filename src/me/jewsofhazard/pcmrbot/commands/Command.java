package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public abstract class Command implements ICommand{

	

	@Override
	public abstract CommandLevel getCommandLevel();

	@Override
	public abstract String getCommandText();

	@Override
	public abstract String execute(String channel, String sender, String... parameters);

}
