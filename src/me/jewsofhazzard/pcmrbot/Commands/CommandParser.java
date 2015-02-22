package me.jewsofhazzard.pcmrbot.Commands;

import java.util.HashMap;

import me.jewsofhazzard.pcmrbot.database.Database;

public class CommandParser {
	private static HashMap<String, Command> commands;
	
	public static void init() {
		commands=new HashMap<>();
		commands.put("addautoreply", new AddAutoReply());
		commands.put("addmoderator", new AddModerator());
		commands.put("broadcast", new Broadcast());
		commands.put("changeoption", new ChangeOption());
		commands.put("changewelcome", new ChangeWelcome());
		commands.put("clear", new Clear());
		commands.put("clearautoreplies", new ClearAutoReplies());
		commands.put("commercial", new Commercial());
		commands.put("disablereplies", new DisableReplies());
		commands.put("disablewelcome", new DisableWelcome());
		commands.put("enablereplies", new EnableReplies());
		commands.put("enablewelcome", new EnableWelcome());
		commands.put("enter", new Enter());
		commands.put("fatality", new Fatality());
		commands.put("fight", new Fight());
		commands.put("gabe", new Gabe());
		commands.put("game", new Game());
		commands.put("help", new Help());
		commands.put("joinme", new JoinMe());
		commands.put("ko", new KO());
		commands.put("leaveme", new LeaveMe());
		commands.put("lmgtfy", new LMGTFY());
		commands.put("me", new Me());
		commands.put("pcmrbot", new PCMRBot());
		commands.put("poll", new Poll());
		commands.put("punch", new Punch());
		commands.put("raffle", new Raffle());
		commands.put("seen", new Seen());
		commands.put("servers", new Servers());
		commands.put("shorten", new Shorten());
		commands.put("shutdown", new Shutdown());
		commands.put("slap", new Slap());
		commands.put("slow", new Slow());
		commands.put("slowclap", new SlowClap());
		commands.put("steamsales", new SteamSales());
		commands.put("subscribers", new Subscribers());
		commands.put("teamspeak", new Teamspeak());
		commands.put("title", new Title());
		commands.put("vote", new Vote());
	}
	
	public static String parse(String command, String sender, String channel, String parameters) {
		Command c=commands.get(command);
		if(hasAccess(c, sender, channel)) {
			return c.execute(channel, sender, parameters);
		}
		return null;
	}
	
	private static boolean hasAccess(Command c, String sender, String channel) {
		switch(commands.get(c).getCommandLevel()) {
		case Mod:
			return Database.isMod(sender, channel);
		case Owner:
			return sender.equalsIgnoreCase(channel.substring(1));
		case Normal:
			return true;
		}
		return false;
	}
}
