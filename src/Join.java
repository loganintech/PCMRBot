

import me.jewsofhazard.pcmrbot.Main;
import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class Join extends Command {

	private CommandLevel level = CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}

	@Override
	public String getCommandText() {
		return "join";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (channel.equalsIgnoreCase(Main.getBotChannel())) {
			Main.joinChannel("#" + sender);
			return "I have joined %user%'s channel.".replace("%user%", sender);
		}
		return "Sorry %user%, but I can't join your channel from here! Visit http://twitch.tv/pcmrbot and try again!"
				.replace("%user%", sender);

	}
}