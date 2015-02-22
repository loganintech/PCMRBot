package me.jewsofhazzard.pcmrbot.Commands;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import me.jewsofhazzard.pcmrbot.database.Database;

public class CommandParser {
	private static HashMap<String, Command> commands;
	
	public static void init() {
		commands=new HashMap<>();
		Reflections r = new Reflections("me.jewsofhazzard.pcmrbot.Commands");
		// Find all commands
		Set<Class<? extends Command>> commandClassList = r.getSubTypesOf(Command.class);
		
		// Add command instances to the commands hash map
		for(Class<? extends Command> cClass: commandClassList){
			
			try {
				
				Command c = cClass.newInstance();
				commands.put(c.getCommandText(), c);
				
			} catch (Exception e) {
				// Ignore commands that fail to initialize
			}

		}
	}
	
	public static String parse(String command, String sender, String channel, String parameters) {
		Command c=commands.get(command);
		if(c != null && hasAccess(c, sender, channel)) {
			return c.execute(channel, sender, parameters);
		}
		return null;
	}
	
	private static boolean hasAccess(Command c, String sender, String channel) {
		switch(c.getCommandLevel()) {
		case Mod:
			return Database.isMod(sender, channel);
		case Owner:
			return sender.equalsIgnoreCase(channel.substring(1));
		case Normal:
			return true;
		}
		return false;
	}
}
