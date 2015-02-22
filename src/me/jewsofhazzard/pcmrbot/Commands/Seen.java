package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Seen implements Command {

	@Override
	public String execute(String... parameters) {
		String sender = parameters[0];
		String target = parameters[1];
		String seen=MyBotMain.getBot().getChatPostSeen(target);
		if (seen !=null) {
			String[] info = seen.split("[|]");
			return String.format("%s, I last saw %s in %s on %s.", sender, target, info[0], info[1]);
		} else {
			return String.format("I'm sorry %s, I haven't seen %s.", sender, target);
		}
	}

}
