package me.jewsofhazzard.pcmrbot.Commands;

public class Fatality implements Command {

	@Override
	public String execute(String... parameters) {
		return String.format("It turns out that %s has killed %s... Run, RUN!", parameters[0], parameters[1]);
	}

}
