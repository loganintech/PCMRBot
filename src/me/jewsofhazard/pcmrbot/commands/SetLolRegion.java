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
 * @author 4thPeriod
 */
public class SetLolRegion extends Command{
    
      @Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "setlolregion";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
            
            if(LeagueUtils.setupClass(parameters[0]).equals("-1")){
                return "Region has been set.";
            }
            else{
                return "Region set has failed, perhaps you used an incorrect region?";
            }
            
                        
	}
    
}
