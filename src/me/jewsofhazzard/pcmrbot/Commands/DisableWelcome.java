package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class DisableWelcome implements Command {

	@Override
	public String execute(String... parameters) {
		MyBotMain.getBot().setWelcomeEnabled(parameters[0], false);
		return "Welcome messages have been disabled.";
	}

}
