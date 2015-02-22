package me.jewsofhazzard.pcmrbot.Commands;

public class Fight implements Command {

	@Override
	public String execute(String... parameters) {
		return String.format("%s puts up his digs in preparation to punch %s.", parameters[0], parameters[1]);
	}

}
