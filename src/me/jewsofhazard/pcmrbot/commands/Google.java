package me.jewsofhazard.pcmrbot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import me.jewsofhazard.pcmrbot.util.CLevel;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Google extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "google";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		/*StringBuilder sb = new StringBuilder();
		sb.append("http://google.com/?q=");
		for(int i=1;i<parameters.length;i++) {
			sb.append(parameters[i]+"+");
		}
		sb.append(parameters[parameters.length-1]);
		return sb.toString();
                */
            
                String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
                String search  = parameters.toString();
                String Encoded;
            try {
                Encoded = google + URLEncoder.encode(search, "UTF-8"); 
                URL url = new URL(Encoded);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                String ret = null;
                
        	while ((str = in.readLine()) != null) {
                    ret += str;
        	}
                in.close();

                ret = ret.substring(ret.indexOf("unescapedUrl") + 15);
                ret = ret.substring(1 ,ret.indexOf("\""));
                google = "This is what I found for you: " + ret;
                
            } catch (UnsupportedEncodingException | MalformedURLException ex) {
                return "Sorry! There was an Error while trying to search.";
            } catch (IOException ex) {
                Logger.getLogger(Google.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return google;
	}

}
