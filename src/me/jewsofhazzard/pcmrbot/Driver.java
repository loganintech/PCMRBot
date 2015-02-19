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
