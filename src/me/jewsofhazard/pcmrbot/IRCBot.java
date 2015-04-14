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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.commands.AddModerator;
import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.customcommands.CustomCommandParser;
import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.DelayedPermitTask;
import me.jewsofhazard.pcmrbot.util.DelayedReJoin;
import me.jewsofhazard.pcmrbot.util.DelayedWelcomeTask;
import me.jewsofhazard.pcmrbot.util.PointsRunnable;
import me.jewsofhazard.pcmrbot.util.TOptions;
import me.jewsofhazard.pcmrbot.util.TType;
import me.jewsofhazard.pcmrbot.util.Timeouts;
import me.jewsofhazard.pcmrbot.util.URLInString;
import org.jibble.pircbot.PircBot;

/**
 *
 * @author JewsOfHazard, Donald10101, And Angablade.
 */

public class IRCBot extends PircBot {

	private static HashMap<String, String> chatPostSeen;
	private static HashMap<String, Boolean> welcomeEnabled;
	private static HashMap<String, Boolean> confirmationReplies;
	private static HashMap<String, Boolean> slowMode;
	private static HashMap<String, Boolean> subMode;
	private static HashMap<String, Boolean> isReJoin;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.Poll> polls;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.Raffle> raffles;
	private static HashMap<String, me.jewsofhazard.pcmrbot.util.VoteTimeOut> voteTimeOuts;
	private static HashMap<String, ArrayList<DelayedPermitTask>> permits;
	private static HashMap<String, ArrayList<String>> welcomes;
	private static final Logger logger = Logger.getLogger(IRCBot.class + "");
        //private static String [] schemes = {"http","https","ftp","ssh","www","sftp"};


	/**
	 * Creates a new instance of IRCBot for the specified channel
	 */
	public IRCBot() {
		this.setName(Main.getBotChannel().substring(1));
		initVariables();
	}

	/**
	 * initializes all of our HashMaps
	 */
	private void initVariables() {
		welcomeEnabled = new HashMap<>();
		confirmationReplies = new HashMap<>();
		chatPostSeen = new HashMap<>();
		slowMode = new HashMap<>();
		subMode = new HashMap<>();
		isReJoin = new HashMap<>();
		polls = new HashMap<>();
		raffles = new HashMap<>();
		permits = new HashMap<>();
		welcomes = new HashMap<>();
	}

	/**
	 * Ensures that a channel moderator is added to the bot's moderator list
	 */
	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		try {
			new AddModerator().execute(Main.getBotChannel().substring(1),
					channel, new String[] { recipient });
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onOp() in " + channel,
					e);
		}
	}

	/**
	 * Decides how to welcome a user and then sends that message to the the
	 * channel.
	 * 
	 * Also starts points accumulation for that user in the channel
	 */
	@Override
	public void onJoin(String channel, String sender, String login,
			String hostname) {
		try {
			if (sender.equalsIgnoreCase(Main.getBotChannel().substring(1))) {
				sendMessage(
						channel,
						"I have joined the channel and will stay with you unless you tell me to !leave or my creators do not shut me down properly because they are cruel people with devious minds.");
				return;
			}
			if (welcomeEnabled.get(channel) && !isReJoin.containsKey(channel)) {
				String msg = Database.getWelcomeMessage(channel.substring(1))
						.replace("%user%", sender).replace("%chan%", channel).replace("%quote%", "\"");
				if (!msg.equalsIgnoreCase("none")
						&& !recentlyWelcomed(sender, channel)) {
					sendMessage(channel, msg);
					addWelcome(channel, sender);
				}
			}
			new PointsRunnable(sender, channel.substring(1));
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"An error occurred while executing onJoin()", e);
		}
	}

	/**
	 * Sends a message to the bot's channel when someone leaves a channel the
	 * bot is in
	 * 
	 * Stops the point accumulation for that user in the channel specified
	 */
	@Override
	public void onPart(String channel, String sender, String login,
			String hostname) {
		try {
			sendMessage(Main.getBotChannel(),
					String.format("%s has left %s's channel.", sender, channel));
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"An error occurred while executing onLeave()", e);
		}
		PointsRunnable.removeChannelFromUser(sender, channel.substring(1));
	}

	/**
	 * Handles spam checking, last seen, commands, auto replies, and custom
	 * commands
	 */
	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		try {
			checkSpam(channel, message, sender);
			if (message.charAt(0) == '!') {
				String[] params = message.substring(message.indexOf(' ') + 1)
						.split(" ");
				String command;
				try {
					command = message.substring(1, message.indexOf(' '));
				} catch (StringIndexOutOfBoundsException e) {
					command = message.substring(1, message.length());
				}
				if (command.equalsIgnoreCase(params[0].substring(1))) {
					params = new String[0];
				}
				String reply = CommandParser.parse(command, sender, channel,
						params);
				if (reply != null) {
					sendMessage(channel, reply);
				}
				reply = CustomCommandParser.parse(command.toLowerCase(),
						sender, channel, params);
				if (reply != null) {
					sendMessage(channel, reply);
				}
			}
			chatPostSeen.put(sender,
					channel.substring(1) + "|" + new Date().toString());
			if (!sender.equalsIgnoreCase(Main.getBotChannel())) {
				autoReplyCheck(channel, message, sender);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onMessage() in "
							+ channel, e);
		}
	}

	/**
	 * Sends message when the bot join's a channel for the first time.
	 */
	public void onFirstJoin(String channel) {
		sendMessage(
				channel,
				"Hello, this appears to be the first time you have invited me to join your channel. We just have a few preliminary manners to attend to. First off make sure to mod me so I don't get timed out, then type !setup");
	}

	/**
	 * @param channel
	 *            - channel we might be in
	 * @return true if we are in the channel specified
	 */
	public boolean isWatchingChannel(String channel) {
		for (String s : getChannels()) {
			if (s.equalsIgnoreCase(channel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param channel
	 *            - the channel the message came from
	 * @param message
	 *            - the message that might contain keywords
         * @param sender
         *            - the sender of the message only used for the Hi Daddy reply for me
	 */
	public void autoReplyCheck(String channel, String message, String sender) {

		message = message.toLowerCase();
		ResultSet rs = Database.getAutoReplies(channel.substring(1));
		try {
                    if(message.equals("hey guys!") && sender.equalsIgnoreCase("j3wsofhazard")){
                    sendMessage(channel, "Hi daddy!");
                    }   while (rs.next()) {
				String[] keyword = rs.getString(1).split(",");
				boolean matches = true;
                        for (String keyword1 : keyword) {
                            if (!message.toLowerCase().contains(keyword1.toLowerCase())) {
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

	/**
	 * @param channel
	 *            - channel the massage happened in
	 * @param message
	 *            - message that might contain spam
	 * @param sender
	 *            - the person the sent the message
	 */
	public void checkSpam(String channel, String message, String sender) {
		if (!Database.isMod(sender, channel.substring(1)) && !Database.isRegular(sender, channel.substring(1)) && !Database.isWhitelisted(sender, channel.substring(1))) {
                    
                    
			System.out.println("The system has checked if " + sender + " has posted spam in " + channel);
                        int caps = Database.getOption(channel.substring(1),
					TOptions.numCaps);
			int symbols = Database.getOption(channel.substring(1),
					TOptions.numSymbols);
			int link = Database.getOption(channel.substring(1), TOptions.link);
			int paragraph = Database.getOption(channel.substring(1),
					TOptions.paragraphLength);
			int emotes = Database.getOption(channel.substring(1),
					TOptions.numEmotes);
			
                        
                        if (URLInString.CheckForUrl(message) == true && link != -1) {
				if (!isPermitted(channel, sender)) {
					// System.out.println("The links are being timed out.");
					new Timeouts(channel, sender, 1, TType.LINK);
				} else {
					removePermit(channel, sender);
				}
			} if (caps != -1 && message.matches("[A-Z\\s]{" + caps + ",}")) {
				// System.out.println("The bot has deemed the caps in a message to be to much.");
				new Timeouts(channel, sender, 1, TType.CAPS);
			} else if (symbols != -1
					&& message.matches("[\\W_\\s]{" + symbols + ",}")) {
				// System.out.println("The bot has deemed the symbols in a message to be to much.");
				new Timeouts(channel, sender, 1, TType.SYMBOLS);
			} else if (paragraph != -1 && message.length() >= paragraph) {
				// System.out.println("The bot has deemed the letters in a paragraph to be to much.");
				new Timeouts(channel, sender, 1, TType.PARAGRAPH);
			} else if (emotes != -1
					&& message
							.matches("(:\\(|:\\)|:/|:D|:o|:p|:z|;\\)|;p|<3|>\\(|B\\)|o_o|R\\)|4Head|ANELE|ArsonNoSexy|AsianGlow|AtGL|AthenaPMS|AtIvy|BabyRage|AtWW|BatChest|BCWarrior|BibleThump|BigBrother|BionicBunion|BlargNaut|BloodTrail|BORT|BrainSlug|BrokeBack|BuddhaBar|CougarHunt|DAESuppy|DansGame|DatSheffy|DBstyle|DendiFace|DogFace|EagleEye|EleGiggle|EvilFetus|FailFish|FPSMarksman|FrankerZ|FreakinStinkin|FUNgineer|FunRun|FuzzyOtterOO|GasJoker|GingerPower|GrammarKing|HassaanChop|HassanChop|HeyGuys|HotPokket|HumbleLife|ItsBoshyTime|Jebaited|KZowl|JKanStyle|JonCarnage|KAPOW|Kappa|Keepo|KevinTurtle|Kippa|Kreygasm|KZassault|KZcover|KZguerilla|KZhelghast|KZskull|Mau5|mcaT|MechaSupes|MrDestructoid|MrDestructoid|MVGame|NightBat|NinjaTroll|NoNoSpot|noScope|NotAtk|OMGScoots|OneHand|OpieOP|OptimizePrime|panicBasket|PanicVis|PazPazowitz|PeoplesChamp|PermaSmug|PicoMause|PipeHype|PJHarley|PJSalt|PMSTwin|PogChamp|Poooound|PRChase|PunchTrees|PuppeyFace|RaccAttack|RalpherZ|RedCoat|ResidentSleeper|RitzMitz|RuleFive|Shazam|shazamicon|ShazBotstix|ShazBotstix|ShibeZ|SMOrc|SMSkull|SoBayed|SoonerLater|SriHead|SSSsss|StoneLightning|StrawBeary|SuperVinlin|SwiftRage|TF2John|TheRinger|TheTarFu|TheThing|ThunBeast|TinyFace|TooSpicy|TriHard|TTours|UleetBackup|UncleNox|UnSane|Volcania|WholeWheat|WinWaker|WTRuck|WutFace|YouWHY|\\(mooning\\)|\\(poolparty\\)|\\(puke\\)|:\\'\\(|:tf:|aPliS|BaconEffect|BasedGod|BroBalt|bttvNice|ButterSauce|cabbag3|CandianRage|CHAccepted|CiGrip|ConcernDoge|D:|DatSauce|FapFapFap|FishMoley|ForeverAlone|FuckYea|GabeN|HailHelix|HerbPerve|Hhhehehe|HHydro|iAMbh|iamsocal|iDog|JessSaiyan|JuliAwesome|KaRappa|KKona|LLuda|M&Mjc|ManlyScreams|NaM|OhGod|OhGodchanZ|OhhhKee|OhMyGoodness|PancakeMix|PedoBear|PedoNam|PokerFace|PoleDoge|RageFace|RebeccaBlack|RollIt!|rStrike|SexPanda|She'llBeRight|ShoopDaWhoop|SourPls|SuchFraud|SwedSwag|TaxiBro|tEh|ToasTy|TopHam|TwaT|UrnCrown|VisLaud|WatChuSay|WhatAYolk|YetiZ|PraiseIt|\\s){"
									+ emotes + ",}")) {
				// System.out.println("The bot has deemed the emotes in a message to be to much.");
				new Timeouts(channel, sender, 1, TType.EMOTE);
			}
			ResultSet rs = Database.getSpam(channel.substring(1));
			// System.out.println("Downloaded the spam check database.");
			try {
				while (rs.next()) {
					// System.out.println("Spam checked for possible checks.");
					if (message.matches("(" + rs.getString(1) + ")+")) {
						// System.out.println("Spam checked and found true");
						new Timeouts(channel, sender, 1, TType.SPAM);
					}
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "An error occurred checking if "
						+ sender + "'s message has bad words", e);
			}
		}
                
	}

	/**
	 * @param channel
	 *            - channel the link was sent in
	 * @param sender
	 *            - person who might be permitted to post a link
	 * @return true if sender is permitted in channel
	 */
	public boolean isPermitted(String channel, String sender) {
		ArrayList<DelayedPermitTask> ps = permits.get(sender);
		if (ps == null) {
			return false;
		}
		for (DelayedPermitTask p : ps) {
			if (p.getChannel().equalsIgnoreCase(channel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param channel
	 *            - channel that we are setting the value for
	 * @param value
	 *            - true if welcome should be enabled, false otherwise
	 */
	public void setWelcomeEnabled(String channel, boolean value) {
		welcomeEnabled.put(channel, value);
	}

	/**
	 * @param channel
	 *            - channel that we are setting the value for
	 * @param value
	 *            - true if confirmations should be on, false otherwise
	 */
	public void setConfirmationEnabled(String channel, boolean value) {
		confirmationReplies.put(channel, value);
	}

	/**
	 * @param target
	 *            - person who might have been seen
	 * @return the time and channel they were seen in, null otherwise
	 */
	public String getChatPostSeen(String target) {
		return chatPostSeen.get(target);
	}

	/**
	 * @param channel
	 *            - the channel the poll is in
	 * @param poll
	 *            - the Poll Object
	 */
	public void addPoll(String channel, me.jewsofhazard.pcmrbot.util.Poll poll) {
		polls.put(channel, poll);
	}

	/**
	 * @param channel
	 *            - the channel the poll is in
	 */
	public void removePoll(String channel) {
		polls.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel the Poll might be in
	 * @return true if the channel has a Poll, false otherwise
	 */
	public boolean hasPoll(String channel) {
		return polls.containsKey(channel);
	}

	/**
	 * @param channel
	 *            - the channel the Poll is in
	 * @return the Poll Object
	 */
	public me.jewsofhazard.pcmrbot.util.Poll getPoll(String channel) {
		return polls.get(channel);
	}

	/**
	 * @param channel
	 *            - the channel to get confirmation reply for
	 * @return true if the replies are enabled, false otherwise
	 */
	public boolean getConfirmationReplies(String channel) {
		return confirmationReplies.get(channel);
	}

	/**
	 * @param channel
	 *            - the channel to add the raffle to
	 * @param raffle
	 *            - the Raffle Object
	 */
	public void addRaffle(String channel,
			me.jewsofhazard.pcmrbot.util.Raffle raffle) {
		raffles.put(channel, raffle);
	}

	/**
	 * @param channel
	 *            - the channel to remove the raffle from
	 */
	public void removeRaffle(String channel) {
		raffles.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to get the Raffle for
	 * @return the Raffle Object
	 */
	public me.jewsofhazard.pcmrbot.util.Raffle getRaffle(String channel) {
		return raffles.get(channel);
	}

	/**
	 * @param chanel
	 *            - the channel to set slow mode for
	 * @param slowMode
	 *            - true if slow mode is enabled, false otherwise
	 */
	public void setSlowMode(String chanel, boolean slowMode) {
		IRCBot.slowMode.put(chanel, slowMode);
	}

	/**
	 * @param channel
	 *            - the channel to get slow mode for
	 * @return true if the channel is in slow mode, false otherwise
	 */
	public boolean getSlowMode(String channel) {
		return slowMode.get(channel);
	}

	/**
	 * @param channel
	 *            - the channel to set slow mode for
	 * @param value
	 *            - true if sub mode is enabled, false otherwise
	 */
	public void setSubMode(String channel, boolean value) {
		subMode.put(channel, value);
	}

	/**
	 * @param channel
	 *            - the channel to get ub mode for
	 * @return true if the channel is in sub mode, false otherwise
	 */
	public boolean getSubMode(String channel) {
		return subMode.get(channel);
	}

	/**
	 * @param channel
	 *            - the channel to remove from the welcome enabled list
	 */
	public void removeWelcomeEnabled(String channel) {
		welcomeEnabled.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to remove from the confirmation replies list
	 */
	public void removeConfirmationReplies(String channel) {
		confirmationReplies.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to remove from the slow mode list
	 */
	public void removeSlowMode(String channel) {
		slowMode.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to remove from the sub mode list
	 */
	public void removeSubMode(String channel) {
		subMode.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to add a vote time out for
	 * @param voteTimeOut
	 *            - VoteTimeOut Object
	 */
	public void addVoteTimeOut(String channel,
			me.jewsofhazard.pcmrbot.util.VoteTimeOut voteTimeOut) {
		voteTimeOuts.put(channel, voteTimeOut);
	}

	/**
	 * @param channel
	 *            - channel to get the VoteTimeOut Object for
	 * @return VoteTimeOut Object
	 */
	public me.jewsofhazard.pcmrbot.util.VoteTimeOut getVoteTimeOut(
			String channel) {
		return voteTimeOuts.get(channel);
	}

	/**
	 * @param permit
	 *            - Permit Object
	 * @param user
	 *            - user to permit
	 */
	public void addPermit(DelayedPermitTask permit, String user) {
		ArrayList<DelayedPermitTask> p = permits.get(user);
		if (p == null) {
			p = new ArrayList<>();
		}
		p.add(permit);
		permits.put(user, p);
	}

	/**
	 * @param permit
	 *            - Permit Object to remove
	 * @param user
	 *            - user to remove the permit for
	 */
	public void removePermit(DelayedPermitTask permit, String user) {
		ArrayList<DelayedPermitTask> p = permits.get(user);
		if (p == null) {
			return;
		}
		p.remove(permit);
		if (p.size() > 0) {
			permits.put(user, p);
		} else {
			permits.remove(user);
		}
	}

	/**
	 * @param channel
	 *            - the channel the permit might be in
	 * @param sender
	 *            - the person who might be permitted
	 */
	private void removePermit(String channel, String sender) {
		ArrayList<DelayedPermitTask> ps = permits.get(sender);
		if (ps == null) {
			return;
		}
		for (DelayedPermitTask p : ps) {
			if (p.getChannel().equalsIgnoreCase(channel)) {
				ps.remove(p);
				break;
			}
		}
		if (ps.size() > 0) {
			permits.put(sender, ps);
		} else {
			permits.remove(sender);
		}
	}

	/**
	 * @param channel
	 *            - the channel to set teJoin for
	 * @param reJoin
	 *            - true if this is a re join, false otherwise
	 */
	public void setReJoin(String channel, boolean reJoin) {
		if (reJoin) {
			isReJoin.put(channel, reJoin);
			new DelayedReJoin(channel);
		}
	}

	/**
	 * @param channel
	 *            - the channel that might contain re join
	 */
	public void removeReJoin(String channel) {
		isReJoin.remove(channel);
	}

	/**
	 * @param channel
	 *            - the channel to add a welcome to
	 * @param user
	 *            - the user to add to the welcomes list
	 */
	public void addWelcome(String channel, String user) {
		ArrayList<String> p = welcomes.get(user);
		if (p == null) {
			p = new ArrayList<>();
		}
		p.add(channel);
		welcomes.put(user, p);
		new DelayedWelcomeTask(channel, user);
	}

	/**
	 * @param channel
	 *            - the channel to remove the welcome from
	 * @param user
	 *            -the user to remove from the welcomes list
	 */
	public void removeWelcome(String channel, String user) {
		ArrayList<String> ws = welcomes.get(user);
		if (ws == null) {
			return;
		}
		for (String s : ws) {
			if (s.equalsIgnoreCase(channel)) {
				ws.remove(s);
				break;
			}
		}
		if (ws.size() > 0) {
			welcomes.put(user, ws);
		} else {
			welcomes.remove(user);
		}
	}

	/**
	 * @param sender
	 *            - person who joined
	 * @param channel
	 *            - channel they joined
	 * @return true if they have joined this channel in the last 30 minutes,
	 *         false otherwise
	 */
	private boolean recentlyWelcomed(String sender, String channel) {
		ArrayList<String> p = welcomes.get(sender);
		if (p == null) {
			return false;
		}
		return p.contains(channel);
	}
}
