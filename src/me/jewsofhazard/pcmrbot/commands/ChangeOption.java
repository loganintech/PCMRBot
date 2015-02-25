package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.TOptions;

public class ChangeOption extends Command  implements ICommand {
	
	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "changeoption";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters){
		String[] options = parameters[0].split("[|]");
		try {
			if (options[0].equalsIgnoreCase("paragraph")) {
				Database.setOption(channel.substring(1), TOptions.paragraphLength, Integer.valueOf(options[1]));
				return "You have changed the paragraph length to %option%.".replace("%option%", options[1]);
			} else if (options[0].equalsIgnoreCase("emotes")) {
				Database.setOption(channel.substring(1), TOptions.numEmotes, Integer.valueOf(options[1]));
				return "You have changed the emote cap to %option%.".replace("%option%", options[1]);
			} else if (options[0].equalsIgnoreCase("symbol")) {
				Database.setOption(channel.substring(1), TOptions.numSymbols, Integer.valueOf(options[1]));
				return "You have changed the symbol cap to %option%.".replace("%option%", options[1]);
			} else if (options[0].equalsIgnoreCase("caps")) {
				Database.setOption(channel.substring(1), TOptions.numCaps, Integer.valueOf(options[1]));
				return "You have changed the capitals cap to %option%.".replace("%option%", options[1]);
			} else if (options[0].equalsIgnoreCase("links")) {
				if (options[1].toLowerCase().equalsIgnoreCase("enable")) {
					Database.setOption(channel.substring(1), TOptions.link, 0);
					return "Enabled link protection!";
				} else if (options[1].toLowerCase().equalsIgnoreCase("disable")) {
					Database.setOption(channel.substring(1), TOptions.link, -1);
					return "Disabled link protection!";
				} else {
					return "Valid values for links are: Enable and Disable";
				}
			}	
		} catch (NumberFormatException e) {
			return "You must pass a number for the value of %option%".replace("%option%", options[0]);
		}
		return "I am sorry, but you have tried to change a type of value that is not supported. Valid options are \"symbol,\" \"emotes,\" or \"paragraph,\"";
	}
}


