package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Poll extends Command implements ICommand {

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
		
		MyBotMain.getBot().addPoll(channel, new me.jewsofhazard.pcmrbot.util.Poll(voteOptions[1], answers, Integer.valueOf(voteOptions[0])).start());
		if(MyBotMain.getBot().getConfirmationReplies(channel)) {
			return voteOptions[1];
		}
		return null;
	}

}
