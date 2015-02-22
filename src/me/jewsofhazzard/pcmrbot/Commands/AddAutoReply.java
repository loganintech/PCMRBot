package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;

public class AddAutoReply implements Command{
	
	public String execute(String... parameters){
		String[] cutUp = parameters[0].split("[|]");
		StringBuilder keywords = new StringBuilder();
		for (int i = 0; i < cutUp.length - 2; i++) {

			keywords.append(cutUp[i] + ",");

		}
		keywords.append(cutUp[cutUp.length - 2]);
		String reply = cutUp[cutUp.length - 1];
		String channel = parameters[1];
		Database.addAutoReply(channel.substring(1), keywords.toString(), reply);
		return String.format("Added auto reply: \"%s\"! Which will be said when all of the following key words are said in %s: %s", reply, channel.substring(1), keywords.toString());
	}
	
}
