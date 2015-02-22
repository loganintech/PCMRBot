package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Enter implements Command{

	@Override
	public String execute(String... parameters) {
		String channel=parameters[0];
		try {
			MyBotMain.getBot().getRaffle(channel).enter(parameters[1]);
		} catch(NullPointerException e) {
			return "There is not currently a raffle happenning in %channel%".replace("%channel%", channel);
		}
		return null;
	}

}
