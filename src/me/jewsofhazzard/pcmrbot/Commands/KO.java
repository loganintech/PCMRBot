package me.jewsofhazzard.pcmrbot.Commands;

public class KO implements Command {

	@Override
	public String execute(String... parameters) {
		return String.format("%s knocks out %s.", parameters[0], parameters[1]);
	}

}
