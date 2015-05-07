/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jewsofhazard.pcmrbot.external.league;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;


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
        private static boolean isSetup = false;

        public static String setupClass(String region){
            RiotAPI.setAPIKey(apiKey);
            regionTest = setRegion(region);
            regionSet = region;
            isSetup = true;
            return regionTest;
        }
        
	public static String getSummonerRank(String region, String summonerName) {
		            setRegion(region);
		try {                    
			if (isSetup && regionTest.equals("-1")) {
                            
                            summoner = RiotAPI.getSummonerByName(summonerName);
                            String tier = getTier();
                            String division = getDivision();
                            try{
                                
                                String mode = getMode();
                                String map = getMap();
                                
                                
                                String returnMe = summoner.getName()
                                        + " is in " + tier + " " + division + " and is playing " + mode + " on " + map + ".";
                                                            setRegion(regionSet);
				return returnMe;
                                
                            } catch(Exception e) {
                                
                                String returnMe = summoner.getName()
                                        + " is in " + tier + " " + division;  
                                                            setRegion(regionSet);
                                return returnMe;
                                
                                
                                }
                            }
                        else{
                            
                            setupClass(region);
                            return getSummonerRank(region, summonerName);
                            
                        }
		} catch (Exception e) {
                    if (!regionTest.equals("-1")) {
			return regionTest;
                    }
                    else{
                    setRegion(region);
                    String returnMe = getLevel(summonerName); 
                    setRegion(regionSet);
			return returnMe;
                    
                    }
		}
		
		
	}

        public static String getSummonerRank(String summonerName){
                        
        try {
			if (isSetup && regionTest.equals("-1")) {
                            summoner = RiotAPI.getSummonerByName(summonerName);
                            String tier = getTier();
                            String division = getDivision();
                            try{
                                
                                String mode = getMode();
                                String map = getMap();
                                
				return summoner.getName()
                                        + " is in " + tier + " " + division + " and is playing " + mode + " on " + map + ".";
                                
                            } catch(Exception e) {

                                return summoner.getName()
                                        + " is in " + tier + " " + division;                
                                
                                }
                            }
                        else{
                        
                            return "To use lolrank without a region, you need to run !setlolregion [region] first.";
                            
                        }
		} catch (Exception e) {
                    
                    if (!regionTest.equals("-1")) {
			return regionTest;
                    }
                    else{
			return getLevel(summonerName);
                    }
		}
		
        }
        
	public static String setRegion(String region) {
		try {
			switch (region.toLowerCase()) {
			case "oce":
				RiotAPI.setMirror(Region.OCE);
				RiotAPI.setRegion(Region.OCE);
				return "-1";
			case "na":
				RiotAPI.setMirror(Region.NA);
				RiotAPI.setRegion(Region.NA);
				return "-1";
			case "pbe":
				RiotAPI.setMirror(Region.PBE);
				RiotAPI.setRegion(Region.PBE);
				return "-1";
			case "ru":
				RiotAPI.setMirror(Region.RU);
				RiotAPI.setRegion(Region.RU);
				return "-1";
			case "tr":
				RiotAPI.setMirror(Region.TR);
				RiotAPI.setRegion(Region.TR);
				return "-1";
			case "las":
				RiotAPI.setMirror(Region.LAS);
				RiotAPI.setRegion(Region.LAS);
				return "-1";
			case "lan":
				RiotAPI.setMirror(Region.LAN);
				RiotAPI.setRegion(Region.LAN);
				return "-1";
			case "kr":
				RiotAPI.setMirror(Region.KR);
				RiotAPI.setRegion(Region.KR);
				return "-1";
			case "euw":
				RiotAPI.setMirror(Region.EUW);
				RiotAPI.setRegion(Region.EUW);
				return "-1";
			case "eune":
				RiotAPI.setMirror(Region.EUNE);
				RiotAPI.setRegion(Region.EUNE);
				return "-1";
			case "br":
				RiotAPI.setMirror(Region.BR);
				RiotAPI.setRegion(Region.BR);
				return "-1";
			}
			return "You have used an incorrect region, please try again.";
		} catch (Exception e) {
			return "You have used an incorrect region, please try again.";
		}

	}

	public static String getLevel(String summonerName) { // this
																		// is
																		// broken,
																		// always
																		// catches
		try {
			return summoner.getName() + " is level " + summoner.getLevel();
		} catch (Exception e) {
			System.out.println(e);
			// return
			// "An error has occured checking for the level of the user, perhaps you used the wrong name or region?";
			return "An error has occured when checking the level of the user. This is most likely due to an incorrect name.";
		}
	}

        public static String getMap(){
        
            String map = summoner.getCurrentGame().getMap().toString().replaceAll("_"," ");
            map = map.substring(0,1)
                .concat(map.substring(1,map.indexOf(" ")).toLowerCase())
                .concat(map.substring(map.indexOf(" "), map.indexOf(" ") + 2))
                .concat(map.substring(map.indexOf(" ") + 2).toLowerCase());
        return map;
        
        }
        
        public static String getMode(){
            return summoner.getCurrentGame().getMode().toString().toLowerCase();
        }
        
        public static String getTier(){     //silver, gold, etc
            String tier = summoner.getLeagues().get(0).getTier().toString(); 
            return tier.substring(0,1).concat(tier.substring(1).toLowerCase());
        }
        
        public static String getDivision(){
        
            return summoner.getLeagueEntries().get(0).getEntries().get(0).getDivision(); //2, 3, etc
            
        }
        
        public static boolean isSetup(){
        
            return isSetup;
            
        }
}
