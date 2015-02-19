package me.jewsofhazzard.pcmrbot;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Driver {

	public static void main(String[] args) throws IOException {
//		Gson gson=new Gson();
//		JsonFactory factory = new JsonFactory();
////		JsonParser parser = factory.createParser(new URL("https://api.twitch.tv/kraken/channels/botduck/?oauth_token=warx77ee00evfiwzpcufm7xodakpbb"));
//		JsonParser parser = factory.createParser(new URL("https://api.twitch.tv/kraken/channels/donald10101/?oauth_token=0waiocof76g654kpilbt83l3lhwxvn"));
//		
//		while(!parser.isClosed()) {
//			System.out.println(parser.getText());
//			JsonToken token=parser.nextToken();
//			if(token == null) {
//				break;
//			}
//			if(JsonToken.START_OBJECT.equals(token)) {
//				token=parser.nextToken();
//				if(JsonToken.FIELD_NAME.equals(token)) {
//					token = parser.nextToken();
//					if(JsonToken.START_OBJECT.equals(token)) {
//						token=parser.nextToken();
//						if(JsonToken.FIELD_NAME.equals(token)) {
//							token=parser.nextToken();
//							if(JsonToken.VALUE_STRING.equals(token)) {
//								System.out.println(parser.getText());
//							}
//						}
//					}
//				}
//			}
//		}
//		String request        = "https://api.twitch.tv/kraken/channels/donald10101/?channel[status]=TESTING_STUFF&_method=put&oauth_token=0waiocof76g654kpilbt83l3lhwxvn";
//		URL    url            = new URL( request );
//		HttpsURLConnection cox= (HttpsURLConnection) url.openConnection();
		
		String url = "https://api.twitch.tv/kraken/channels/donald10101/";
		String charset=StandardCharsets.UTF_8.name();
		String status="ProgrammingL";
		String _method="put";
		String oauth_token="0waiocof76g654kpilbt83l3lhwxvn";
		
		String query = String.format("channel[game]=%s&_method=%s&oauth_token=%s", URLEncoder.encode(status, charset), URLEncoder.encode(_method, charset), URLEncoder.encode(oauth_token, charset));
		
		URLConnection connection = new URL(url + "?" + query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		connection.getInputStream();
		
		
		
		
//		BufferedReader reader= null;
//		try {
//			final URL url=new URL("https://api.twitch.tv/kraken");
//			reader = new BufferedReader(new InputStreamReader(url.openStream()));
//			final StringBuffer buffer=new StringBuffer();
//			int read;
//			final char[] chars = new char[1024];
//			while((read = reader.read(chars)) != -1) {
//				buffer.append(chars, 0, read);
//			}
//			JsonObject blah=gson.fromJson(buffer.toString(), JsonObject.class);
//			System.out.println(blah);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if(reader != null) {
//				reader.close();
//			}
//		}
		
	}

}
