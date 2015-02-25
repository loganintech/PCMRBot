

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Slow extends Command {

	private CommandLevel level = CommandLevel.Mod;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "slow";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (!Main.getBot().getSlowMode(channel)) {
			Main.getBot().setSlowMode(channel, true);
			try {
				int time = Integer.valueOf(parameters[0]);
				return "/slow " + time;
			} catch (NumberFormatException e) {
				return "/slow";
			}
		} else {
			Main.getBot().setSlowMode(channel, false);
			return "/slowoff";
		}
	}

}
