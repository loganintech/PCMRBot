package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Permit extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "permit";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String target=parameters[0];
		if(!MyBotMain.getBot().isPermitted(channel, target)) {
			MyBotMain.getBot().addPermit(new me.jewsofhazard.pcmrbot.util.Permit(target, channel), target);
			return "Permitted %sender% to post a link! You have three minutes".replace("%sender%", target);
		}
		return "%sender% has already been permitted to post a link!".replace("%sender%", target);
	}

}
