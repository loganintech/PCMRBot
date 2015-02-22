package me.jewsofhazzard.pcmrbot.Commands;

public class Command {

	protected String command;
	protected String[] parameters;
	
	public Command(String... params) {
		command="!command";
		parameters=params;
	}
	
	public String getReply() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<parameters.length-2;i++) {
			sb.append(parameters[i]+", ");
		}
		sb.append(parameters[parameters.length-2]);
		return sb.toString();
	}
	
	public String getCommand() {
		return command;
	}
}
