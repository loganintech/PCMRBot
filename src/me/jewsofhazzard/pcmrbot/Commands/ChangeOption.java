package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;
import me.jewsofhazzard.pcmrbot.util.Options;

public class ChangeOption implements Command {
	
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters){
		String[] options = parameters[0].split("[|]");
		
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


