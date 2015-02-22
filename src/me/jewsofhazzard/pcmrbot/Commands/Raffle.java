package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.UserLevel;

public class Raffle implements Command {

	@Override
	public String execute(String... parameters) {
		String channel = parameters[0];
		if(MyBotMain.getBot().getRaffle(channel) != null) {
			return "There is already a raffle in progress!";
		}
		UserLevel level=UserLevel.getTypeFromString(parameters[1]);
		if(level == null) {
			return "Improper raffle type! Valid choices are \"everyone\", \"followers\" \"subscribers\"";
		}
		MyBotMain.getBot().addRaffle(channel, new me.jewsofhazzard.pcmrbot.util.Raffle(channel, level));
		return "Raffle started for %level%".replace("%level%", level.toString());
	}

}
