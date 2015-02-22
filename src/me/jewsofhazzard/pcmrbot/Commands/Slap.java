package me.jewsofhazzard.pcmrbot.Commands;

public class Slap implements Command {

	@Override
	public String execute(String... parameters) {
		return "slaps %target% with a raw fish".replace("%target%", parameters[0]);
	}

}
