package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Broadcast implements Command {

	@Override
	public String execute(String... parameters) {
		String message=parameters[0];
		for (String s : MyBotMain.getBot().getChannels()) {
			if (!s.equalsIgnoreCase("#pcmrbot")) {
				MyBotMain.getBot().sendMessage(s,	message);
			}
		}
		return "I have sent %message% to all channels.".replace("%message%", message);
	}

}
