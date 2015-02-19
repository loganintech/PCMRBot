package me.jewsofhazzard.pcmrbot.util;

import java.util.HashMap;
import java.util.Random;

public enum TType {
	
	CAPS("No need to yell buddy! (Too many Capital letters)"),
	SYMBOLS("Hey! Watch it! (Too many symbols)"),
	EMOTE("We get it, you like using that emote! (Too many emotes)"), 
	LINK("You can't just go posting things williy nilly (Link)"), 
	SPAM("You sure do like to say that, huh? (Spam)"), 
	PARAGRAPH("A bit long winded aren't we? (Too many characters in your message)");
	
	private final String[] messages;
	private final HashMap<String,Integer> offenders;
	
	public String getRandomMessage() {
		Random rand=new Random();
		return messages[rand.nextInt(messages.length)];
	}
	
	public boolean previousOffender(String u) {
		return offenders.containsKey(u);
	}
	
	public void updateOffender(String u, int o) {
		offenders.put(u, o);
	}
	
	public int getOffender(String u) {
		return offenders.get(u);
	}
	
	public void removeOffender(String u) {
		offenders.remove(u);
	}
	
	TType(String... m){
		messages=m;
		offenders=new HashMap<>();
	}
}
