package me.jewsofhazard.pcmrbot.commands;

import java.util.HashMap;
import java.util.Map;

import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Help extends Command  implements ICommand {

	private Map<String, String> replies;
	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "help";
	}
	
	public Help() {
		replies=new HashMap<>();
		replies.put("poll", "The format of the poll command is as follows: !poll {time in seconds}|{question to ask}|{option 1}|{infinte more options}\nNote, you do not need { or } and you must not add spaces between |. For example, !poll 30|What game should I play?|Bioshock|Minecraft|League. Is perfect.");
		replies.put("addautoreply", "Autoreply is formated similarly to starting votes. All you need is to type !addautoreply {keyword}|{keyword}|{reply}. Note: again, there can be no spaces between pipes ( | ). The difference is that you may add as many keywords as you like as long as the reply is last.");
		replies.put("raffle", "A raffle's context is simply !raffle {type} where type could be (all, follower or follwers, subscriber or subscribers.");
		replies.put("shorten", "This is simply !shorten {link} to make it a bit.ly link.");
		replies.put("seen", "The syntax for this is !seen {user} and will tell you how long it has been since {user} has chatted.");
		replies.put("slap", "This slaps the targeted user. !slap {user}.");
		replies.put("welcome", "You can use !enablewelcome and !disablewelcome to enable and disable welcome messages BUT it will not save that, it would only be that way while the bot is running. You can use !changewelcome to change it. If you change it to none you will recieve no welcome messages AND it will save that you recieve no messages.");
	}

	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (parameters.length == 0) {
			return "I am sorry %user% we have not added command-specific help for that command yet. Valid options are pol, addautoreply, raffle, shorten, seen, slap, and welcome. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", sender);
		}
		String command = parameters[0];
		
		String reply = replies.get(command);
		
		if(reply==null) {
			return "I am sorry %user% we have not added command-specific help for that command yet. Valid options are pol, addautoreply, raffle, shorten, seen, slap, and welcome. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", sender);
		}
		return reply;
	}

}
