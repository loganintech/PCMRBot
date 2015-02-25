

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class KO extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "ko";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return String.format("%s knocks out %s.", sender, parameters[0]);
	}

}
