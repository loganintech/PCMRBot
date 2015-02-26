/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.commands;

import java.util.Random;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class RNG extends Command {
    
    private boolean inBounds = false;
    
    @Override
    public CLevel getCommandLevel(){
        
        return CLevel.Normal;
        
    }
    
    @Override
    public String getCommandText(){
        
        return "rng";
        
    }
    
    @Override
    public String execute(String channel, String sender, String... parameters){
    
        try{
        Random rand = new Random();
        return "Your random number is " + Integer.toString(rand.nextInt(Integer.valueOf(parameters[0]) + 1)) + ".";
        }
        catch(NumberFormatException e){
        return "You have to type a valid number.";
        }
        
    }
    
}
