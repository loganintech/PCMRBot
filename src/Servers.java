

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Servers extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "servers";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "http://www.reddit.com/r/pcmasterrace/wiki/servers";
	}

}
