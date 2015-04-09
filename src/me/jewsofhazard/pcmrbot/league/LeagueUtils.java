/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.league;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.Reader;

/**
 *
 * @author Hazard
 * @riot api key: df57fbca-8417-4af6-92d0-3150cb01e1f7
 */
public class LeagueUtils {

    
    //private String apiKey = "df57fbca-8417-4af6-92d0-3150cb01e1f7";
    
    //private final static Logger logger = Logger.getLogger(LeagueUtils.class
	//		+ "");
    //private final static String CHARSET = StandardCharsets.UTF_8.name();
    
    //private String location = "na";
    //private String summoner = "jewsofhazard";
    ///* add methods to find a bunch of different things here, primarily summoner rank, or level
    //    then we can do, oh idk, something else interesting.
    //*/
    /*
    public String getSummonerId(String location, String summoner){
        this.location = location;
        this.summoner = summoner;
        
        URLConnection connection = null;
        String query = "https://"+ location +".api.pvp.net/api/lol/"+location+"/v1.4/summoner/by-name/"+ summoner +"?" + apiKey;
        return getSummonerName(getSomething(query, "id"));
        
    }
    
    public String getSummonerName(String id){
        String query = "https://"+location+".api.pvp.net/api/lol/na/v1.4/summoner/34854742?api_key=df57fbca-8417-4af6-92d0-3150cb01e1f7"
        getSomething(query, )
    
    }
    
    public String getSomething(String url, String something){
        try{
            Reader reader = new InputStreamReader(new URL("url").openStream()); //Read the json output
            Gson gson = new GsonBuilder().create();
            DataObject obj = gson.fromJson(reader, DataObject.class);
            return obj.toString();
        }catch(Exception e){
            System.out.println(e);
            return "-1";
        }
    }
    


private class DataObject{ //This class should match your json object structure
        private String something;
        
        public String toString(){
        return something;
        }
    }
*/
}
