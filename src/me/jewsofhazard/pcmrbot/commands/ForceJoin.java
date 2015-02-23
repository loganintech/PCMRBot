package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class ForceJoin extends Command implements ICommand {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "forcejoin";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			MyBotMain.joinChannel(parameters[0]);
			return "Forcefully joining %channel%".replace("%channel%", parameters[0]);
		}
		return "You can only preform this command from the main bot channel!";
	}

}
