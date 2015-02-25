package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.CommandsPage;

public class Help extends Command  implements ICommand {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "help";
	}

	@Override
	public String execute(String channel, String sender, String...parameters) {
		if(CommandsPage.pageExists(channel.substring(1))) {
			return "You can find all of my commands at http://pcmrbot.no-ip.info/commands and all of the commands for %s at http://pcmrbot.no-ip.info/commands/%s.html.".replace("%channel%", channel.substring(1));
		}
		return "You can find all of my commands at http://pcmrbot.no-ip.info/commands.";
	}

}
