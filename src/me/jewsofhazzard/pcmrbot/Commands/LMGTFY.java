package me.jewsofhazzard.pcmrbot.Commands;

public class LMGTFY implements Command {

	@Override
	public String execute(String...parameters) {
		return "http://lmgtfy.com?q=" + parameters[0].replace(' ', '+');
	}

}
