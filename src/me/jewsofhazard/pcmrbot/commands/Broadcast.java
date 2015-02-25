package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Broadcast extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "broadcast";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (channel.equalsIgnoreCase(Main.getBotChannel())) {
			String message = parameters[0];
			for (String s : Main.getBot().getChannels()) {
				if (!s.equalsIgnoreCase("#pcmrbot")) {
					Main.getBot().sendMessage(s, message);
				}
			}
			return "I have sent %message% to all channels.".replace(
					"%message%", message);
		}
		return "You can only preform this command from the main bot channel!";
	}

}
