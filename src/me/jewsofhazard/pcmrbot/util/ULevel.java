package me.jewsofhazard.pcmrbot.util;

public enum ULevel {
	Normal,Follower,Subscriber;

	public static ULevel getTypeFromString(String level) {
		level=level.toLowerCase();
		if(level.endsWith("s")) {
			level=level.substring(0, level.length()-1);
		}
		switch(level.toLowerCase()) {
		case "normal":
		case "everyone":
		case "all":
			return Normal;
		case "follower":
			return Follower;
		case "subscriber":
		case "sub":
			return Subscriber;
		default :
			return null;
		}
	}
}
