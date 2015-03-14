/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.util.CLevel;
import java.util.ArrayList;

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
	me.jewsofhazard.pcmrbot.IRCBot bot = new me.jewsofhazard.pcmrbot.IRCBot();
        me.jewsofhazard.pcmrbot.database.Database database = new me.jewsofhazard.pcmrbot.database.Database();
        
        String list = "";
        
        for(int i = 0; i < bot.getUsers(channel).length; i++){
        
        if(database.isMod(bot.getUsers(channel)[i].getNick(), channel.substring(1))){}
        
        list += bot.getUsers(channel)[i].getNick() + " ";
        
        }
            
            return "The mods of this channel: " + list;
            
	}
    
}
