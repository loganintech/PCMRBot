package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class EnableWelcome extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "enablewelcome";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		Main.getBot().setWelcomeEnabled(channel, true);
		return "Welcome messages have been enabled.";
	}

}
