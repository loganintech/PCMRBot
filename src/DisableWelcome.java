

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class DisableWelcome extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "disablewelcome";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		Main.getBot().setWelcomeEnabled(channel, false);
		return "Welcome messages have been disabled.";
	}

}
