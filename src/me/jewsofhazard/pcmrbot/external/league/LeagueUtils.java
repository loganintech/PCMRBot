/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.external.league;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;
import java.util.logging.Logger;


/**
 *
 * @author Hazard
 */
public class LeagueUtils {
	// private String region;
	private static Summoner summoner;
	private static final String apiKey = "df57fbca-8417-4af6-92d0-3150cb01e1f7";
        private static String regionTest ;
        private static String regionSet ;
        private static String currentRegion ;
        private static boolean isSetup = false;

        public static String setupClass(String region){
            RiotAPI.setAPIKey(apiKey);
            regionTest = setRegion(region);
            regionSet = region;
            isSetup = true;
            return regionTest;
        }
        
	public static String getSummonerRank(String region, String summonerName) {
		
            try {
                regionTest = setRegion(region);
                summoner = RiotAPI.getSummonerByName(summonerName);
                String mode = getMode(summoner);
                if(regionTest.equals("-1")){
                    
                    String tier = getTier(summoner);
                    String division = getDivision(summoner);  
                    
                    if(!mode.equals("404")){
                        
                        String map = getMap(summoner);  
                        String returnMe = summoner.getName() + " is in " + tier + " " + division + " and is playing " + mode + " on " + map + " in " + currentRegion;
                        setRegion(regionSet);
                        return returnMe;
                    }
                    else{
                    
                        String returnMe = summoner.getName() + " is in " + tier + " " + division + " in " + currentRegion;
                        setRegion(regionSet);
                        return returnMe;
                    }
                }
                else{
                    setRegion(regionSet);
                    return regionTest;
                    
                }
                
            } catch(com.robrua.orianna.type.exception.APIException e){
                if(e.getStatus().equals(APIException.Status.NOT_FOUND)){
                    return "User was not found in " + currentRegion;
                } else if(e.getStatus().equals(APIException.Status.INTERNAL_SERVER_ERROR)){
                    return "There was an issue in the league servers, please try again later.";
                } else if(e.getStatus().equals(APIException.Status.RATE_LIMIT_EXCEEDED)){
                    return "The rate limit was exceeded, please try again later.";
                } else if(e.getStatus().equals(APIException.Status.UNAUTHORIZED)){
                    return "An authorization error has occured with the riot servers, please notify /u/JewsOfHazard on reddit.";
                } else {
                    return getLevel();
                }
            } catch(Exception e){
                return "An error has occured, please notify /u/JewsOfHazard on reddit.";
            }
            
            
            
            
            
            
            /*
                setRegion(region);
		try {                    
			if (regionTest.equals("-1")) {
                            
                            summoner = RiotAPI.getSummonerByName(summonerName);
                            String tier = getTier(summoner);
                            String division = getDivision(summoner);
                            String mode = getMode(summoner);
                            String map = getMap(summoner);
                                                                
                            String returnMe = summoner.getName()
                                    + " is in " + tier + " " + division;
                            if (!mode.equals("404")){
                                returnMe = returnMe.concat(" and is playing " + mode + " on " + map);
                            }
                            setRegion(regionSet);
                            return returnMe; 
                        } 

		} catch (Exception e) {
                    if (!regionTest.equals("-1")) {
			return regionTest;
                    }
                    else{
                        
                    String returnMe = getLevel(summonerName); 
                    setRegion(regionSet);
			return returnMe;
                    
                    }
		}
		return "If the class got here, jewsofhazard is being dumb and needs to revisit this command. Please message him on reddit /u/jewsofhazard.";
        
            */        

            }
        
        public static String getSummonerRank(String summonerName){
        try {
               
                summoner = RiotAPI.getSummonerByName(summonerName);
                String mode = getMode(summoner);
                if(regionTest.equals("-1")){
                    
                    String tier = getTier(summoner);
                    String division = getDivision(summoner);  
                    
                    if(!mode.equals("404")){
                        
                        String map = getMap(summoner);  
                        String returnMe =  summoner.getName() + " is in " + tier + " " + division + " and is playing " + mode + " on " + map + " in " + currentRegion;
                        setRegion(regionSet);
                        return returnMe;
                    }
                    else{
                    
                        String returnMe = summoner.getName() + " is in " + tier + " " + division + " in " + currentRegion;
                        setRegion(regionSet);
                        return returnMe;
                        
                    }
                }
                else{
                
                    return "You have used the command without a region and the region has not been set. Please make sure you set the region before you use the command.";
                    
                }
                
            } catch(com.robrua.orianna.type.exception.APIException e) {
                if(e.getStatus().equals(APIException.Status.NOT_FOUND)){
                    return "User was not found in " + currentRegion;
                } else if(e.getStatus().equals(APIException.Status.INTERNAL_SERVER_ERROR)){
                    return "There was an issue in the league servers, please try again later.";
                } else if(e.getStatus().equals(APIException.Status.RATE_LIMIT_EXCEEDED)){
                    return "The rate limit was exceeded, please try again later.";
                } else if(e.getStatus().equals(APIException.Status.UNAUTHORIZED)){
                    return "An authorization error has occured with the riot servers, please notify the developer.";
                } else {
                    return getLevel();
                }
            } catch(Exception e){
                return "An error has occured, please notify /u/JewsOfHazard on reddit.";
            }            
        }
        
        
        
	public static String setRegion(String region) {
		try {
			switch (region.toLowerCase()) {
			case "oce":
				RiotAPI.setMirror(Region.OCE);
				RiotAPI.setRegion(Region.OCE);
                                currentRegion = "OCE";
				return "-1";
			case "na":
				RiotAPI.setMirror(Region.NA);
				RiotAPI.setRegion(Region.NA);
                                currentRegion = "NA";
				return "-1";
			case "pbe":
				RiotAPI.setMirror(Region.PBE);
				RiotAPI.setRegion(Region.PBE);
				return "-1";
			case "ru":
				RiotAPI.setMirror(Region.RU);
				RiotAPI.setRegion(Region.RU);
                                currentRegion = "RU";
				return "-1";
			case "tr":
				RiotAPI.setMirror(Region.TR);
				RiotAPI.setRegion(Region.TR);
                                currentRegion = "TR";
				return "-1";
			case "las":
				RiotAPI.setMirror(Region.LAS);
				RiotAPI.setRegion(Region.LAS);
                                currentRegion = "LAS";
				return "-1";
			case "lan":
				RiotAPI.setMirror(Region.LAN);
				RiotAPI.setRegion(Region.LAN);
                                currentRegion = "LAN";
				return "-1";
			case "kr":
				RiotAPI.setMirror(Region.KR);
				RiotAPI.setRegion(Region.KR);
                                currentRegion = "KR";
				return "-1";
			case "euw":
				RiotAPI.setMirror(Region.EUW);
				RiotAPI.setRegion(Region.EUW);
                                currentRegion = "EUW";
				return "-1";
			case "eune":
				RiotAPI.setMirror(Region.EUNE);
				RiotAPI.setRegion(Region.EUNE);
                                currentRegion = "EUNE";
				return "-1";
			case "br":
				RiotAPI.setMirror(Region.BR);
				RiotAPI.setRegion(Region.BR);
                                currentRegion = "BR";
				return "-1";
			}
			return "You have used an incorrect region, please try again.";
		} catch (Exception e) {
			return "You have used an incorrect region, please try again.";
		}

	}

	public static String getLevel() {
		try {
			String returnMe = summoner.getName() + " is level " + summoner.getLevel() + " in " + currentRegion;
                        setRegion(regionSet);
                        return returnMe;
		} catch (Exception e) {
			String returnMe = "An error has occured when checking the level of the user. This is most likely due to an incorrect name.";
                        setRegion(regionSet);
                        return returnMe;
                }
	}

        public static String getMap(Summoner summoner){
        
            String map = summoner.getCurrentGame().getMap().toString().replaceAll("_"," ");
            map = map.substring(0,1)
                .concat(map.substring(1,map.indexOf(" ")).toLowerCase())
                .concat(map.substring(map.indexOf(" "), map.indexOf(" ") + 2))
                .concat(map.substring(map.indexOf(" ") + 2).toLowerCase());
        return map;
        
        }
        
        public static String getMode(Summoner summoner){
            try{    
                return summoner.getCurrentGame().getMode().toString().toLowerCase();
            }
            catch(Exception e){
                return "404";
            }
        }
        
        public static String getTier(Summoner summoner){     //silver, gold, etc
            String tier = summoner.getLeagues().get(0).getTier().toString(); 
            return tier.substring(0,1).concat(tier.substring(1).toLowerCase());
        }
        
        public static String getDivision(Summoner summoner){
        
            return summoner.getLeagueEntries().get(0).getEntries().get(0).getDivision(); //2, 3, etc
            
        }
        
        public static boolean isSetup(){
        
            return isSetup;
            
        }
        
        public static String changeRegionSet(String region){
        region = region.toLowerCase();
        String checkRegion = setRegion(region);
        if(checkRegion.equals("-1")){
            
            currentRegion = region;
            return checkRegion;
            
            }
        
            return checkRegion;
        
        }
        
        public static String getCurrentRegion(){
            return currentRegion;
        }
}
