package me.jewsofhazzard.pcmrbot.Commands;

import java.util.HashMap;
import java.util.Map;

public class Help implements Command {

	private String[] parameters;

	private String reply;

	private Map<String, String> replies;

	public Help(String... params) {
		parameters=params;
		replies=new HashMap<>();
		replies.put("votestart", "The format of the votestart command is as follows: !votestart {time in seconds}|{question to ask}|{option 1}|{infinte more options}\nNote, you do not need { or } and you must not add spaces between |. For example, !votestart 30|What game should I play?|Bioshock|Minecraft|League. Is perfect.");
		replies.put("addautoreply", "Autoreply is formated similarly to starting votes. All you need is to type !addautoreply {keyword}|{keyword}|{reply}. Note: again, there can be no spaces between pipes ( | ). The difference is that you may add as many keywords as you like as long as the reply is last.");
		replies.put("raffle", "A raffle's context is simply !raffle {type} where type could be (all, follower or follwers, subscriber or subscribers.");
		replies.put("shorten", "This is simply !shorten {link} to make it a bit.ly link.");
		replies.put("seen", "The syntax for this is !seen {user} and will tell you how long it has been since {user} has chatted.");
		replies.put("slap", "This slaps the targeted user. !slap {user}.");
		execute();
	}

	@Override
	public String getReply() {
		return reply;
	}

	@Override
	public void execute() {
		if (parameters.length == 1) {
			reply = "I am sorry %user% we have not added command-specific help for that command yet. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", parameters[0]);
			return;
		}
		String command = parameters[0];
		
		reply = replies.get(command);
		
		if(reply==null) {
			reply = "I am sorry %user% we have not added command-specific help for that command yet. Please proceed to http://pcmrbot.no-ip.info/commands for more information.".replace("%user%", parameters[1]);
		}
	}

}
