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

package me.jewsofhazzard.pcmrbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Driver {

	public static void main(String[] args) throws IOException {
		String element = new JsonParser().parse(new JsonReader(new InputStreamReader(new URL("https://api.twitch.tv/kraken/channels/donald10101/follows?direction=DESC&limit=25&offset=50").openStream()))).getAsJsonObject().getAsJsonArray("follows").get(25).getAsJsonObject().getAsJsonObject("user").get("display_name").getAsString();
		System.out.println(element);
		
		int i=new JsonParser().parse(new JsonReader(new InputStreamReader(new URL("https://api.twitch.tv/kraken/channels/donald10101").openStream()))).getAsJsonObject().getAsJsonPrimitive("followers").getAsInt();
		System.out.println(i);
		
//		String url = "https://api.twitch.tv/kraken/channels/donald10101/";
//		String charset=StandardCharsets.UTF_8.name();
//		String status="ProgrammingL";
//		String _method="put";
//		String oauth_token="0waiocof76g654kpilbt83l3lhwxvn";
//		
//		String query = String.format("channel[game]=%s&_method=%s&oauth_token=%s", URLEncoder.encode(status, charset), URLEncoder.encode(_method, charset), URLEncoder.encode(oauth_token, charset));
//		
//		URLConnection connection = new URL(url + "?" + query).openConnection();
//		connection.setRequestProperty("Accept-Charset", charset);
//		connection.getInputStream();
		
	}

}
