package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.database.Database;

public class AddAutoReply {

	String [] p;
	String reply;
	
	public AddAutoReply(String... params){
		
		p = params;
		execute();
		
	}
	
	public void execute(){
		
		p[0] = p[0].substring(p[0].indexOf(" ") + 1);
		String[] cutUp = p[0].split("[|]");
		StringBuilder keywords = new StringBuilder();
		for (int i = 0; i < cutUp.length - 2; i++) {

			keywords.append(cutUp[i] + ",");

		}
		keywords.append(cutUp[cutUp.length - 2]);
		String reply = cutUp[cutUp.length - 1];
		Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
				+ p[1].substring(1) + "AutoReplies VALUES(\'"
				+ keywords.toString() + "\' , '" + reply + "\')");

		
				reply = "Added auto reply: "
						+ reply
						+ " When all of the following key words are said in a p[0]: "
						+ keywords.toString();
		
		
	}
	
	public String getReply(){
		
		return reply;
		
	}
	
}
