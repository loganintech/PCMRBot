package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class JoinMe {

	private String [] p;
	
	public JoinMe(String... params){
		
		p = params;
		execute();
		
	}
	



	public void execute(){
		
		if(p[1].equalsIgnoreCase("true")){
			
			new MyBotMain("#" + p[0]);
			
			
		}
		
	}
		
	public String getReply(){
		
		return "I have joined " + p[0] + "'s channel.";
		
	}
}