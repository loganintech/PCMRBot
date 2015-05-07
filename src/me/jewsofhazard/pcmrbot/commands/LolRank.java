/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.external.league.LeagueUtils;
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
            
            try {
                if(parameters.length == 2){
                    return LeagueUtils.getSummonerRank(parameters[0], parameters[1]);
                }
                else if(parameters.length > 2){
                    return "You must put the summoner name as one string. To search for GH Fifty, use ghfifty.";
                }
                else{
                    return LeagueUtils.getSummonerRank(parameters[0]);
                }

            } catch(Exception e) {
            
            return "You must search for someone's summoner name and region only.";
            
            }
            
                        
	}
    
}
