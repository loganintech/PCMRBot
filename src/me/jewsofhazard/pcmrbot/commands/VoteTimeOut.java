package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

import org.jibble.pircbot.User;

public class VoteTimeOut extends Command implements ICommand {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "voteto";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		
		if(MyBotMain.getBot().getVoteTimeOut(channel) != null) {
			return "There is already a vote to timeout a user happenning in this channel. If you were trying to vote just type \"!voteTO\"!";
		}
		
		String kickee=parameters[0];
		
		boolean exists = false;
		for(User u:MyBotMain.getBot().getUsers(channel)) {
			if(u.getNick().equalsIgnoreCase(kickee)) {
				exists=true;
			}
		}
		
		if(exists && Database.isMod(sender, channel.substring(1))) {
			MyBotMain.getBot().addVoteTimeOut(channel, new me.jewsofhazard.pcmrbot.util.VoteTimeOut(channel, kickee));
			return "The channel has begun a vote to timeout %toKick%. Type !voteTO to place your vote. To vote no just do not vote.".replace("%toKick%", kickee);
		}
		
		boolean startVote = true;
		if(getCommandText().equalsIgnoreCase(kickee.substring(1))) {
			startVote = false;
		}
		
		if(startVote && Database.isMod(sender, channel.substring(1))) {
			return "%kickee% is not in the channel!".replace("%kickee%", kickee);
		}
		return MyBotMain.getBot().getVoteTimeOut(channel).addVote(sender);
	}

}
