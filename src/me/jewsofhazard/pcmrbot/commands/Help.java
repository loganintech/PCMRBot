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
		replies.put("votestart", "The format of the votestart command is as follows: !votestart {time in seconds}|{question to ask}|{option 1}|{infinte more options}\nNote, you do not need { or } and you must not add spaces between |. For example, !votestart 30|What game should I play?|Bioshock|Minecraft|League. Is perfect.");
		replies.put("addautoreply", "Autoreply is formated similarly to starting votes. All you need is to type !addautoreply {keyword}|{keyword}|{reply}. Note: again, there can be no spaces between pipes ( | ). The difference is that you may add as many keywords as you like as long as the reply is last.");
		replies.put("raffle", "A raffle's context is simply !raffle {type} where type could be (all, follower or follwers, subscriber or subscribers.");
		replies.put("shorten", "This is simply !shorten {link} to make it a bit.ly link.");
		replies.put("seen", "The syntax for this is !seen {user} and will tell you how long it has been since {user} has chatted.");
		replies.put("slap", "This slaps the targeted user. !slap {user}.");
	}

	@Override
	public String execute(String channel, String sender, String...parameters) {
		if (parameters.length == 0) {
			return "I am sorry %user% we have not added command-specific help for that command yet. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", sender);
		}
		String command = parameters[0];
		
		String reply = replies.get(command);
		
		if(reply==null) {
			return "I am sorry %user% we have not added command-specific help for that command yet. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", sender);
		}
		return reply;
	}

}
