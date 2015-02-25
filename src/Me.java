

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Me extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "me";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "/me %message%".replace("%message%", parameters[0]);
	}

}
