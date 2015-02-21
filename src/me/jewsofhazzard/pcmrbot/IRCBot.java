/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Saso, James Wolff, Kyle Nabinger
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.jewsofhazzard.pcmrbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



//
import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazzard.pcmrbot.util.TType;
import me.jewsofhazzard.pcmrbot.util.Timeouts;
import me.jewsofhazzard.pcmrbot.util.Timer;
import net.swisstech.bitly.BitlyClient;
import net.swisstech.bitly.model.Response;
import net.swisstech.bitly.model.v3.ShortenResponse;

import org.jibble.pircbot.PircBot;

import com.google.gson.JsonParser;
//

/**
 *
 * @author JewsOfHazard, Donald10101, And Angablade.
 */

public class IRCBot extends PircBot {

	private boolean confirmationReplys = true;
	private String toKick;
	private boolean voteKickActive;
	private boolean voteCall;
	private boolean raffleActive;
	private ArrayList<String> inRaffle;		//Let it be known, Mr_chris is the first user to ever win the raffle.
	private String raffleType;
	private ArrayList<ArrayList<String>> voting = new ArrayList<>();
	private ArrayList<String> voteKickCount;
	private static HashMap<String, String> chatPostSeen = new HashMap<>();
	private ArrayList<String> ringazinUsers = new ArrayList<>(); // ringazin,
																	// may he
																	// forever
																	// be known
																	// as the
																	// one who
																	// initially
																	// tried to
																	// vote for
																	// an option
																	// out of
																	// the
																	// bounds of
																	// the
																	// choices
	private int optionCount;
	private String connectedChannel;
	private int paragraphLength = 250;
	private int numSymbols = 15;
	private int numEmotes = 10;
	Random rand = new Random();

	private static final Logger logger = Logger.getLogger(IRCBot.class + "");

	/**
	 * Creates a new instance of IRCBot for the specified channel
	 * 
	 * @param channel
	 *            - The IRC Channel we are connecting to.
	 */
	public IRCBot(String channel) {
		connectedChannel = channel;
		this.setName(MyBotMain.getBotChannel().substring(1));
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		if (channel.equalsIgnoreCase(connectedChannel)) {
			addModerator(recipient);
		}
	}
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname){
		
		sendMessage(connectedChannel, "Welcome " + sender + " to our channel, may you find it entertaining or flat out enjoyable.");
		
	}
	
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {

		Date date = new Date();
		chatPostSeen.put(sender, channel + "|" + date.toString());
			
		/*
		if (!isMod(sender)) {
			if (message.matches("[A-Z]{20,}")) {
				new Timeouts(connectedChannel, sender, 1, TType.CAPS);
			} else if (message
					.matches("([A-Za-z0-9_:/\\-@\\\\s.]+[\\s?\\.\\s?]?)+([\\s?\\.\\s?](c\\s?o\\s?m|n\\s?e\\s?t|o\\s?r\\s?g|c\\s?o|a\\s?u|u\\s?k|u\\s?s|m\\s?e|b\\s?z|i\\s?n\\s?t|e\\s?d\\s?u|g\\s?o\\s?v\\s?|m\\s?i\\s?l|a\\s?c)(\\s)?(/)?)+")) {
				new Timeouts(connectedChannel, sender, 1, TType.LINK);
			} else if (message.matches("[\\W_]{" +numSymbols + ",}")) {
				new Timeouts(connectedChannel, sender, 1, TType.SYMBOLS);
			} else if (message.length() >= paragraphLength) {
				new Timeouts(connectedChannel, sender, 1, TType.PARAGRAPH);
			} else if (message
					.matches("(:\\(|:\\)|:/|:D|:o|:p|:z|;\\)|;p|<3|>\\(|B\\)|o_o|R\\)|4Head|ANELE|ArsonNoSexy|AsianGlow|AtGL|AthenaPMS|AtIvy|BabyRage|AtWW|BatChest|BCWarrior|BibleThump|BigBrother|BionicBunion|BlargNaut|BloodTrail|BORT|BrainSlug|BrokeBack|BuddhaBar|CougarHunt|DAESuppy|DansGame|DatSheffy|DBstyle|DendiFace|DogFace|EagleEye|EleGiggle|EvilFetus|FailFish|FPSMarksman|FrankerZ|FreakinStinkin|FUNgineer|FunRun|FuzzyOtterOO|GasJoker|GingerPower|GrammarKing|HassaanChop|HassanChop|HeyGuys|HotPokket|HumbleLife|ItsBoshyTime|Jebaited|KZowl|JKanStyle|JonCarnage|KAPOW|Kappa|Keepo|KevinTurtle|Kippa|Kreygasm|KZassault|KZcover|KZguerilla|KZhelghast|KZskull|Mau5|mcaT|MechaSupes|MrDestructoid|MrDestructoid|MVGame|NightBat|NinjaTroll|NoNoSpot|noScope|NotAtk|OMGScoots|OneHand|OpieOP|OptimizePrime|panicBasket|PanicVis|PazPazowitz|PeoplesChamp|PermaSmug|PicoMause|PipeHype|PJHarley|PJSalt|PMSTwin|PogChamp|Poooound|PRChase|PunchTrees|PuppeyFace|RaccAttack|RalpherZ|RedCoat|ResidentSleeper|RitzMitz|RuleFive|Shazam|shazamicon|ShazBotstix|ShazBotstix|ShibeZ|SMOrc|SMSkull|SoBayed|SoonerLater|SriHead|SSSsss|StoneLightning|StrawBeary|SuperVinlin|SwiftRage|TF2John|TheRinger|TheTarFu|TheThing|ThunBeast|TinyFace|TooSpicy|TriHard|TTours|UleetBackup|UncleNox|UnSane|Volcania|WholeWheat|WinWaker|WTRuck|WutFace|YouWHY|\\(mooning\\)|\\(poolparty\\)|\\(puke\\)|:'\\(|:tf:|aPliS|BaconEffect|BasedGod|BroBalt|bttvNice|ButterSauce|cabbag3|CandianRage|CHAccepted|CiGrip|ConcernDoge|D:|DatSauce|FapFapFap|FishMoley|ForeverAlone|FuckYea|GabeN|HailHelix|HerbPerve|Hhhehehe|HHydro|iAMbh|iamsocal|iDog|JessSaiyan|JuliAwesome|KaRappa|KKona|LLuda|M&Mjc|ManlyScreams|NaM|OhGod|OhGodchanZ|OhhhKee|OhMyGoodness|PancakeMix|PedoBear|PedoNam|PokerFace|PoleDoge|RageFace|RebeccaBlack|RollIt!|rStrike|SexPanda|She'llBeRight|ShoopDaWhoop|SourPls|SuchFraud|SwedSwag|TaxiBro|tEh|ToasTy|TopHam|TwaT|UrnCrown|VisLaud|WatChuSay|WhatAYolk|YetiZ|PraiseIt|\\s){"+numEmotes+",}")) {
				new Timeouts(connectedChannel, sender, 1, TType.EMOTE);
			}
			ResultSet rs = Database
					.executeQuery("SELECT * FROM " + Database.DATABASE
							+ "." + connectedChannel.substring(1) + "Spam");
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
		} */
		
		if(message.startsWith("!lmgtfy ")) {
			message=message.substring(message.indexOf(' '));
			String param=message.replace(' ', '+');
			sendMessage(connectedChannel, "http://lmgtfy.com?q="+param);
		} else if(message.toLowerCase().startsWith("!title ") && isMod(sender)) {
			message=message.substring(message.indexOf(' '));
			if(TwitchUtilities.updateTitle(connectedChannel.substring(1), message)) {
				sendMessage(connectedChannel, "Successfully changed the stream title to \""+message+"\"!");
			} else {
				sendMessage(connectedChannel, "I am not authorized to do that visit http://pcmrbot.no-ip.info/authorize to allows me to do this and so much more!");
			}
		} else if(message.toLowerCase().startsWith("!game ") && isMod(sender)) {
			TwitchUtilities.updateTitle(connectedChannel.substring(1), message.substring(message.indexOf(' ')));
		} else if(message.equalsIgnoreCase("!clearAutoReplies")) {
			if(sender.equalsIgnoreCase(channel.substring(1))) {		//This makes sure ONLY the channel admin can run this
				sendMessage(connectedChannel, channel.substring(1) + " has cleared the auto replies.");
				Database.clearAutoRepliesTable(channel.substring(1));
			}
		} else if (message.equalsIgnoreCase("!help")) {
			sendMessage(
					connectedChannel,
					"http://pcmrbot.no-ip.info/commands");
		} else if (message.startsWith("!help ")){
			
			message = message.substring(message.indexOf(" ") + 1);
			
			if(message.equalsIgnoreCase("votestart")){
				
				sendMessage(connectedChannel, "The format of the votestart command is as follows:"
						+ " !votestart {time in seconds}|{question to ask}|{option 1}|{infinte more options}");
				sendMessage(connectedChannel, "Note, you do not need { or } and you must not add spaces "
						+ "between |."
						+ " For example, !votestart 30|What game should I play?|Bioshock|Minecraft|League. Is perfect.");
			}
			else if(message.equalsIgnoreCase("addautoreply")){
				
				sendMessage(connectedChannel, "Autoreply is formated similarly to starting votes. All you need is to type"
						+ " !addautoreply {keyword}|{keyword}|{reply}. Note: again, there can be no spaces between pipes ( | )"
						+ ". The difference is that you may add as many keywords as you like as long as the reply is last.");
				
			}
			else if(message.equalsIgnoreCase("raffle")){
				
				sendMessage(connectedChannel, "A raffle's context is simply !raffle {type} where type could be (all, follower or follwers,"
						+ " subscriber or subscribers.");
				
			}
			else if(message.equalsIgnoreCase("shorten")){
				
				sendMessage(connectedChannel,
						"This is simply !shorten {link} to make it a bit.ly link.");
				
			}
			else if(message.equalsIgnoreCase("seen")){
				
				sendMessage(connectedChannel,
						"The syntax for this is !seen {user} and will tell you how long it has been since {user} has chatted.");
				
			}
			else if(message.equalsIgnoreCase("slap")){
				
				sendMessage(connectedChannel, "This slaps the targeted user. !slap {user}.");
				
			}
			else{
				
				sendMessage(connectedChannel, "I am sorry " + sender + " we have not added command-specific help for that command yet. Please proceed to "
						+ "http://pcmrbot.no-ip.info/commands for more information.");
				
			}
			
			
		}   else if(message.equalsIgnoreCase("!commercial")){
		
			TwitchUtilities.runCommercial(connectedChannel);
		
		}	else if(message.toLowerCase().startsWith("!slow ")){
			
			if(isMod("pcmrbot")){
				
				sendMessage(connectedChannel, "/slow " + message.substring(message.indexOf(" ")+1));
				
			}
			else{
				
				sendMessage(connectedChannel, "I am sorry, the pcmrbot is not running as a moderator in the channel.");
				
			}
		
		}   else if(message.equalsIgnoreCase("!slow")){
			
			if(isMod("pcmrbot")){
				sendMessage(connectedChannel, "/slowoff");
			}
			else{
				
				sendMessage(connectedChannel, "I am sorry, the pcmrbot is not running as a moderator in the channel.");
				
			}
				
		}	else if(message.equalsIgnoreCase("!disablereplies") && isMod(sender)){
			
			this.confirmationReplys = false;
			sendMessage(connectedChannel, sender + " has disabled bot replies");
		
		}	else if(message.equalsIgnoreCase("!enablereplies") && isMod(sender)){
			
			this.confirmationReplys = true;
			sendMessage(connectedChannel, sender + " has enabled bot replies");
		
		}   else if(message.toLowerCase().startsWith("!changeoption ")){
		
			message = message.substring(message.indexOf(" ") + 1);
			String [] command = message.split("[|]");
			changeOption(command);
		
		}   else if (message.toLowerCase().startsWith("!votestart ") && isMod(sender)) {

			voting = new ArrayList<>();
			ringazinUsers = new ArrayList<>();
			optionCount = 0;

			message = message.substring(message.indexOf(" ") + 1);
			String[] voteOptions = message.split("[|]");
			String[] answers = new String[voteOptions.length - 2];
			if(answers.length<2) {
				sendMessage(connectedChannel, "You must provide at least two answers!");
				return;
			}
			for (int i = 2; i < voteOptions.length; i++) {
				answers[i - 2] = voteOptions[i];
				optionCount++;
			}
			sendMessage(connectedChannel, voteOptions[1]);

			for (int i = 0; i < answers.length; i++) {

				sendMessage(connectedChannel, (i + 1) + ": " +  answers[i]);
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
			setVoteCall(true);
			try {
				vote((long) Integer.valueOf(voteOptions[0]));
			} catch (NumberFormatException e) {
				
			}
		} else if (message.toLowerCase().startsWith("!shorten ") && isMod(sender)) {
			String out = shortenURL(message.substring(message.indexOf(" ") + 1));
			if(out == null) {
				sendMessage(connectedChannel, message.substring(message.indexOf(' ')+1) + " is an invalid url! Make sure you include http(s)://.");
			}
			sendMessage(connectedChannel, "URL: " + out);
			
		} else if (message.toLowerCase().startsWith("!slap ")) {
			String target = message.substring(message.indexOf(" ") + 1);
			sendAction(connectedChannel, "slaps " + target + " with a raw fish");
			
		} else if (message.toLowerCase().startsWith("!seen ")) {
			String target = message.substring(message.indexOf(" ") + 1);
			if(chatPostSeen.containsKey(target)) {
				// they have a recent message in the chatPostSeen map
				// the info of the message (channel & date)
				String info = chatPostSeen.get(target);

				String[] tokens = info.split("|");
	
				sendMessage(connectedChannel, sender + ", I last saw " + target + " in " + tokens[0].substring(1) + " on " + tokens[1] + ".");
			}
			
			else {
				// they haven't chatted  (They are not in the map)
				sendMessage(connectedChannel, "I'm sorry " + sender + ", I haven't seen " + message + ".");
			}
		}  else if(message.toLowerCase().startsWith("!votekick ") && !voteKickActive && isMod(sender)){
		
			message = message.substring(message.indexOf(" ") + 1);
			voteKick(message);
		
		}  else if(message.equalsIgnoreCase("!votekick") && voteKickActive){
			
			addToVoteKickCount(sender);
		
		}  else if (message.toLowerCase().startsWith("!vote ") && voteCall) {

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
				int vote = Integer.valueOf(message.substring(message.indexOf(" ")+ 1));
				if(message.substring(message.indexOf(" ")+1).equalsIgnoreCase("random")){
					vote = rand.nextInt(optionCount);
				}		
					
				if (vote < optionCount + 1) {
					voting.get(vote - 1).add(sender);
				} else {
					sendMessage(
							connectedChannel,
							sender
									+ " tried to break me, may hell forever reign upon him! (You cannot participate in this vote.)");
					ringazinUsers.add(sender);
					return;
				}
				if(confirmationReplys){
				sendMessage(connectedChannel, sender + " has voted for "
						+ voting.get(vote - 1).get(0));
				}

			}

		} else if (message.toLowerCase().startsWith("!enter") && raffleActive){
			
			joinRaffle(sender, raffleType);
			
		} else if (message.toLowerCase().startsWith("!addmod ") && sender.equals(connectedChannel.substring(1))) {

			message = message.substring(message.indexOf(" ") + 1);
			addModerator(message);

		} else if (message.equalsIgnoreCase("!join")) {

			joinMe(sender);

		} else if (message.equalsIgnoreCase("!leave")) {

			leaveMe(sender);

		} else if (message.equalsIgnoreCase("!pcmrbot")) {
			sendMessage(
					connectedChannel,
					"I was made by J3wsOfHazard, Donald10101, and Angablade. Source at: http://github.com/jwolff52/PCMRBot");
		} else if (message.toLowerCase().startsWith("!addautoreply ") && isMod(sender)) {

			autoReply(message);
			
		} else if(message.toLowerCase().startsWith("!raffle ") && isMod(sender)){
			
			message = message.substring(message.indexOf(" ") + 1);
			sendMessage(connectedChannel, "The raffle has begun for " + message);
			raffle(message);
			
		}
		
		ResultSet rs;
		rs = Database.executeQuery("SELECT * FROM " + Database.DATABASE + "." + connectedChannel.substring(1) + "AutoReplies");
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

	/**
	 * Sends a message to the channel telling them how long they have to vote
	 * and sets up the timer
	 * 
	 * @param time
	 *            - The amount of time (in seconds) the vote will last.
	 */
	public void vote(long time) {

		sendMessage(connectedChannel, "You have " + time + " seconds to vote.");

		new Timer(connectedChannel, time, "vote");

	}

	/**
	 * Tallies and decides what option wins the vote, then reports it to the
	 * channel.
	 */
	public void voteCounter() {

		int chosen = 0;

		for (int i = 0; i < voting.size(); i++) {

			if (voting.get(i).size() > voting.get(chosen).size())

				chosen = i;

		}

		sendMessage(connectedChannel,
				"The community chose " + voting.get(chosen).get(0));

	}

	/**
	 * <p>
	 * Does one of two things.
	 * </p>
	 * 
	 * <p>
	 * 1. Add's a moderator to the channel's Mod's table in the database and
	 * notifies the channel
	 * </p>
	 * <p>
	 * 2. Notifies the channel that the user is already a moderator
	 * </p>
	 * 
	 * @param moderator
	 */
	public void addModerator(String moderator) {
		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DATABASE + "." + connectedChannel.substring(1)
				+ "Mods WHERE userID=\'" + moderator + "\'");
		try {
			if (rs.next()) {
				sendMessage(connectedChannel, moderator
						+ " is already a moderator!");
			} else {
				Database.executeUpdate("INSERT INTO " + Database.DATABASE
						+ "." + connectedChannel.substring(1)
						+ "Mods VALUES(\'" + moderator + "\')");
				sendMessage(connectedChannel, "Successfully added " + moderator
						+ " to the bots mod list!");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred adding " + moderator
					+ " to " + connectedChannel
					+ "'s Mod List. This can probably be ignored!", e);
		}
	}

	public boolean isMod(String sender) {

		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DATABASE + "." + connectedChannel.substring(1)
				+ "Mods WHERE userID=\'" + sender + "\'");
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred checking if " + sender
					+ " is a mod of " + connectedChannel, e);
			return false;
		}
	}

	/**
	 * Checks if the sender is a moderator for the channel
	 * 
	 * @param sender
	 */
	public void joinMe(String sender) {

		if (connectedChannel.equalsIgnoreCase(MyBotMain.getBotChannel())) {
			new MyBotMain("#" + sender);
			sendMessage(connectedChannel, "I have joined " + sender
					+ "'s channel.");
		}

	}

	/**
	 * Leaves the channel provided. (WIP?)
	 * 
	 * @param channel
	 */
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
							+ ", I cannot allow you to disconnect me from my home channel.");
		}
	}

	/**
	 * Setup messages sent when the bot join's a channel for the first time.
	 * (WIP)
	 */
	public void onFirstJoin() {
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
				"To change this, please run !changeOption {type (emotes, paragraph, symbols}|{new value}. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");
		sendMessage(
				connectedChannel,
				"Also, if you are partnered and would wish to use subscriber raffles or change the stream title and game, please go to http://162.212.1.135/authorize to allow your chat.");
		
	}

	/**
	 * Add's an auto reply to the channels database and send a confirmation
	 * message to the channel
	 * 
	 * @param message
	 */
	public void autoReply(String message) {

		message = message.substring(message.indexOf(" ") + 1);
		String[] cutUp = message.split("[|]");
		StringBuilder keywords = new StringBuilder();
		for (int i = 0; i < cutUp.length - 2; i++) {

			keywords.append(cutUp[i] + ",");

		}
		keywords.append(cutUp[cutUp.length - 2]);
		String reply = cutUp[cutUp.length - 1];
		Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
				+ connectedChannel.substring(1) + "AutoReplies VALUES(\'"
				+ keywords.toString() + "\' , '" + reply + "\')");

		sendMessage(
				connectedChannel,
				"Added auto reply: "
						+ reply
						+ " When all of the following key words are said in a message: "
						+ keywords.toString());
		
		

	}
	
	public static String shortenURL(String link) {
		BitlyClient client=new BitlyClient("596d69348e5db5711a9f698ed606f4500fe0e766");
		Response<ShortenResponse> repShort = client.shorten().setLongUrl(link).call();
		System.out.println(repShort.toString());
		
		if(repShort.status_txt.equalsIgnoreCase("ok")) {
			return new JsonParser().parse(repShort.data.toString()).getAsJsonObject().getAsJsonPrimitive("url").getAsString();
		}
		return null;
	}

	public void raffle(String type) {
		raffleActive = true;
		this.raffleType = type;
		inRaffle = new ArrayList<>();
		new Timer(connectedChannel, 300, "raffle");
		

	}
	
	public void raffleCount(){
		
		sendMessage(connectedChannel, "There raffle has closed. There are "
				+ inRaffle.size()
				+ " users in the raffle.");
		int selection = rand.nextInt(inRaffle.size());
		sendMessage(connectedChannel, "The raffle winner is " + inRaffle.get(selection) + ".");
		
	}
	
	public void joinRaffle(String sender, String raffleType){
		boolean inRaffleAlready = false;
		
		if(inRaffle.contains(sender)){
			
			inRaffleAlready = true;
			sendMessage(connectedChannel, sender + " is a dirty cheater and tried to join the raffle more than once, may he be smiten.");
			
			
		}
			
		

		if((raffleType.equalsIgnoreCase("follower") || raffleType.equalsIgnoreCase("followers")) && (isFollower(sender) || sender.equalsIgnoreCase(connectedChannel.substring(1))) && !inRaffleAlready){
			
			inRaffle.add(sender);
			if(confirmationReplys){
			sendMessage(connectedChannel, sender + " has joined the raffle.");
			}
		} else if((raffleType.equalsIgnoreCase("subscriber") || raffleType.equalsIgnoreCase("subscribers")) && (isSubscriber(sender) || sender.equalsIgnoreCase(connectedChannel.substring(1))) && !inRaffleAlready){
			
			inRaffle.add(sender);
			if(confirmationReplys){
			sendMessage(connectedChannel, sender + " has joined the raffle.");
			}
		}
		else if(raffleType.equals("all") && !inRaffleAlready) {
			
			inRaffle.add(sender);
			if(confirmationReplys){
			sendMessage(connectedChannel, sender + " has joined the raffle.");
			}
		}
		else {
			sendMessage(connectedChannel, "I am sorry "+ sender +" you are not allowed to join this raffle.");
		}
		
	}

	/**
	 * Checks to see if sender is a follower of the channel we are currently
	 * connected to.
	 * 
	 * @param sender
	 * @return true if sender is a following of connectedChannel
	 */
	public boolean isFollower(String sender) {
		return TwitchUtilities.isFollower(sender, connectedChannel);
	}

	/**
	 * Checks to see if sender is a subscriber of the channel we are currently
	 * connected to. (WIP)
	 * 
	 * @param sender
	 * @return true if sender is subscribed to connectedChannel
	 */
	public boolean isSubscriber(String sender) {
		return TwitchUtilities.isSubscriber(sender, connectedChannel);
	}

	/**
	 * Gets the channel we are currently connected to.
	 * 
	 * @return the channel we are currently connected to
	 */
	public String getChannel() {

		return connectedChannel;

	}

	/**
	 * Sets the boolean voteCall to the value of set.
	 * 
	 * @param set
	 */
	public void setVoteCall(boolean set) {

		this.voteCall = set;

	}
	public void setRaffle(boolean set){
		
		this.raffleActive = set;
		
	}
	public void voteKick(String toKick){
		this.toKick = toKick;
		voteKickCount = new ArrayList<>();
		sendMessage(connectedChannel, "The channel has begun a vote to kick " + toKick + ". Type !voteKick to place your vote. To vote no"
				+ ", just do not vote.");
		setVoteKickActive(true);
		new Timer(connectedChannel, 180, "kick");
		
	}
	public void setVoteKickActive(boolean set){
		
		this.voteKickActive = set;
		
	}
	public void voteKickCount(){
		
		if((voteKickCount.size() / getUsers(connectedChannel).length) >= .55){
			
			sendMessage(connectedChannel, "The community has chosen to kick " + toKick + ".");
			
		}
		else{
			
			sendMessage(connectedChannel, "The community has chosen to spare " + toKick + ".");
			
		}
		
	}
	public void addToVoteKickCount(String sender){
		
		if(voteKickCount.contains(sender)){
			
			sendMessage(connectedChannel, sender + " tried to kick "+ toKick + " twice. Do they have a personal vendetta? That is for me to know.");
			
		}
		else{
			if(confirmationReplys){
			sendMessage(connectedChannel, sender + " has has voted to kick " + toKick + ".");
			}
			voteKickCount.add(sender);
			
		}
		
	}

	
	public void changeOption(String [] option){
		
		if(option[0].equalsIgnoreCase("paragraph")){
			
			paragraphLength = Integer.valueOf(option[1]);
			sendMessage(connectedChannel, "You have changed the value to " + option[1] + ".");
			
		}
		
		else if(option[0].equalsIgnoreCase("emotes")){
					
			numEmotes = Integer.valueOf(option[1]);	
			sendMessage(connectedChannel, "You have changed the value to " + option[1] + ".");		
				}
		
		else if(option[0].equalsIgnoreCase("symbol")){
			
			numSymbols = Integer.valueOf(option[1]);	
			sendMessage(connectedChannel, "You have changed the value to " + option[1] + ".");
		}
		
		else{
			
			sendMessage(connectedChannel, "I am sorry, but you have tried to change a type of value that is not supported. Valid options are \"symbol,\" \"emotes,\" or \"paragraph,\"");
			
		}
		
	}
	

}