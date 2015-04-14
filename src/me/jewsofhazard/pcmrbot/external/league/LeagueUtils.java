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

	private static String apiKey = "df57fbca-8417-4af6-92d0-3150cb01e1f7";

	// private final String NAME_URL = "https://" + region +
	// ".api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+summoner+"?api_key="+apiKey;

	public static String getSummonerRank(String region, String summonerName) {
		String regionTest = setRegion(region);
		RiotAPI.setAPIKey(apiKey);
		summoner = RiotAPI.getSummonerByName(summonerName);
		try {
			if (regionTest.equals("-1")) {
				return summoner.getName()
						+ " is in "
						+ summoner.getLeagues().get(0).getTier().toString()
								.toLowerCase();
			}
		} catch (Exception e) {
			return getLevel(region, summonerName);
		}
		if (!regionTest.equals("-1")) {
			return regionTest;
		}
		return "";
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

	public static String getLevel(String region, String summonerName) { // this
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
			return "";
		}
	}
}
