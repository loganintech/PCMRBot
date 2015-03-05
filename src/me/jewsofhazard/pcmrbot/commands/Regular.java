package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Regular extends Command{

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "reg";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(parameters.length!=2){
			return "Hey, thats not right, use !reg <add|remove> <user>";
		}
		if(parameters[0].equals("add")){
			if(Database.addRegular(channel.substring(1), parameters[1])) {
				return "Succelfully added %user% to the regulars list".replace("%user%", parameters[1]);
			}
			return "There was an issue adding %user% to the regulars list!".replace("%user%", parameters[1]);
		} else if(parameters[0].equals("remove")) {
			if(Database.delRegular(channel.substring(1), parameters[1])) {
				return "Succelfully removed %user% from the regulars list".replace("%user%", parameters[1]);
			}
			return "There was an issue removing %user% from the regulars list!".replace("%user%", parameters[1]);
		} else {
			return "Hey, thats not right, use !reg <add|remove>|<user>";
		}
	}

}
