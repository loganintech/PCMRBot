package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Broadcast extends Command  implements ICommand {
	
	private CommandLevel level=CommandLevel.Mod;

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
		if(channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			String message=parameters[0];
			for (String s : MyBotMain.getBot().getChannels()) {
				if (!s.equalsIgnoreCase(MyBotMain.getBotChannel())) {
					MyBotMain.getBot().sendMessage(s,	message);
				}
			}
			return "I have sent %message% to all channels.".replace("%message%", message);
		}
		return "You can only preform this command from the main bot channel!";
	}

}
