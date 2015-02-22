package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Broadcast implements Command {
	
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(channel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			String message=parameters[0];
			for (String s : MyBotMain.getBot().getChannels()) {
				if (!s.equalsIgnoreCase("#pcmrbot")) {
					MyBotMain.getBot().sendMessage(s,	message);
				}
			}
			return "I have sent %message% to all channels.".replace("%message%", message);
		}
		return "You can only preform this command from the main bot channel!";
	}

}
