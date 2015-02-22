package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;

public class Poll implements Command {

	@Override
	public String execute(String... parameters) {
		String channel = parameters[0];
		String[] voteOptions = parameters[1].split("[|]");
		
		String[] answers = new String[voteOptions.length - 2];
		if (answers.length < 2) {
			return	"You must provide at least two answers!";
		} else if (MyBotMain.getBot().hasPoll(channel)) {
			return "There is already a poll happenning in your channel, wait for it to complete first!";
		}
		
		for (int i = 2; i < voteOptions.length; i++) {
			answers[i - 2] = voteOptions[i];
		}
		
		MyBotMain.getBot().addPoll(channel, new me.jewsofhazzard.pcmrbot.util.Poll(voteOptions[1], answers, Integer.valueOf(voteOptions[0])).start());
		return voteOptions[1];
	}

}
