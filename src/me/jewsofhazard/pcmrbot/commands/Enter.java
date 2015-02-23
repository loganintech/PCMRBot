package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Enter extends Command  implements ICommand{

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "enter";
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
