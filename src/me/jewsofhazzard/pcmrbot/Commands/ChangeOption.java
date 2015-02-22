package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.Options;

public class ChangeOption {

	public String [] p;
	
	public ChangeOption(String ... params){
		
		p = params;
		execute();
	}
	
	public void execute(){
		
		String[] option = p[1].split("[|]");
		
		if (option[0].equalsIgnoreCase("paragraph")) {
			Database.setOption(p[0], Options.paragraphLength,
					option[1]);
			getReply("You have changed the paragraph length to " + option[1]
							+ ".");
		} else if (option[0].equalsIgnoreCase("emotes")) {
			Database.setOption(p[0], Options.numEmotes, option[1]);
			getReply("You have changed the emote cap to "
					+ option[1] + ".");
		} else if (option[0].equalsIgnoreCase("symbol")) {
			Database.setOption(p[0], Options.numSymbols, option[1]);
			getReply("You have changed the symbol cap to "
					+ option[1] + ".");
		} else if (option[0].equalsIgnoreCase("caps")) {
			Database.setOption(p[0], Options.numCaps, option[1]);
			getReply("You have changed the capitals cap to " + option[1] + ".");
		} else {
			
			getReply("I am sorry, but you have tried to change a type of value that is not supported. Valid options are \"symbol,\" \"emotes,\" or \"paragraph,\"");
		}
		
		
	}
	
	public String getReply(String reply){
		
		return reply;
		
	}
}


