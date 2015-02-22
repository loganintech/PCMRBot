package me.jewsofhazzard.pcmrbot.Commands;

public class SlowClap implements Command {

	@Override
	public String execute(String... parameters) {
		return "%user% claps his hands slowly while walking off into the sunset.".replace("%user%", parameters[0]);
	}

}
