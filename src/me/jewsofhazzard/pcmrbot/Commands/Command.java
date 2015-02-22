package me.jewsofhazzard.pcmrbot.Commands;

public interface Command {
	
	public abstract String getReply();
	
	public abstract void execute();
}
