package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class LeaveMe {

	String [] p;
	String reply;
	
	public LeaveMe(String... params){
		
		p = params;
		execute();
	}
	
	
	public void execute(){
		
		if (MyBotMain.getConnectedChannel(p[0]) != null
				&& p[0] != "#pcmrbot") {
				reply = ("I have disconnected from "+ p[0] + "'s channel.");
				MyBotMain.getConnectedChannel(p[0]).partChannel(p[0]);
				MyBotMain.getConnectedChannels().remove(p[0]);
				} else {
				reply ="Sorry "	+ p[0].substring(1)	+ ", I cannot allow you to disconnect me from my home channel.";
				}
		
		
	}
	
	public String getReply(){
		
		return reply;
		
	}
	
	
}





