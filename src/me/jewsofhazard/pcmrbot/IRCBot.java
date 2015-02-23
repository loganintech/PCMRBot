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

package me.jewsofhazard.pcmrbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.commands.AddModerator;
import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.Options;
import me.jewsofhazard.pcmrbot.util.TType;
import me.jewsofhazard.pcmrbot.util.Timeouts;

import org.jibble.pircbot.PircBot;

//

/**
 *
 * @author JewsOfHazard, Donald10101, And Angablade.
 */

public class IRCBot extends PircBot {
	
	private static HashMap<String, String> chatPostSeen;
	private static HashMap<String,Boolean> welcomeEnabled;
	private static HashMap<String,Boolean> confirmationReplies;
	private static HashMap<String,me.jewsofhazard.pcmrbot.util.Poll> polls;
	private static HashMap<String,me.jewsofhazard.pcmrbot.util.Raffle> raffles;
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
							Database.getOption(channel.substring(1), Options.welcomeMessage).replace("%user%", sender));
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

			checkSpam(channel, message, sender);
			
			String params="";
			try {
				params = message.substring(message.indexOf(' ') + 1);
			} catch(StringIndexOutOfBoundsException e) {
				
			}
			
			String command=message.substring(1, message.length());
			try {
				command = message.substring(1, message.indexOf(' '));
			} catch(StringIndexOutOfBoundsException e) {
				
			}

			Date date = new Date();
			chatPostSeen.put(sender,
					channel.substring(1) + "|" + date.toString());

			String reply = CommandParser.parse(command.toLowerCase(), sender, channel, params);
			if(reply != null) {
				sendMessage(channel, reply);
			}
			if(!sender.equalsIgnoreCase(MyBotMain.getBotChannel())) {
				autoReplyCheck(channel, message);
			}
			
			
			
			
			
			
			/*else if (message.startsWith("!votekick ")
					&& !voteKickActive && Database.isMod(sender, channel.substring(1))) {

				message = message.substring(message.indexOf(" ") + 1);
				voteKick(channel, message);

			} else if (message.equalsIgnoreCase("!votekick") && voteKickActive) {

				addToVoteKickCount(channel, sender);

			} */
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
				"Hello, this appears to be the first time you have invited me to join your channel. We just have a few preliminary manners to attend to. First off make sure to mod me so I don't get timed out, then type !setup");
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

		message=message.toLowerCase();
		ResultSet rs = Database.getAutoReplies(channel.substring(1));
		try {
			while (rs.next()) {
				String[] keyword = rs.getString(1).split(",");
				for(String s:keyword) {
					logger.info(s);
				}
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
					+ Database.getOption(channel.substring(1), Options.numCaps)
					+ ",}")) {
				new Timeouts(channel, sender, 1, TType.CAPS);
			} else if (message
					.matches("([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})")
					|| message
							.matches("(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?")) {
				new Timeouts(channel, sender, 1, TType.LINK);
			} else if (message.matches("[\\W_\\s]{"
					+ Database.getOption(channel.substring(1), Options.numSymbols)
					+ ",}")) {
				new Timeouts(channel, sender, 1, TType.SYMBOLS);
			} else if (message.length() >= Integer.valueOf(Database.getOption(
					channel, Options.paragraphLength))) {
				new Timeouts(channel, sender, 1, TType.PARAGRAPH);
			} else if (message
					.matches("(:\\(|:\\)|:/|:D|:o|:p|:z|;\\)|;p|<3|>\\(|B\\)|o_o|R\\)|4Head|ANELE|ArsonNoSexy|AsianGlow|AtGL|AthenaPMS|AtIvy|BabyRage|AtWW|BatChest|BCWarrior|BibleThump|BigBrother|BionicBunion|BlargNaut|BloodTrail|BORT|BrainSlug|BrokeBack|BuddhaBar|CougarHunt|DAESuppy|DansGame|DatSheffy|DBstyle|DendiFace|DogFace|EagleEye|EleGiggle|EvilFetus|FailFish|FPSMarksman|FrankerZ|FreakinStinkin|FUNgineer|FunRun|FuzzyOtterOO|GasJoker|GingerPower|GrammarKing|HassaanChop|HassanChop|HeyGuys|HotPokket|HumbleLife|ItsBoshyTime|Jebaited|KZowl|JKanStyle|JonCarnage|KAPOW|Kappa|Keepo|KevinTurtle|Kippa|Kreygasm|KZassault|KZcover|KZguerilla|KZhelghast|KZskull|Mau5|mcaT|MechaSupes|MrDestructoid|MrDestructoid|MVGame|NightBat|NinjaTroll|NoNoSpot|noScope|NotAtk|OMGScoots|OneHand|OpieOP|OptimizePrime|panicBasket|PanicVis|PazPazowitz|PeoplesChamp|PermaSmug|PicoMause|PipeHype|PJHarley|PJSalt|PMSTwin|PogChamp|Poooound|PRChase|PunchTrees|PuppeyFace|RaccAttack|RalpherZ|RedCoat|ResidentSleeper|RitzMitz|RuleFive|Shazam|shazamicon|ShazBotstix|ShazBotstix|ShibeZ|SMOrc|SMSkull|SoBayed|SoonerLater|SriHead|SSSsss|StoneLightning|StrawBeary|SuperVinlin|SwiftRage|TF2John|TheRinger|TheTarFu|TheThing|ThunBeast|TinyFace|TooSpicy|TriHard|TTours|UleetBackup|UncleNox|UnSane|Volcania|WholeWheat|WinWaker|WTRuck|WutFace|YouWHY|\\(mooning\\)|\\(poolparty\\)|\\(puke\\)|:\\'\\(|:tf:|aPliS|BaconEffect|BasedGod|BroBalt|bttvNice|ButterSauce|cabbag3|CandianRage|CHAccepted|CiGrip|ConcernDoge|D:|DatSauce|FapFapFap|FishMoley|ForeverAlone|FuckYea|GabeN|HailHelix|HerbPerve|Hhhehehe|HHydro|iAMbh|iamsocal|iDog|JessSaiyan|JuliAwesome|KaRappa|KKona|LLuda|M&Mjc|ManlyScreams|NaM|OhGod|OhGodchanZ|OhhhKee|OhMyGoodness|PancakeMix|PedoBear|PedoNam|PokerFace|PoleDoge|RageFace|RebeccaBlack|RollIt!|rStrike|SexPanda|She'llBeRight|ShoopDaWhoop|SourPls|SuchFraud|SwedSwag|TaxiBro|tEh|ToasTy|TopHam|TwaT|UrnCrown|VisLaud|WatChuSay|WhatAYolk|YetiZ|PraiseIt|\\s){"
							+ Database.getOption(channel.substring(1),
									Options.numEmotes) + ",}")) {
				new Timeouts(channel, sender, 1, TType.EMOTE);
			}
			ResultSet rs = Database.getSpam(channel.substring(1));
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
	
	public void addPoll(String channel, me.jewsofhazard.pcmrbot.util.Poll poll) {
		polls.put(channel, poll);
	}

	public void removePoll(String channel) {
		polls.remove(channel);
	}

	public boolean hasPoll(String channel) {
		return polls.containsKey(channel);
	}

	public me.jewsofhazard.pcmrbot.util.Poll getPoll(String channel) {
		return polls.get(channel);
	}

	public boolean getConfirmationReplies(String channel) {
		return confirmationReplies.get(channel);
	}

	public void addRaffle(String channel, me.jewsofhazard.pcmrbot.util.Raffle raffle) {
		raffles.put(channel, raffle);
	}

	public void removeRaffle(String channel) {
		raffles.remove(channel);
	}

	public me.jewsofhazard.pcmrbot.util.Raffle getRaffle(String string) {
		return raffles.get(string);
	}
	
}