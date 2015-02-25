

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Leave extends Command {

	private CommandLevel level = CommandLevel.Owner;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "leave";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (!channel.equalsIgnoreCase(Main.getBotChannel())) {
			Main.partChannel(channel);
			return "I have disconnected from %channel%'s channel.".replace(
					"%channel%", channel);
		} else {
			return "Sorry %user%, I cannot allow you to disconnect me from my home channel."
					.replace("%user%", sender);
		}

	}

}
