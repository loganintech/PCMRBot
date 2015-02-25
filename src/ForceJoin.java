

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class ForceJoin extends Command {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "forcejoin";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (channel.equalsIgnoreCase(Main.getBotChannel())) {
			Main.joinChannel(parameters[0]);
			return "Forcefully joining %channel%".replace("%channel%",
					parameters[0]);
		}
		return "You can only preform this command from the main bot channel!";
	}

}
