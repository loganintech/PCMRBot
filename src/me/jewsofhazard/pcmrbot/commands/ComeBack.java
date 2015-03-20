/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

/**
 *
 * @author 4thPeriod
 */
public class ComeBack extends Command{
    
    
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}
	
	
	public String getCommandText() {
		return "comeback";
	}
	
	public String execute(String channel, String sender, String...parameters) {
		return String.format("%s COME BACK! YOU CAN BLAME IT ALL ON ME!", parameters[0]);    
	}
}
