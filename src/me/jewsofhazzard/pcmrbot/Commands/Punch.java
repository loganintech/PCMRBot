package me.jewsofhazzard.pcmrbot.Commands;

public class Punch implements Command {

	@Override
	public String execute(String... parameters) {
		return String.format("%s punches %s.", parameters[0], parameters[1]);
	}

}
