package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.MyBotMain;
import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class Poll implements Command {

	private CommandLevel level=CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "poll";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String[] voteOptions = parameters[0].split("[|]");
		
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
