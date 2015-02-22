package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;
import me.jewsofhazzard.pcmrbot.util.UserLevel;

public class Raffle implements Command {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(MyBotMain.getBot().getRaffle(channel) != null) {
			return "There is already a raffle in progress!";
		}
		UserLevel level=UserLevel.getTypeFromString(parameters[0]);
		if(level == null) {
			return "Improper raffle type! Valid choices are \"everyone\", \"followers\" \"subscribers\"";
		}
		MyBotMain.getBot().addRaffle(channel, new me.jewsofhazzard.pcmrbot.util.Raffle(channel, level));
		return "Raffle started for %level%".replace("%level%", level.toString());
	}

}
