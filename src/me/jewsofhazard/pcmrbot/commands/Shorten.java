/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Saso, James Wolff, Kyle Nabinger
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;
import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.ShortenResponse;

import com.google.gson.JsonParser;

public class Shorten extends Command {

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
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
