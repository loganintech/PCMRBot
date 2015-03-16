/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

/**
 *
 * @author Hazard
 */
public class Mods extends Command{
	
	private static final Logger logger = Logger.getLogger(Mods.class+"");
    
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
        ResultSet rs=Database.getMods(channel.substring(1));
        StringBuilder sb = new StringBuilder();
        try {
			while(rs.next()) {
				sb.append(rs.getString(1)+", ");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an issue getting the mods for the channel", e);
		}
        return "The mods of this channel: " + sb.toString().substring(0, sb.toString().length()-2);
            
	}
    
}
