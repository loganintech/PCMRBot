package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Vote implements Command {

	@Override
	public String execute(String... parameters) {
		MyBotMain.getBot().getPoll(parameters[0]).vote(parameters[1], parameters[2]);
		return null;
	}

}
