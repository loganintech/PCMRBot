package me.jewsofhazzard.pcmrbot.Commands;

public class LMGTFY implements Command {

	private String[] parameters;
	
	private String reply;
	
	public LMGTFY(String... params) {
		parameters=params;
		execute();
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
