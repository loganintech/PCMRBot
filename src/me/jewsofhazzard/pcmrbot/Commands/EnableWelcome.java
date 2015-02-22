package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class EnableWelcome implements Command {

	@Override
	public String execute(String... parameters) {
		MyBotMain.getBot().setWelcomeEnabled(parameters[0], true);
		return "Welcome messages have been enabled.";
	}

}
