package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.Options;

public class ChangeOption extends Command  implements ICommand {
	
	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "changeoption";
	}
	
	@Override
	public String execute(String channel, String sender, String...parameters){
		String[] options = parameters[0].split("[|]");
		
		if (options[0].equalsIgnoreCase("paragraph")) {
			Database.setOption(channel.substring(0), Options.paragraphLength,
					options[1]);
			return "You have changed the paragraph length to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("emotes")) {
			Database.setOption(channel.substring(0), Options.numEmotes, options[1]);
			return "You have changed the emote cap to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("symbol")) {
			Database.setOption(channel.substring(0), Options.numSymbols, options[1]);
			return "You have changed the symbol cap to %option%.".replace("%option%", options[1]);
		} else if (options[0].equalsIgnoreCase("caps")) {
			Database.setOption(channel.substring(0), Options.numCaps, options[1]);
			return "You have changed the capitals cap to %option%.".replace("%option%", options[1]);
		} else {
			return "I am sorry, but you have tried to change a type of value that is not supported. Valid options are \"symbol,\" \"emotes,\" or \"paragraph,\"";
		}
		
		
	}
}


