package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.CommandsPage;

public class GenerateCommandsPage extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Owner;
	}

	@Override
	public String getCommandText() {
		return "generatecommandspage";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(CommandsPage.createCommandsHTML(channel.substring(1))) {
			return "Created commands page for your custom commands! http://pcmrbot.no-ip.info/commands/%channel%.html".replace("%channel%", channel.substring(1));
		}
		return "It appears you don't have any custom commands! Use !addcom <commandName>|<reply> to create one";
	}

}
