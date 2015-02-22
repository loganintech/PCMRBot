package me.jewsofhazzard.pcmrbot.Commands;

public class DisableReplies implements Command {

	@Override
	public String execute(String... parameters) {
		return "%user% has disabled bot replies".replace("%user%", parameters[0]);
	}

}
