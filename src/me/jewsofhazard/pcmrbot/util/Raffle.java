package me.jewsofhazard.pcmrbot.util;

import java.util.ArrayList;
import java.util.Random;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.twitch.TwitchUtilities;

public class Raffle {

	// Let it be known, Mr_chris is the
	// first user to ever win the raffle.
	private ArrayList<String> participants;
	private String channel;
	private UserLevel type;
	
	public Raffle(String c, UserLevel t) {
		channel = c;
		type = t;
		participants = new ArrayList<>();
	}
	
	public Raffle start() {
		new Timer(channel, 300, this);
		return this;
	}
	
	public void enter(String sender) {

		if (participants.contains(sender)) {
			Main.getBot().sendMessage(channel,"%sender% is a dirty cheater and tried to join the raffle more than once, may he be smiten.".replace("%sender%", sender));
			return;
		}

		if ((type.equals(UserLevel.Follower)) && (TwitchUtilities.isFollower(channel, sender) || sender.equalsIgnoreCase(channel.substring(1)))) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else if (type.equals(UserLevel.Subscriber) && (TwitchUtilities.isSubscriber(channel, sender) || sender.equalsIgnoreCase(channel.substring(1)))) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else if (type.equals(UserLevel.Normal)) {
			participants.add(sender);
			if (Main.getBot().getConfirmationReplies(channel)) {
				Main.getBot().sendMessage(channel, "%sender% has joined the raffle.".replace("%sender%", sender));
			}
		} else {
			Main.getBot().sendMessage(channel, "I am sorry %sender% you are not allowed to join this raffle.".replace("%sender%", sender));
		}
	}

	public void selectWinner() {
		String winner = participants.get(new Random().nextInt(participants.size()));
		Main.getBot().sendMessage(channel, "There raffle has closed. There are %amt% users in the raffle.".replace("%amt%", participants.size()+""));
		Main.getBot().sendMessage(channel,	"The raffle winner is %winner%.".replace("%winner%", winner));
		Main.getBot().removeRaffle(channel);
	}
}
