package me.jewsofhazzard.pcmrbot.twitch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class TwitchUtilities {

	private final static String BASE_URL = "https://api.twitch.tv/kraken/";
	private final static String CHARSET = StandardCharsets.UTF_8.name(); 
	
	private final static Logger logger = Logger.getLogger(TwitchUtilities.class+"");
	
	/**
	 * Changes the title on streamers page
	 * 
	 * @param channel - channel to change the title on
	 * @param title - title to be changed to
	 */
	public static void updateTitle(String channel, String title) {
		String url = BASE_URL+"channels/"+channel.substring(1)+"/";
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
			logger.log(Level.SEVERE, "An error occurred updating the title for "+channel.substring(1), e);
		}
	}
	
	/**
	 * Changes the game on the streamers page
	 * 
	 * @param channel - channel to change the game on
	 * @param game - game to be changed to
	 */
	public static void updateGame(String channel, String game) {
		String url = BASE_URL+"channels/"+channel.substring(1)+"/";
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
			logger.log(Level.SEVERE, "An error occurred updating the game for "+channel.substring(1), e);
		}
	}

	/**
	 * Checks if the sender is a follower of channel
	 * 
	 * @param sender
	 * @param channel
	 * @return - true if sender is following channel 
	 */
	public static boolean isFollower(String sender, String channel) {
		try {
			int count=followerCount(channel);
			int pages=count/25;
			if(count%25!=0) {
				pages++;
			}
			String nextUrl = "https://api.twitch.tv/kraken/channels/"+channel.substring(1)+"/follows";
			JsonArray followers;
			for(int i=0;i<pages;i++) {
				followers = new JsonParser().parse(new JsonReader(new InputStreamReader(new URL(nextUrl).openStream()))).getAsJsonObject().getAsJsonArray("follows");
				for(int j=0;j<25;j++) {
					if(sender.equalsIgnoreCase(followers.get(i).getAsJsonObject().getAsJsonObject("user").get("display_name").getAsString())) {
						return true;
					}
				}
				nextUrl = URLEncoder.encode(new JsonParser().parse(new JsonReader(new InputStreamReader(new URL(nextUrl).openStream()))).getAsJsonObject().getAsJsonArray("_links").get(1).getAsString(), CHARSET);
			}
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			logger.log(Level.SEVERE, "An error occurred checking if "+sender+" is following "+channel.substring(1), e);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Gets the amount of people following the specified channel
	 * 
	 * @param channel
	 * @return number of followers for channel, 0 if an error occurs
	 */
	public static int followerCount(String channel) {
		try {
			return new JsonParser().parse(new JsonReader(new InputStreamReader(new URL(BASE_URL+"channels"+channel.substring(1)).openStream()))).getAsJsonObject().getAsJsonPrimitive("followers").getAsInt();
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			logger.log(Level.SEVERE, "An error occurred getting the follower count for "+channel.substring(1), e);
		}
		return 0;
	}
	
}
