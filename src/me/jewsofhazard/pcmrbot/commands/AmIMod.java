/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

/**
 *
 * @author Hazard
 */
public class AmIMod extends Command {
    
    @Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "amimod";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {	
            return "Do not worry " + sender + " you are a moderator in this channel.";
	}
    
}
