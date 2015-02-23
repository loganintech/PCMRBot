package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Setup extends Command implements ICommand {

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(!parameters[0].equalsIgnoreCase("continue")) {
			MyBotMain.getBot().sendMessage(
				channel,
				"To begin with, we use a two-part system to define a few options. Let's begin with timing out a user.");
			MyBotMain.getBot().sendMessage(
				channel,
				"Users are timed out for excessive caps, symbols, emotes, long messages, links, and blacklisted words (spam).");
			MyBotMain.getBot().sendMessage(
				channel,
				"We would like you to configure the ammount of capital letters, symbols, and emotes (all of which default to 10), and paragraph length(defaults to 250 characters) allowed in a message.");
			MyBotMain.getBot().sendMessage(
				channel,
				"To change this, please run !changeOption <caps, symbols, emotes, paragraph>|<new value>. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");
			MyBotMain.getBot().sendMessage(
				channel,
				"Once you are finished type \"!setup continue\" in chat!");
		} else {
			MyBotMain.getBot().sendMessage(
				channel,
				"Next we are going to configure the welcome message for when new users enter your channel. It has a default (which should have been said to you by now), but if you want to change it you can type !changewelcome <message>.");
			MyBotMain.getBot().sendMessage(
				channel,
				"If you want the users name to appear in the join message use %user%. This will cause the bot to replace that with the name of the person who is joining.");
			MyBotMain.getBot().sendMessage(
				channel,
				"If you would like to permanantely disable this feature type \"!changeWelcome none\" without the quotes!");
			MyBotMain.getBot().sendMessage(
				channel,
				"Also, if you are partnered and would wish to use subscriber raffles or change the stream title and game, please go to http://pcmrbot.no-ip.com/authorize to allow your chat.");
		}
		return null;
	}

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Owner;
	}

	@Override
	public String getCommandText() {
		return "setup";
	}

}
