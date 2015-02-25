package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

import org.jibble.pircbot.User;

public class VoteTimeOut extends Command {

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

		if (Main.getBot().getVoteTimeOut(channel) != null) {
			return "There is already a vote to timeout a user happenning in this channel. If you were trying to vote just type \"!voteTO\"!";
		}

		String kickee = parameters[0];

		boolean exists = false;
		for (User u : Main.getBot().getUsers(channel)) {
			if (u.getNick().equalsIgnoreCase(kickee)) {
				exists = true;
			}
		}

		if (exists && Database.isMod(sender, channel.substring(1))) {
			Main.getBot().addVoteTimeOut(
					channel,
					new me.jewsofhazard.pcmrbot.util.VoteTimeOut(channel,
							kickee));
			return "The channel has begun a vote to timeout %toKick%. Type !voteTO to place your vote. To vote no just do not vote."
					.replace("%toKick%", kickee);
		}

		boolean startVote = true;
		if (getCommandText().equalsIgnoreCase(kickee.substring(1))) {
			startVote = false;
		}

		if (startVote && Database.isMod(sender, channel.substring(1))) {
			return "%kickee% is not in the channel!"
					.replace("%kickee%", kickee);
		}
		return Main.getBot().getVoteTimeOut(channel).addVote(sender);
	}

}
