package me.jewsofhazzard.pcmrbot.Commands;

public class Slow extends Command {
	
	public Slow (String... params){
		
		super(params);
		command = "slow";
		
	}
	
	private boolean isSlow = false;
	
	public String getReply(){
	
		if(parameters[0].equalsIgnoreCase("true")){	
		
			if(!isSlow){
			
			return "/slow";
				
			}
			else{
				
			return "/slowoff";
				
			}
	
		}
	
		else{
			
			return "I am sorry but the bot is not moderator in your channel and cannot run slowmode.";
			
		}
	}
	
}
