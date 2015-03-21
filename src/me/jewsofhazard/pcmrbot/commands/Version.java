/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;
import com.jcabi.manifests.Manifests;

/**
 *
 * @author Hazard
 */
public class Version extends Command {
    
    @Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}
	
	@Override
	public String getCommandText() {
		return "version";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "I am curently version " + Manifests.read("Build-Version")  + ".";
	}
    
}
