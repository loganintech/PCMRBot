

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.db.Database;
import me.jewsofhazard.pcmrbot.util.CommandLevel;

public class DelAutoReply extends Command {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "delautoreply";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		String keywords = parameters[0].replace("|", ",");
		if (Database.delAutoReply(channel.substring(1), keywords)) {
			return "Successfully removed the auto reply associated with the keywords: %kw%"
					.replace("%kw%", keywords);
		}
		return "There was an issue removing the auto reply associated with the keywords: %kw%! Did you pass all of the keywords?"
				.replace("%kw%", keywords);
	}

}
