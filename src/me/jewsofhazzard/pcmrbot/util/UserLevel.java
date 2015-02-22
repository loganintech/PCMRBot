package me.jewsofhazzard.pcmrbot.util;

public enum UserLevel {
	Normal,Follower,Subscriber;

	public static UserLevel getTypeFromString(String level) {
		level=level.toLowerCase();
		if(level.endsWith("s"))
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
		return null;
	}
}
