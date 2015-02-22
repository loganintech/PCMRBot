package me.jewsofhazzard.pcmrbot.Commands;

public class Me implements Command {

	@Override
	public String execute(String... parameters) {
		return "/me %message%".replace("%message%", parameters[0]);
	}

}
