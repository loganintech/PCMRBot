/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

import org.jibble.pircbot.User;

/**
 *
 * @author Hazard
 */
public class Mods extends Command{
    
    @Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}
	
	@Override
	public String getCommandText() {
		return "mods";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		User[] users= Main.getBot().getUsers(channel);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < users.length; i++){
			if(Database.isMod(users[i].getNick(), channel.substring(1))){}
				sb.append(users[i].getNick() + ",");
			}
        return "The mods of this channel: " + sb.toString().substring(0, sb.toString().length()-1);
            
	}
    
}
