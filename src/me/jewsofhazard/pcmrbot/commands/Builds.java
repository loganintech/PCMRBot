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
public class Builds extends Command{
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	public String getCommandText() {
		return "builds";
	}
	
	public String execute(String channel, String sender, String... parameters) {	
        return "You can find custom builds and things at http://www.reddit.com/r/PCMasterRace/wiki/builds or https://reddit.com/r/buildapc";
	}
    
}
