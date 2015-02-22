package me.jewsofhazzard.pcmrbot.Commands;

public class LMGTFY implements Command {

	private String[] parameters;
	
	private String reply;
	
	public LMGTFY(String... params) {
		parameters=params;
	}
	
	@Override
	public String getReply() {
		return reply;
	}

	@Override
	public void execute() {
		reply = "http://lmgtfy.com?q=" + parameters[0].replace(' ', '+');
	}

}
