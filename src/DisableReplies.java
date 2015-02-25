

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class DisableReplies extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "disablereplies";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		Main.getBot().setConfirmationEnabled(channel, false);
		return "%user% has disabled bot replies".replace("%user%", sender);
	}

}
