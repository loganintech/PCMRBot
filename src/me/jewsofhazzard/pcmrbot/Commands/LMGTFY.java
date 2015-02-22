package me.jewsofhazzard.pcmrbot.Commands;

public class LMGTFY extends Command {

	public LMGTFY(String... params) {
		super(params);
		command="lmgtfy";
	}
	
	@Override
	public String getReply() {
		parameters[0] = parameters[0].replace(' ', '+');
		return "http://lmgtfy.com?q=" + parameters[0];
	}

}
