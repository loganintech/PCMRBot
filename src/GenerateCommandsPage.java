

import me.jewsofhazard.pcmrbot.cmd.Command;
import me.jewsofhazard.pcmrbot.util.CommandLevel;
import me.jewsofhazard.pcmrbot.util.GenerateCommandsHTML;

public class GenerateCommandsPage extends Command {

	@Override
	public CommandLevel getCommandLevel() {
		return CommandLevel.Owner;
	}

	@Override
	public String getCommandText() {
		return "generatecommandspage";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if (GenerateCommandsHTML.createCommandsHTML(channel.substring(1))) {
			return "Created commands page for your custom commands! http://pcmrbot.no-ip.info/commands/%channel%.html"
					.replace("%channel%", channel.substring(1));
		}
		return "It appears you don't have any custom commands! Use !addcom <commandName>|<reply> to create one";
	}

}
