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

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.cmd.CommandManager;
import me.jewsofhazard.pcmrbot.cmd.impl.AddModerator;
import me.jewsofhazard.pcmrbot.db.Database;
import me.jewsofhazard.pcmrbot.util.Options;
import me.jewsofhazard.pcmrbot.util.TType;
import me.jewsofhazard.pcmrbot.util.Timeouts;

import org.jibble.pircbot.PircBot;

/**
 *
 * @author JewsOfHazard
 * @author Donald10101
 * @author Angablade
 */
public class IRCBot extends PircBot {

	private static HashMap<String, String> chatPostSeen;
	private static HashMap<String, Boolean> welcomeEnabled;
	private static HashMap<String, Boolean> confirmationReplies;
	private static HashMap<String, Boolean> slowMode;
	private static HashMap<String, Boolean> subMode;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.Poll> polls;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.Raffle> raffles;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.VoteTimeOut> voteTimeOuts;
	private static final Logger logger = Logger.getLogger(IRCBot.class + "");

	private CommandManager cm = new CommandManager();

	/**
	 * Creates a new instance of IRCBot for the specified channel
	 * 
	 * @param channel
	 *            - The IRC Channel we are connecting to.
	 */
	public IRCBot() {
		setName(Main.getBotChannel().substring(1));
		welcomeEnabled = new HashMap<>();
		confirmationReplies = new HashMap<>();
		chatPostSeen = new HashMap<>();
		slowMode = new HashMap<>();
		subMode = new HashMap<>();
		polls = new HashMap<>();
		raffles = new HashMap<>();
		try {
			cm.loadCommands(Paths.get("data/cmd/"));
		} catch (IOException ex) {
			/* TODO */
		}
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
					"An error was thrown while executing onOp() in " + channel,
					e);
		}
	}

	@Override
	public void onJoin(String channel, String sender, String login,
			String hostname) {

		try {
			if (welcomeEnabled.get(channel)) {
				if (!sender.equalsIgnoreCase(Main.getBotChannel().substring(1))) {
					String msg = Database.getOption(channel.substring(1),
							Options.welcomeMessage).replace("%user%", sender);
					if (!msg.equalsIgnoreCase("none")) {
						sendMessage(channel, msg);
					}
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

			String params = "";
			try {
				params = message.substring(message.indexOf(' ') + 1);
			} catch (StringIndexOutOfBoundsException e) {

			}

			String command = message.substring(1, message.length());
			try {
				command = message.substring(1, message.indexOf(' '));
			} catch (StringIndexOutOfBoundsException e) {

			}

			Date date = new Date();
			chatPostSeen.put(sender,
					channel.substring(1) + "|" + date.toString());

			String reply = cm.parse(command.toLowerCase(), sender, channel,
					params);
			if (reply != null) {
				sendMessage(channel, reply);
			}
			if (!sender.equalsIgnoreCase(Main.getBotChannel().substring(1))) {
				autoReplyCheck(channel, message);
			}
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
			if (s.equalsIgnoreCase(channel)) {
				return true;
			}
		}
		return false;
	}

	public void autoReplyCheck(String channel, String message) {

		message = message.toLowerCase();
		ResultSet rs = Database.getAutoReplies(channel.substring(1));
		try {
			while (rs.next()) {
				String[] keyword = rs.getString(1).split(",");
				boolean matches = true;
				for (int i = 0; i < keyword.length; i++) {
					if (!message.contains(keyword[i].toLowerCase())) {
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
					+ Database.getOption(channel.substring(1),
							Options.numSymbols) + ",}")) {
				new Timeouts(channel, sender, 1, TType.SYMBOLS);
			} else if (message.length() >= Integer.valueOf(Database.getOption(
					channel.substring(1), Options.paragraphLength))) {
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

	public void addRaffle(String channel,
			me.jewsofhazard.pcmrbot.util.Raffle raffle) {
		raffles.put(channel, raffle);
	}

	public void removeRaffle(String channel) {
		raffles.remove(channel);
	}

	public me.jewsofhazard.pcmrbot.util.Raffle getRaffle(String string) {
		return raffles.get(string);
	}

	public void setSlowMode(String chanel, boolean slowMode) {
		IRCBot.slowMode.put(chanel, slowMode);
	}

	public boolean getSlowMode(String channel) {
		return slowMode.get(channel);
	}

	public void setSubscribersMode(String chanel, boolean subMode) {
		IRCBot.slowMode.put(chanel, subMode);
	}

	public boolean getSubscribersMode(String channel) {
		return subMode.get(channel);
	}

	public void removeWelcomeEnabled(String channel) {
		welcomeEnabled.remove(channel);
	}

	public void removeConfirmationReplies(String channel) {
		confirmationReplies.remove(channel);
	}

	public void removeSlowMode(String channel) {
		slowMode.remove(channel);
	}

	public void removeSubMode(String channel) {
		subMode.remove(channel);
	}

	public void addVoteTimeOut(String channel,
			me.jewsofhazard.pcmrbot.util.VoteTimeOut voteTimeOut) {
		voteTimeOuts.put(channel, voteTimeOut);
	}

	public me.jewsofhazard.pcmrbot.util.VoteTimeOut getVoteTimeOut(
			String channel) {
		return voteTimeOuts.get(channel);
	}

}