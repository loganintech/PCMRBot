/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.league;

import java.util.List;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.QueueType;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.summoner.Summoner;
/**
 *
 * @author Hazard
 */
public class LeagueUtils {
    private String region;
    private String summoner;
    
    private static String apiKey = "df57fbca-8417-4af6-92d0-3150cb01e1f7";    
    //private final String NAME_URL = "https://" + region + ".api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+summoner+"?api_key="+apiKey;
    
    public LeagueUtils(String region, String summoner) throws Exception{
    
    this.region = region;
    this.summoner = summoner;

    }
    
    public static String getSummonerRank(String region, String summonerName){

        if(region.toLowerCase().equals("na")){
        RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        if(region.toLowerCase().equals("oce")){
        RiotAPI.setMirror(Region.OCE);
        RiotAPI.setRegion(Region.OCE);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("pbe")){
        RiotAPI.setMirror(Region.PBE);
        RiotAPI.setRegion(Region.PBE);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("ru")){
        RiotAPI.setMirror(Region.RU);
        RiotAPI.setRegion(Region.RU);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("tr")){
        RiotAPI.setMirror(Region.TR);
        RiotAPI.setRegion(Region.TR);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("las")){
        RiotAPI.setMirror(Region.LAS);
        RiotAPI.setRegion(Region.LAS);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("lan")){
        RiotAPI.setMirror(Region.LAN);
        RiotAPI.setRegion(Region.LAN);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("kr")){
        RiotAPI.setMirror(Region.KR);
        RiotAPI.setRegion(Region.KR);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("euw")){
        RiotAPI.setMirror(Region.EUW);
        RiotAPI.setRegion(Region.EUW);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("eune")){
        RiotAPI.setMirror(Region.EUNE);
        RiotAPI.setRegion(Region.EUNE);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
        else if(region.toLowerCase().equals("br")){
        RiotAPI.setMirror(Region.BR);
        RiotAPI.setRegion(Region.BR);
        RiotAPI.setAPIKey(apiKey);

        Summoner summoner = RiotAPI.getSummonerByName(summonerName);
        return summoner.getName() + " is in " + summoner.getLeagues().get(0).getTier().toString().toLowerCase();
        }
    return "You have used an incorrect region or summoner name, please try again.";
    }
   
    
    
}
