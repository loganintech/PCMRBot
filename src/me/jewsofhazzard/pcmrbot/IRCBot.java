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
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.Commands.AddAutoReply;
import me.jewsofhazzard.pcmrbot.Commands.AddModerator;
import me.jewsofhazzard.pcmrbot.Commands.Broadcast;
import me.jewsofhazzard.pcmrbot.Commands.ChangeOption;
import me.jewsofhazzard.pcmrbot.Commands.ChangeWelcome;
import me.jewsofhazzard.pcmrbot.Commands.Clear;
import me.jewsofhazzard.pcmrbot.Commands.ClearAutoReplies;
import me.jewsofhazzard.pcmrbot.Commands.Commercial;
import me.jewsofhazzard.pcmrbot.Commands.DisableReplies;
import me.jewsofhazzard.pcmrbot.Commands.DisableWelcome;
import me.jewsofhazzard.pcmrbot.Commands.EnableReplies;
import me.jewsofhazzard.pcmrbot.Commands.EnableWelcome;
import me.jewsofhazzard.pcmrbot.Commands.Enter;
import me.jewsofhazzard.pcmrbot.Commands.Fatality;
import me.jewsofhazzard.pcmrbot.Commands.Fight;
import me.jewsofhazzard.pcmrbot.Commands.Gabe;
import me.jewsofhazzard.pcmrbot.Commands.Game;
import me.jewsofhazzard.pcmrbot.Commands.Help;
import me.jewsofhazzard.pcmrbot.Commands.JoinMe;
import me.jewsofhazzard.pcmrbot.Commands.KO;
import me.jewsofhazzard.pcmrbot.Commands.LMGTFY;
import me.jewsofhazzard.pcmrbot.Commands.LeaveMe;
import me.jewsofhazzard.pcmrbot.Commands.Me;
import me.jewsofhazzard.pcmrbot.Commands.PCMRBot;
import me.jewsofhazzard.pcmrbot.Commands.Poll;
import me.jewsofhazzard.pcmrbot.Commands.Punch;
import me.jewsofhazzard.pcmrbot.Commands.Raffle;
import me.jewsofhazzard.pcmrbot.Commands.Seen;
import me.jewsofhazzard.pcmrbot.Commands.Servers;
import me.jewsofhazzard.pcmrbot.Commands.Shorten;
import me.jewsofhazzard.pcmrbot.Commands.Shutdown;
import me.jewsofhazzard.pcmrbot.Commands.Slap;
import me.jewsofhazzard.pcmrbot.Commands.Slow;
import me.jewsofhazzard.pcmrbot.Commands.SlowClap;
import me.jewsofhazzard.pcmrbot.Commands.SteamSales;
import me.jewsofhazzard.pcmrbot.Commands.Subscribers;
import me.jewsofhazzard.pcmrbot.Commands.Teamspeak;
import me.jewsofhazzard.pcmrbot.Commands.Title;
import me.jewsofhazzard.pcmrbot.Commands.Vote;
import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.util.Options;
import me.jewsofhazzard.pcmrbot.util.TType;
import me.jewsofhazzard.pcmrbot.util.Timeouts;

import org.jibble.pircbot.PircBot;

//

/**
 *
 * @author JewsOfHazard, Donald10101, And Angablade.
 */

public class IRCBot extends PircBot {
	
	private boolean raffleActive;
	private static HashMap<String, String> chatPostSeen;
	private static HashMap<String,Boolean> welcomeEnabled;
	private static HashMap<String,Boolean> confirmationReplies;
	private static HashMap<String,me.jewsofhazzard.pcmrbot.util.Poll> polls;
	private static HashMap<String,me.jewsofhazzard.pcmrbot.util.Raffle> raffles;
	private static final Logger logger = Logger.getLogger(IRCBot.class + "");

	/**
	 * Creates a new instance of IRCBot for the specified channel
	 * 
	 * @param channel
	 *            - The IRC Channel we are connecting to.
	 */
	public IRCBot() {
		this.setName(MyBotMain.getBotChannel().substring(1));
		initVariables();
	}

	private void initVariables() {
		welcomeEnabled = new HashMap<>();
		confirmationReplies = new HashMap<>();
		chatPostSeen = new HashMap<>();
		polls = new HashMap<>();
		raffles = new HashMap<>();
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		try {
			try {
				new AddModerator().execute(recipient, channel);
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"An error occurred while executing onOP()", e);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onOp() in "
							+ channel, e);
		}
	}

	@Override
	public void onJoin(String channel, String sender, String login,
			String hostname) {

		try {
			if (welcomeEnabled.get(channel)) {
				if (!sender.equalsIgnoreCase("pcmrbot")) {
					sendMessage(
							channel,
							Database.getOption(channel,
									Options.welcomeMessage).replace(
									"%user%", sender));
				} else {

					sendMessage(
							channel,
							"I have joined the channel and will stay with you unless you tell me to !leave or my creators do not shut me down properly because they are cruel people with devious minds.");

				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"An error occurred while executing onJoin()", e);
		}
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {

		try {
			message=message.toLowerCase();

			checkSpam(channel, message, sender);

			Date date = new Date();
			chatPostSeen.put(sender,
					channel.substring(1) + "|" + date.toString());

			if (message.startsWith("!lmgtfy ")) {
				sendMessage(channel, new LMGTFY().execute(message.substring(message.indexOf(' ') + 1)));
			} else if(message.startsWith("!slow ") && Database.isMod(sender, channel.substring(1))){
				sendMessage(channel, new Slow().execute(channel, message.substring(message.indexOf(" ") + 1)));
			} else if (message.equalsIgnoreCase("!shutdown")) {
				if (channel.equalsIgnoreCase("#pcmrbot")
						&& Database.isMod(sender, channel.substring(1))) {
					new Shutdown().execute();
				}
			} else if (message.startsWith("!title ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Title().execute(channel, message.substring(message.indexOf(' ') + 1)));
			} else if (message.startsWith("!game ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Game().execute(channel, message.substring(message.indexOf(' ') + 1)));
			} else if (message.equalsIgnoreCase("!clearAutoReplies") && sender.equalsIgnoreCase(channel.substring(1))) {
					sendMessage(channel, new ClearAutoReplies().execute(channel, sender));
			} else if (message.equalsIgnoreCase("!help")) {
				sendMessage(channel, new Help().execute(sender));
			} else if (message.startsWith("!help ")) {
				sendMessage(message.substring(message.indexOf(' ')), sender);
			} else if (message.startsWith("!broadcast ") && Database.isMod(sender, channel.substring(1)) && channel.equalsIgnoreCase("#pcmrbot")){
				sendMessage("#pcmrbot", new Broadcast().execute(message.substring(message.indexOf(" ") + 1)));
			} else if (message.equalsIgnoreCase("!commercial") && sender.equalsIgnoreCase(channel.substring(1))) {
				sendMessage(channel, new Commercial().execute(channel));
			} else if (message.equalsIgnoreCase("!commercial ")
					&& sender.equalsIgnoreCase(channel.substring(1))) {
				sendMessage(channel, new Commercial().execute(channel, message.substring(message.indexOf(' ') + 1)));
			} else if (message.equalsIgnoreCase("!teamspeak")) {
				sendMessage(channel, new Teamspeak().execute());
			} else if (message.startsWith("!me ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Me().execute(message.substring(message.indexOf(" ") + 1)));
			} else if (message.equalsIgnoreCase("!servers")) {
				sendMessage(channel, new Servers().execute());
			} else if (message.equalsIgnoreCase("!gabe") && Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Gabe().execute());
			} else if (message.equalsIgnoreCase("!steamsales") && Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new SteamSales().execute());
			} else if (message.startsWith("!fight ")) {
				sendMessage(channel, new Fight().execute(sender, message.substring(message.indexOf(' '))));
			} else if (message.startsWith("!punch ")) {
				sendMessage(channel, new Punch().execute(sender, message.substring(message.indexOf(" ") + 1)));
			} else if (message.startsWith("!ko ")) {
				sendMessage(channel, new KO().execute(sender, message.substring(message.indexOf(" ") + 1)));
			} else if (message.startsWith("!fatality ")) {
				sendMessage(channel, new Fatality().execute(sender, message.substring(message.indexOf(" ") + 1)));
			} else if (message.equalsIgnoreCase("!subscribers")
					&& sender.equals(channel.substring(1))) {
				sendMessage(channel, new Subscribers().execute(channel));
			} else if (message.equalsIgnoreCase("!slowclap")) {
				sendMessage(channel, new SlowClap().execute(sender));
			} else if (message.equalsIgnoreCase("!clear") && Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Clear().execute());
			}  else if (message.equalsIgnoreCase("!disableWelcome")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new DisableWelcome().execute(channel));
			} else if (message.equalsIgnoreCase("!enableWelcome")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new EnableWelcome().execute(channel));
			} else if (message.startsWith("!changewelcome ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel,new ChangeWelcome().execute(channel, message.substring(message.indexOf(' '))));
			} else if (message.equalsIgnoreCase("!disablereplies")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new DisableReplies().execute(sender));
			} else if (message.equalsIgnoreCase("!enablereplies")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new EnableReplies().execute(sender));
			} else if (message.startsWith("!changeoption ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new ChangeOption().execute(channel, message.substring(message.indexOf(" ") + 1)));
			} else if (message.startsWith("!votestart ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Poll().execute(channel, message.substring(message.indexOf(' ') + 1)));
			} else if (message.startsWith("!shorten ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Shorten().execute(message.substring(message.indexOf(' ') + 1)));
			} else if (message.startsWith("!slap ")) {
				sendAction(channel, new Slap().execute(message.substring(message.indexOf(" ") + 1)));
			} else if (message.startsWith("!seen ")) {
				sendMessage(channel, new Seen().execute(sender, message.substring(message.indexOf(" ") + 1)));
			}else if (message.startsWith("!vote ") && hasPoll(channel)) {
				new Vote().execute(channel, sender, message.substring(message.indexOf(' ') + 1));
			} else if (message.startsWith("!enter")
					&& raffleActive) {
				String reply=new Enter().execute(channel, sender);
				if(reply!=null) {
					sendMessage(channel, reply);
				}
			} else if (message.startsWith("!addmod ")
					&& sender.equalsIgnoreCase(channel.substring(1))) {
				sendMessage(channel, new AddModerator().execute(message = message.substring(message.indexOf(" ") + 1), channel));
			} else if (message.equalsIgnoreCase("!join")) {
				sendMessage(channel, new JoinMe().execute(sender, channel));
			} else if (message.equalsIgnoreCase("!leave") && sender.equalsIgnoreCase(channel.substring(1))) {
				sendMessage(channel, new LeaveMe().execute(channel));
			} else if (message.equalsIgnoreCase("!pcmrbot")) {
				sendMessage(channel, new PCMRBot().execute());
			} else if (message.toLowerCase().startsWith("!addautoreply ")&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new AddAutoReply().execute(message, channel));
			} else if (message.startsWith("!raffle ")
					&& Database.isMod(sender, channel.substring(1))) {
				sendMessage(channel, new Raffle().execute(channel, message.substring(message.indexOf(' ') + 1)));
			} /*else if (message.startsWith("!votekick ")
					&& !voteKickActive && Database.isMod(sender, channel.substring(1))) {

				message = message.substring(message.indexOf(" ") + 1);
				voteKick(channel, message);

			} else if (message.equalsIgnoreCase("!votekick") && voteKickActive) {

				addToVoteKickCount(channel, sender);

			} */
			autoReplyCheck(channel, message);
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onMessage() in "
							+ channel, e);
		}
	}

	/**
	 * Setup messages sent when the bot join's a channel for the first time.
	 */
	public void onFirstJoin(String channel) {
		sendMessage(
				channel,
				"Hello, this appears to be the first time you have invited me to join your channel. We just have a few preliminary manners to attend to.");

		sendMessage(
				channel,
				"To begin with, we use a three-part system to define a few options. Let's begin with timeing out a user.");
		sendMessage(
				channel,
				"Users are timed out for excessive caps or symbols, an excessive or exclusive message of emoticons, links, repeated messages (spam), and messages longer than 250 characters.");
		sendMessage(
				channel,
				"We would like you to configure the ammount of emoticons(default 15), capital letters(default 20), and paragraphs(defaults to 250 characters) allowed in a message.");
		sendMessage(
				channel,
				"To change this, please run !changeOption {type (emotes, paragraph, symbols}|{new value}. Note: If you make paragraph to short users may not be able to post proper sentences. Think of it like twitter messages.");
		sendMessage(
				channel,
				"Also, if you are partnered and would wish to use subscriber raffles or change the stream title and game, please go to http://162.212.1.135/authorize to allow your chat.");
	}
	
	public boolean isWatchingChannel(String channel) {
		for (String s : getChannels()) {
			if(s.equalsIgnoreCase(channel)) {
				return true;
			}
		}
		return false;
	}

	public void autoReplyCheck(String channel, String message) {

		ResultSet rs;
		rs = Database.executeQuery("SELECT * FROM " + Database.DATABASE + "."
				+ channel.substring(1) + "AutoReplies");
		try {
			while (rs.next()) {
				String[] keyword = rs.getString(1).split("[,]");
				if(keyword.length == 0) {
					keyword=new String[1];
					keyword[0]=rs.getString(1);
				}
				boolean matches = true;
				for (int i = 0; i < keyword.length - 1; i++) {
					if (!message.contains(
							keyword[i].toLowerCase())) {
						matches = false;
						break;
					}
				}
				if (matches) {
					sendMessage(channel, rs.getString("reply"));
				}
			}
		} catch (SQLException e) {

			logger.log(Level.SEVERE,
					"An error occured while trying to access the database.", e);
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"An error occurred while executing onMessage()", e);
		}

	}

	public void checkSpam(String channel, String message, String sender) {

		if (!Database.isMod(sender, channel.substring(1))) {
			if (message.matches("[A-Z\\s]{"
					+ Database.getOption(channel, Options.numCaps)
					+ ",}")) {
				new Timeouts(channel, sender, 1, TType.CAPS);
			} else if (message
					.matches("([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})")
					|| message
							.matches("(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?")) {
				new Timeouts(channel, sender, 1, TType.LINK);
			} else if (message.matches("[\\W_\\s]{"
					+ Database.getOption(channel, Options.numSymbols)
					+ ",}")) {
				new Timeouts(channel, sender, 1, TType.SYMBOLS);
			} else if (message.length() >= Integer.valueOf(Database.getOption(
					channel, Options.paragraphLength))) {
				new Timeouts(channel, sender, 1, TType.PARAGRAPH);
			} else if (message
					.matches("(:\\(|:\\)|:/|:D|:o|:p|:z|;\\)|;p|<3|>\\(|B\\)|o_o|R\\)|4Head|ANELE|ArsonNoSexy|AsianGlow|AtGL|AthenaPMS|AtIvy|BabyRage|AtWW|BatChest|BCWarrior|BibleThump|BigBrother|BionicBunion|BlargNaut|BloodTrail|BORT|BrainSlug|BrokeBack|BuddhaBar|CougarHunt|DAESuppy|DansGame|DatSheffy|DBstyle|DendiFace|DogFace|EagleEye|EleGiggle|EvilFetus|FailFish|FPSMarksman|FrankerZ|FreakinStinkin|FUNgineer|FunRun|FuzzyOtterOO|GasJoker|GingerPower|GrammarKing|HassaanChop|HassanChop|HeyGuys|HotPokket|HumbleLife|ItsBoshyTime|Jebaited|KZowl|JKanStyle|JonCarnage|KAPOW|Kappa|Keepo|KevinTurtle|Kippa|Kreygasm|KZassault|KZcover|KZguerilla|KZhelghast|KZskull|Mau5|mcaT|MechaSupes|MrDestructoid|MrDestructoid|MVGame|NightBat|NinjaTroll|NoNoSpot|noScope|NotAtk|OMGScoots|OneHand|OpieOP|OptimizePrime|panicBasket|PanicVis|PazPazowitz|PeoplesChamp|PermaSmug|PicoMause|PipeHype|PJHarley|PJSalt|PMSTwin|PogChamp|Poooound|PRChase|PunchTrees|PuppeyFace|RaccAttack|RalpherZ|RedCoat|ResidentSleeper|RitzMitz|RuleFive|Shazam|shazamicon|ShazBotstix|ShazBotstix|ShibeZ|SMOrc|SMSkull|SoBayed|SoonerLater|SriHead|SSSsss|StoneLightning|StrawBeary|SuperVinlin|SwiftRage|TF2John|TheRinger|TheTarFu|TheThing|ThunBeast|TinyFace|TooSpicy|TriHard|TTours|UleetBackup|UncleNox|UnSane|Volcania|WholeWheat|WinWaker|WTRuck|WutFace|YouWHY|\\(mooning\\)|\\(poolparty\\)|\\(puke\\)|:\\'\\(|:tf:|aPliS|BaconEffect|BasedGod|BroBalt|bttvNice|ButterSauce|cabbag3|CandianRage|CHAccepted|CiGrip|ConcernDoge|D:|DatSauce|FapFapFap|FishMoley|ForeverAlone|FuckYea|GabeN|HailHelix|HerbPerve|Hhhehehe|HHydro|iAMbh|iamsocal|iDog|JessSaiyan|JuliAwesome|KaRappa|KKona|LLuda|M&Mjc|ManlyScreams|NaM|OhGod|OhGodchanZ|OhhhKee|OhMyGoodness|PancakeMix|PedoBear|PedoNam|PokerFace|PoleDoge|RageFace|RebeccaBlack|RollIt!|rStrike|SexPanda|She'llBeRight|ShoopDaWhoop|SourPls|SuchFraud|SwedSwag|TaxiBro|tEh|ToasTy|TopHam|TwaT|UrnCrown|VisLaud|WatChuSay|WhatAYolk|YetiZ|PraiseIt|\\s){"
							+ Database.getOption(channel,
									Options.numEmotes) + ",}")) {
				new Timeouts(channel, sender, 1, TType.EMOTE);
			}
			ResultSet rs = Database.executeQuery("SELECT * FROM "
					+ Database.DATABASE + "." + channel.substring(1)
					+ "Spam");
			try {
				while (rs.next()) {
					if (message.matches("(" + rs.getString(1) + ")+")) {
						new Timeouts(channel, sender, 1, TType.SPAM);
					}
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "An error occurred checking if "
						+ sender + "'s message has bad words", e);
			}
		}

	}

	/*public void voteKickCount(String channel) {

		if ((voteKickCount.size() / getUsers(channel).length) >= .55) {

			sendMessage(channel, "The community has chosen to kick "
					+ toKick + ".");

		} else {

			sendMessage(channel, "The community has chosen to spare "
					+ toKick + ".");

		}

	}

	public void voteKick(String channel, String toKick) {
		this.toKick = toKick;
		voteKickCount = new ArrayList<>();
		sendMessage(channel, "The channel has begun a vote to kick "
				+ toKick + ". Type !voteKick to place your vote. To vote no"
				+ ", just do not vote.");
		new Timer(channel, 180, "kick");

	}

	public void addToVoteKickCount(String channel, String sender) {

		if (voteKickCount.contains(sender)) {

			sendMessage(
					channel,
					sender
							+ " tried to kick "
							+ toKick
							+ " twice. Do they have a personal vendetta? That is for me to know.");

		} else {
			if (confirmationReplies.get(channel)) {
				sendMessage(channel, sender
						+ " has has voted to kick " + toKick + ".");
			}
			voteKickCount.add(sender);

		}

	}*/
	
	public void setWelcomeEnabled(String channel, boolean value) {
		welcomeEnabled.put(channel, value);
	}
	
	public void setConfirmationEnabled(String channel, boolean value) {
		confirmationReplies.put(channel, value);
	}

	public String getChatPostSeen(String target) {
		return chatPostSeen.get(target);
	}
	
	public void addPoll(String channel, me.jewsofhazzard.pcmrbot.util.Poll poll) {
		polls.put(channel, poll);
	}

	public void removePoll(String channel) {
		polls.remove(channel);
	}

	public boolean hasPoll(String channel) {
		return polls.containsKey(channel);
	}

	public me.jewsofhazzard.pcmrbot.util.Poll getPoll(String channel) {
		return polls.get(channel);
	}

	public boolean getConfirmationReplies(String channel) {
		return confirmationReplies.get(channel);
	}

	public void addRaffle(String channel, me.jewsofhazzard.pcmrbot.util.Raffle raffle) {
		raffles.put(channel, raffle);
	}

	public void removeRaffle(String channel) {
		raffles.remove(channel);
	}

	public me.jewsofhazzard.pcmrbot.util.Raffle getRaffle(String string) {
		return raffles.get(string);
	}
	
}