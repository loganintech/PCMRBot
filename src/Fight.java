

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Fight extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "fight";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("%s puts up his digs in preparation to punch %s.",
				sender, parameters[0]);
	}

}
