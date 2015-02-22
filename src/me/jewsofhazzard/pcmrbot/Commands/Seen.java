package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Seen implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "seen";
	}
		
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String target = parameters[0];
		String seen=MyBotMain.getBot().getChatPostSeen(target);
		if (seen !=null) {
			String[] info = seen.split("[|]");
			return String.format("%s, I last saw %s in %s on %s.", sender, target, info[0], info[1]);
		} else {
			return String.format("I'm sorry %s, I haven't seen %s.", sender, target);
		}
	}

}
