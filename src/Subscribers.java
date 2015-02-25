

import java.util.HashMap;

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Subscribers extends Command {

	private HashMap<String, Boolean> subscriberModes;
	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "subscribers";
	}

	public Subscribers() {
		subscriberModes = new HashMap<>();
		for (String s : Main.getBot().getChannels()) {
			subscriberModes.put(s, false);
		}
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (!Main.getBot().getSubscribersMode(channel)) {
			Main.getBot().setSubscribersMode(channel, true);
			return "/subscribers";
		}
		Main.getBot().setSubscribersMode(channel, false);
		return "/subscribersoff";
	}
}
