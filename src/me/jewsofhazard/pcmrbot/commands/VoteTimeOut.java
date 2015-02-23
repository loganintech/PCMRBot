package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

import org.jibble.pircbot.User;

public class VoteTimeOut extends Command implements ICommand {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "voteto";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String kickee=parameters[0];
		
		boolean exists = false;
		for(User u:MyBotMain.getBot().getUsers(channel)) {
			if(u.getNick().equalsIgnoreCase(kickee)) {
				exists=true;
			}
		}
		if(exists) {
			MyBotMain.getBot().addVoteTimeOut(channel, new me.jewsofhazard.pcmrbot.util.VoteTimeOut(channel, kickee));
			return "The channel has begun a vote to timeout %toKick%. Type !voteTO to place your vote. To vote no just do not vote.".replace("%toKick%", kickee);
		}
		boolean startVote = true;
		if(getCommandText().equalsIgnoreCase(kickee.substring(1))) {
			startVote = false;
		}
		if(startVote) {
			return "%kickee% is not in the channel!".replace("%kickee%", kickee);
		}
		return MyBotMain.getBot().getVoteTimeOut(channel).addVote(sender);
	}

}
