package me.jewsofhazzard.pcmrbot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.TType;
import me.jewsofhazzard.pcmrbot.util.Timeouts;
import me.jewsofhazzard.pcmrbot.util.Timer;

import org.jibble.pircbot.PircBot;

/**
 *
 * @author JewsOfHazard, Donald10101, And peoples.
 */

public class IRCBot extends PircBot {

	@SuppressWarnings("unused")
	private boolean timer = false;
	private boolean voteCall;
	private ArrayList<ArrayList<String>> voting = new ArrayList<>();
	private ArrayList<string> chatpostseen = new arraylist<>();
	private ArrayList<String> ringazinUsers = new ArrayList<>(); // ringazin, may he forever be known as the one who initially tried to vote for an option out of the bounds of the choices
	private int optionCount;
	private String connectedChannel;
	@SuppressWarnings("unused")
	private Robot robot;

	private static final Logger logger = Logger.getLogger(IRCBot.class + "");

	public IRCBot(String channel) {
		connectedChannel = channel;
		this.setName(MyBotMain.getBotChannel().substring(1));
		try {
			this.robot = new Robot();
		} catch (AWTException ex) {
			logger.log(Level.SEVERE,
					"An error occurred initializing the Robot!", ex);
		}
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		if (channel.equalsIgnoreCase(connectedChannel)) {
			addModerator(recipient);
		}
	}

	/* (non-Javadoc)
	 * @see org.jibble.pircbot.PircBot#onMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {

			Date date = new Date();
			chatpostseen.add(sender + "|" + channel + "|" + date.tostring());
			
			
		if (!isMod(sender)) {
			if (message.matches("[A-Z]{20,}")) {
				new Timeouts(connectedChannel, sender, 1, TType.CAPS);
			} else if (message
					.matches("([A-Za-z0-9_:/\\-@\\\\s.]+[\\s?\\.\\s?]?)+([\\s?\\.\\s?](c\\s?o\\s?m|n\\s?e\\s?t|o\\s?r\\s?g|c\\s?o|a\\s?u|u\\s?k|u\\s?s|m\\s?e|b\\s?z|i\\s?n\\s?t|e\\s?d\\s?u|g\\s?o\\s?v\\s?|m\\s?i\\s?l|a\\s?c)(\\s)?(/)?)+")) {
				new Timeouts(connectedChannel, sender, 1, TType.LINK);
			} else if (message.matches("[\\W_]{15,}")) {
				new Timeouts(connectedChannel, sender, 1, TType.SYMBOLS);
			} else if (message.length() >= 250) {
				new Timeouts(connectedChannel, sender, 1, TType.PARAGRAPH);
			} else if (message
					.matches("(:\\(|:\\)|:/|:D|:o|:p|:z|;\\)|;p|<3|>\\(|B\\)|o_o|R\\)|4Head|ANELE|ArsonNoSexy|AsianGlow|AtGL|AthenaPMS|AtIvy|BabyRage|AtWW|BatChest|BCWarrior|BibleThump|BigBrother|BionicBunion|BlargNaut|BloodTrail|BORT|BrainSlug|BrokeBack|BuddhaBar|CougarHunt|DAESuppy|DansGame|DatSheffy|DBstyle|DendiFace|DogFace|EagleEye|EleGiggle|EvilFetus|FailFish|FPSMarksman|FrankerZ|FreakinStinkin|FUNgineer|FunRun|FuzzyOtterOO|GasJoker|GingerPower|GrammarKing|HassaanChop|HassanChop|HeyGuys|HotPokket|HumbleLife|ItsBoshyTime|Jebaited|KZowl|JKanStyle|JonCarnage|KAPOW|Kappa|Keepo|KevinTurtle|Kippa|Kreygasm|KZassault|KZcover|KZguerilla|KZhelghast|KZskull|Mau5|mcaT|MechaSupes|MrDestructoid|MrDestructoid|MVGame|NightBat|NinjaTroll|NoNoSpot|noScope|NotAtk|OMGScoots|OneHand|OpieOP|OptimizePrime|panicBasket|PanicVis|PazPazowitz|PeoplesChamp|PermaSmug|PicoMause|PipeHype|PJHarley|PJSalt|PMSTwin|PogChamp|Poooound|PRChase|PunchTrees|PuppeyFace|RaccAttack|RalpherZ|RedCoat|ResidentSleeper|RitzMitz|RuleFive|Shazam|shazamicon|ShazBotstix|ShazBotstix|ShibeZ|SMOrc|SMSkull|SoBayed|SoonerLater|SriHead|SSSsss|StoneLightning|StrawBeary|SuperVinlin|SwiftRage|TF2John|TheRinger|TheTarFu|TheThing|ThunBeast|TinyFace|TooSpicy|TriHard|TTours|UleetBackup|UncleNox|UnSane|Volcania|WholeWheat|WinWaker|WTRuck|WutFace|YouWHY|\\(mooning\\)|\\(poolparty\\)|\\(puke\\)|:'\\(|:tf:|aPliS|BaconEffect|BasedGod|BroBalt|bttvNice|ButterSauce|cabbag3|CandianRage|CHAccepted|CiGrip|ConcernDoge|D:|DatSauce|FapFapFap|FishMoley|ForeverAlone|FuckYea|GabeN|HailHelix|HerbPerve|Hhhehehe|HHydro|iAMbh|iamsocal|iDog|JessSaiyan|JuliAwesome|KaRappa|KKona|LLuda|M&Mjc|ManlyScreams|NaM|OhGod|OhGodchanZ|OhhhKee|OhMyGoodness|PancakeMix|PedoBear|PedoNam|PokerFace|PoleDoge|RageFace|RebeccaBlack|RollIt!|rStrike|SexPanda|She'llBeRight|ShoopDaWhoop|SourPls|SuchFraud|SwedSwag|TaxiBro|tEh|ToasTy|TopHam|TwaT|UrnCrown|VisLaud|WatChuSay|WhatAYolk|YetiZ|PraiseIt|\\s){8,}")) {
				new Timeouts(connectedChannel, sender, 1, TType.EMOTE);
			}
			ResultSet rs = Database
					.executeQuery("SELECT * FROM " + Database.DEFAULT_SCHEMA
							+ "." + connectedChannel.substring(0) + "Spam");
			try {
				while (rs.next()) {
					if (message.matches("(" + rs.getString(1) + ")+")) {
						new Timeouts(connectedChannel, sender, 1, TType.SPAM);
					}
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "An error occurred checking if "
						+ sender + "'s message has bad words", e);
			}
		}
		
		if(message.equalsIgnoreCase("!clearAutoReplies")) {
			if(sender.equalsIgnoreCase(channel.substring(1))) {
				Database.clearAutoRepliesTable(channel.substring(1));
			}
		}

		if (message.equalsIgnoreCase("!helppcmr")) {

			sendMessage(
					connectedChannel,
					"Current commands: !join (ask pcmr bot nicely to join your chat) !pcmrbot (information) !vote {choice} (vote during active votes) !votestart (admin only) !addmod (admin only).");

		}

		if (message.toLowerCase().startsWith("!votestart ") && isMod(sender)) {

			voting = new ArrayList<>();
			ringazinUsers = new ArrayList<>();
			optionCount = 0;

			message = message.substring(message.indexOf(" ") + 1);
			String[] voteOptions = message.split("[|]");
			String[] answers = new String[voteOptions.length - 2];
			for (int i = 2; i < voteOptions.length; i++) {
				answers[i - 2] = voteOptions[i];
				optionCount++;
			}
			sendMessage(connectedChannel, voteOptions[1]);

			for (int i = 0; i < answers.length; i++) {

				sendMessage(connectedChannel, answers[i]);
				voting.add(new ArrayList<String>());
				voting.get(i).add(answers[i]);

			}

			sendMessage(
					connectedChannel,
					"Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

			for (int i = 0; i < answers.length; i++) {

				voting.add(new ArrayList<String>());
				voting.get(i).add(voteOptions[i]);

			}

			setTimer(false);
			setVoteCall(true);
			vote((long) Integer.valueOf(voteOptions[0]));

		}

		
		if (message.ToLowerCase().startswith("!seen ") {
			
			message = message.substring(message.indexOf(" ") + 1);

			String strtosend = "";
			
			for(int i = 0; i < chatpostseen.length - 1; i++){
				String element = chatpostseen[i];
				
				if (element.startswith(message))
				{
					strtosend = element;
				}
			}
			
			if (strtosend.contains("|") = true) {
				String[] tokens = strtosend.split("\\|");
				sendMessage(connectedChannel, sender + ", I last seen '" + tokens[0] + "' was in " + tokens[1] + " on " + tokens[2] + ".");
			} else {
				sendMessage(connectedChannel, "I am sorry " + sender + ", I have not seen them.");
			}

		}
		
		
		if (message.toLowerCase().startsWith("!vote ") && voteCall) {

			boolean canVote = true;

			for (int i = 0; i < voting.size(); i++) {

				if (voting.get(i).contains(sender)) {

					sendMessage(connectedChannel, "I am sorry " + sender
							+ " you cannot vote more than once.");
					canVote = false;
				}

			}

			if (ringazinUsers.contains(sender)) {

				sendMessage(connectedChannel, "I am sorry " + sender
						+ " you cannot vote more than once.");
				canVote = false;
			}

			if (canVote) {
				int vote = Integer.valueOf(message.substring(message
						.indexOf(" ") + 1));
				if (vote < optionCount) {
					voting.get(vote - 1).add(sender);
				} else {
					sendMessage(
							connectedChannel,
							sender
									+ " tried to break me, may hell forever reign upon him! (You cannot participate in this vote.)");
					ringazinUsers.add(sender);
					return;
				}
				sendMessage(connectedChannel, sender + " has voted for "
						+ voting.get(vote - 1).get(0));

			}

		}

		if (message.toLowerCase().startsWith("!addmod ") && isMod(sender)) {

			message = message.substring(message.indexOf(" ") + 1);
			addModerator(message);

		}

		if (message.equalsIgnoreCase("!join")) {

			joinMe(sender);

		}

		if (message.equalsIgnoreCase("!leave")) {

			leaveMe(sender);

		}

		if (message.equalsIgnoreCase("!pcmrbot")) {

			sendMessage(
					connectedChannel,
					"I was made by J3wsOfHazard in free time during an AP Computer Science class in high school.");

		}
		
		if (message.equalsIgnoreCase("!addautoreply") && isMod(sender)) {

			autoReply(message);
			
		}
		
		ResultSet rs;
		rs = Database.executeQuery("SELECT * FROM " + Database.DEFAULT_SCHEMA + "." + connectedChannel.substring(1) + "AutoReplies");
		try {
			while(rs.next()){
				String [] keyword = rs.getString("keyword").split(",");
				StringBuilder matchMe = new StringBuilder();
				
				for(int i = 0; i < keyword.length-1; i++){
					matchMe.append("([\\s]*" + keyword[i] + "[\\s]*)+");
				}
				if(message.matches(matchMe.toString())){
					
					sendMessage(connectedChannel, rs.getString("reply"));
					
				}
				
			}
		} catch (SQLException e) {
			
			logger.log(Level.SEVERE, "An error occured while trying to access the database.", e);
		}
		
		
	}

	public void vote(long time) {

		sendMessage(connectedChannel, "You have " + time + " seconds to vote.");

		new Timer(connectedChannel, time);

	}

	public void voteCounter() {

		int chosen = 0;

		for (int i = 0; i < voting.size(); i++) {

			if (voting.get(i).size() > voting.get(chosen).size())

				chosen = i;

		}

		sendMessage(connectedChannel,
				"The community chose " + voting.get(chosen).get(0));

	}

	public void setTimer(boolean duh) {

		this.timer = duh;

	}

	public void setVoteCall(boolean set) {

		this.voteCall = set;

	}

	public void addModerator(String moderator) {
		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DEFAULT_SCHEMA + "." + connectedChannel.substring(0)
				+ "Mods WHERE userID=\'" + moderator + "\'");
		try {
			if (rs.next()) {
				sendMessage(connectedChannel, moderator
						+ " is already a moderator!");
			} else {
				Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA
						+ "." + connectedChannel.substring(0) + "Mods VALUES(\'" + moderator
						+ "\')");
				sendMessage(connectedChannel, "Successfully added " + moderator
						+ " to the bots mod list!");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred adding " + moderator
					+ " to " + connectedChannel + "'s Mod List. This can probably be ignored!", e);
		}
	}

	public boolean isMod(String sender) {

		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DEFAULT_SCHEMA + "." + connectedChannel.substring(0)
				+ "Mods WHERE userID=\'" + sender + "\'");
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred checking if " + sender
					+ " is a mod of " + connectedChannel, e);
			return false;
		}
	}

	public String getChannel() {

		return connectedChannel;

	}

	public void joinMe(String sender) {

		if (connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			new MyBotMain("#" + sender);
			sendMessage(connectedChannel, "I have joined " + sender
					+ "'s channel.");
		}

	}

	public void leaveMe(String channel) {
		if (MyBotMain.getConnectedChannel("#" + channel) != null
				&& connectedChannel != "#pcmrbot") {
			sendMessage(connectedChannel, "I have disconnected from " + channel
					+ "'s channel.");
			MyBotMain.getConnectedChannel("#" + channel).partChannel(
					"#" + channel);
			MyBotMain.getConnectedChannels().remove("#" + channel);
		} else {
			sendMessage(
					"#" + channel,
					"Sorry "
							+ channel
							+ ", I cannot allow you to disconnect me from my hope channel.");
		}
	}

	public void onJoin() {

		// if statement to check if the database has an entry for this channel.
		// Might need a for loop and an if statement inside

		sendMessage(
				connectedChannel,
				"Hello, this appears to be the first time you have invited me to join your channel. We just have a few preliminary manners to attend to.");
		sendMessage(
				connectedChannel,
				"To begin with, we use a three-part system to define a few options. Let's begin with timeing out a user.");
		sendMessage(
				connectedChannel,
				"Users are timed out for excessive caps or symbols, an excessive or exclusive message of emoticons, links, repeated messages (spam), and messages longer than 250 characters.");
		sendMessage(
				connectedChannel,
				"We would like you to configure the ammount of emoticons(default 15), capital letters(default 20), and paragraphs(defaults to 250 characters) allowed in a message.");
		sendMessage(
				connectedChannel,
				"To change this, please run !changeOption {type to change}|{new value}. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");

	}
	
	public void autoReply(String message){
		
		message = message.substring(message.indexOf(" ") + 1);
		String [] cutUp = message.split("[|]");
		StringBuilder keywords = new StringBuilder();
		for(int i = 0; i < cutUp.length-2; i++){
			
			keywords.append(cutUp[i] + ",");
			
		}
		keywords.append(cutUp.length-2);
		String reply = cutUp[cutUp.length-1];
		Database.executeUpdate("INSERT INTO " + Database.DEFAULT_SCHEMA + "." + connectedChannel.substring(1) + "AutoReplies VALUES(\'"+ keywords.toString() +"\' , '"+reply+"\')"  );
		
		
	}
	
	public boolean isFollower(String sender){
		
		if(true /*this will be where we check to see if they are a follower with the api*/){
			
			
		}
		
		return false;
	}
	
	public boolean isSubscriber(String sender){
		
		if(true /*this will be where we check to see if they are a subscriber with the api*/){
			
			
		}
		
		
		return false;
	}

}
// add a general voting system using 2d array lists to ask questions. Also, I
// can do !callvote 