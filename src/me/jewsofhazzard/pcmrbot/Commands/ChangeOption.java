package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.Options;

public class ChangeOption implements Command {
	
	public String execute(String...parameters){
		String channel = parameters[0];
		String[] options = parameters[1].split("[|]");
		
		if (options[0].equalsIgnoreCase("paragraph")) {
			Database.setOption(channel, Options.paragraphLength,
					options[1]);
			return "You have changed the paragraph length to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("emotes")) {
			Database.setOption(channel, Options.numEmotes, options[1]);
			return "You have changed the emote cap to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("symbol")) {
			Database.setOption(channel, Options.numSymbols, options[1]);
			return "You have changed the symbol cap to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("caps")) {
			Database.setOption(channel, Options.numCaps, options[1]);
			return "You have changed the capitals cap to %option%.".replace("%option%", options[1]);
		} else {
			return "I am sorry, but you have tried to change a type of value that is not supported. Valid options are \"symbol,\" \"emotes,\" or \"paragraph,\"";
		}
		
		
	}
}


