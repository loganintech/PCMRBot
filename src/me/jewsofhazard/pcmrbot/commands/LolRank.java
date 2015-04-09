/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.league.LeagueUtils;
import me.jewsofhazard.pcmrbot.util.CLevel;

/**
 *
 * @author Hazard
 */
public class LolRank extends Command {
    
    @Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "lolrank";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
            
            if(parameters.length > 0){
            
                return LeagueUtils.getSummonerRank();
                
            } else {
            
            return "You must search for someone's summoner name.";
            
            }
            
                        
	}
    
}
