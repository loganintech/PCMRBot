

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.UserLevel;

public class Raffle extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "raffle";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (Main.getBot().getRaffle(channel) != null) {
			return "There is already a raffle in progress!";
		}
		UserLevel level = UserLevel.getTypeFromString(parameters[0]);
		if (level == null) {
			return "Improper raffle type! Valid choices are \"everyone\", \"followers\" \"subscribers\"";
		}
		Main.getBot().addRaffle(channel,
				new me.jewsofhazard.pcmrbot.util.Raffle(channel, level));
		return "Raffle started for %level%"
				.replace("%level%", level.toString());
	}

}
