package me.jewsofhazzard.pcmrbot.twitch;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwitchUtilities {

	private final static String BASE_URL = "https://api.twitch.tv/kraken/";
	private final static String CHARSET = StandardCharsets.UTF_8.name(); 
	
	public static void updateTitle(String channel, String title) {
		String url = BASE_URL+"channels/"+channel+"/";
		String _method="put";
		String oauth_token="0waiocof76g654kpilbt83l3lhwxvn";
		String query = null;
		URLConnection connection = null;
		try {
			query = String.format("channel[status]=%s&_method=%s&oauth_token=%s", URLEncoder.encode(title, CHARSET), URLEncoder.encode(_method, CHARSET), URLEncoder.encode(oauth_token, CHARSET));
			connection = new URL(url + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateGame(String channel, String game) {
		String url = BASE_URL+"channels/"+channel+"/";
		String _method="put";
		String oauth_token="0waiocof76g654kpilbt83l3lhwxvn";
		String query = null;
		URLConnection connection = null;
		try {
			query = String.format("channel[game]=%s&_method=%s&oauth_token=%s", URLEncoder.encode(game, CHARSET), URLEncoder.encode(_method, CHARSET), URLEncoder.encode(oauth_token, CHARSET));
			connection = new URL(url + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
