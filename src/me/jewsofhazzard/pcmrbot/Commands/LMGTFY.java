package me.jewsofhazzard.pcmrbot.Commands;

public class LMGTFY implements Command {

	private String[] parameters;
	
	public LMGTFY(String... params) {
		parameters=params;
	}
	
	@Override
	public String getReply() {
		parameters[0] = parameters[0].replace(' ', '+');
		return "http://lmgtfy.com?q=" + parameters[0];
	}

}
