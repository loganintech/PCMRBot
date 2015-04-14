/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;
import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;



public class WolframAlpha extends Command {
    
    private static final String appid = "XXXXX";
    
    	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}
	
	@Override
	public String getCommandText() {
		return "wolfit";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {

        WAEngine engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(parameters[0]);
        
        WAQueryResult queryResult;
        
        try {
            queryResult = engine.performQuery(query);
            for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                         for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    return ((WAPlainText) element).getText();
                                }
                            }
                        }
                    }
                }
            
        } catch (WAException ex) {
           return "I'm sorry. I do not understand.";
        }
        
        return null;
    }
}
