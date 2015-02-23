/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Ssaso, James Wolff, Angablade
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

package me.jewsofhazard.pcmrbot;

import java.io.IOException;

import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.ShortenResponse;

import com.google.gson.JsonParser;

public class Driver {

	public static void main(String[] args) throws IOException {
//		String element = new JsonParser().parse(new JsonReader(new InputStreamReader(new URL("https://api.twitch.tv/kraken/channels/donald10101/follows?direction=DESC&limit=25&offset=50").openStream()))).getAsJsonObject().getAsJsonArray("follows").get(25).getAsJsonObject().getAsJsonObject("user").get("display_name").getAsString();
//		System.out.println(element);
//		
//		int i=new JsonParser().parse(new JsonReader(new InputStreamReader(new URL("https://api.twitch.tv/kraken/channels/donald10101").openStream()))).getAsJsonObject().getAsJsonPrimitive("followers").getAsInt();
//		System.out.println(i);
		
		String link = "http://www.google.com";
		BitlyClient client=new BitlyClient("596d69348e5db5711a9f698ed606f4500fe0e766");
		Response<ShortenResponse> repShort = client.shorten().setLongUrl(link).call();
		System.out.println(repShort.toString());
		
		if(repShort.status_txt.equalsIgnoreCase("ok")) {
			String out = new JsonParser().parse(repShort.data.toString()).getAsJsonObject().getAsJsonPrimitive("url").getAsString();
			System.out.println(out);
		}
		
		
	}

}
