package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class DisableReplies extends Command  implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "disablereplies";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		MyBotMain.getBot().setConfirmationEnabled(channel, false);
		return "%user% has disabled bot replies".replace("%user%", sender);
	}

}
