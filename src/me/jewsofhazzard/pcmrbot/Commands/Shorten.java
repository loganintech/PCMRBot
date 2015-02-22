package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;
import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.ShortenResponse;

import com.google.gson.JsonParser;

public class Shorten implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "shorten";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		String url = parameters[0];
		BitlyClient client = new BitlyClient(
				"596d69348e5db5711a9f698ed606f4500fe0e766");
		Response<ShortenResponse> repShort = client.shorten().setLongUrl(url)
				.call();

		if (repShort.status_txt.equalsIgnoreCase("ok")) {
			return new JsonParser().parse(repShort.data.toString())
					.getAsJsonObject().getAsJsonPrimitive("url").getAsString();
		}
		return "%url% is an invalid url! Make sure you include http(s)://.".replace("%url%", url);
	}

}
