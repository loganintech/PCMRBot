package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Enter implements Command{

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		try {
			MyBotMain.getBot().getRaffle(channel).enter(sender);
		} catch(NullPointerException e) {
			return "There is not currently a raffle happenning in %channel%".replace("%channel%", channel);
		}
		return null;
	}

}
