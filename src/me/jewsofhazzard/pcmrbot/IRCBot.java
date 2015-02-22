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

import me.jewsofhazzard.pcmrbot.Commands.*;
import me.jewsofhazzard.pcmrbot.database.Database;
import me.jewsofhazzard.pcmrbot.twitch.TwitchUtilities;
import me.jewsofhazzard.pcmrbot.util.Options;
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
	private boolean subscribersOn = false;
	private String toKick;
	private boolean voteKickActive;
	private boolean voteCall;
	private boolean raffleActive;
	// Let it be known, Mr_chris is the
	// first user to ever win the raffle.
	private ArrayList<String> inRaffle;
	private String raffleType;
	private ArrayList<ArrayList<String>> voting = new ArrayList<>();
	private ArrayList<String> voteKickCount;
	private static HashMap<String, String> chatPostSeen = new HashMap<>();
	private int optionCount;
	private boolean welcomeEnabled = true;
	// ringazin, may he forever be known as the one who initially
	// tried to vote for an option out of the bounds of the choices
	private ArrayList<String> ringazinUsers = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(IRCBot.class + "");

	/**
	 * Creates a new instance of IRCBot for the specified channel
	 * 
	 * @param channel
	 *            - The IRC Channel we are connecting to.
	 */
	public IRCBot() {
		this.setName(MyBotMain.getBotChannel().substring(1));
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		try {
			try {
				new AddModerator(recipient, channel);
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
			try {
				if (welcomeEnabled) {
					if (!sender.equalsIgnoreCase("pcmrbot")) {
						sendMessage(
								channel,
								Database.getOption(channel,
										Options.welcomeMessage).replace(
										"%user%", sender));
					} else {

						sendMessage(
								channel,
								"I have joined the channel and will stay with you unless you tell me to !leave or my creators do not"
										+ " shut me down properly because they are cruel people with devious minds.");

					}
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"An error occurred while executing onJoin()", e);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onJoin() in "
							+ channel, e);
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
				sendMessage(channel, new LMGTFY(message.substring(message.indexOf(' '))).getReply());
			} else if(message.startsWith("!slow ") && isMod(channel, sender)){
				
				sendMessage(channel, new Slow(isMod(channel, "pcmrbot")+"", message.substring(message.indexOf(" ") + 1)).getReply());
				
			} else if (message.equalsIgnoreCase("!shutdown")) {
				if (channel.equalsIgnoreCase("#pcmrbot")
						&& (isMod(channel, sender) || sender
								.equalsIgnoreCase("j3wsofhazard"))) {
					new Shutdown().execute();
				}
			} else if (message.startsWith("!title ")
					&& isMod(channel, sender)) {
				sendMessage(channel, new Title(channel, message.substring(message.indexOf(' '))).getReply());
			} else if (message.startsWith("!game ")
					&& isMod(channel, sender)) {
				sendMessage(channel, new Game(channel, message.substring(message.indexOf(' '))).getReply());
			} else if (message.equalsIgnoreCase("!clearAutoReplies") && sender.equalsIgnoreCase(channel.substring(1))) {
					sendMessage(channel, new ClearAutoReplies(channel, sender).getReply());
			} else if (message.equalsIgnoreCase("!help")) {
				sendMessage(channel, new Help().getReply());
			} else if (message.startsWith("!help ")) {
				
			} else if (message.startsWith("!broadcast ") && isMod(channel, sender) && channel.equalsIgnoreCase("#pcmrbot")){
				
				sendMessage("#pcmrbot", "I have sent " + message.substring(message.indexOf(" ") + 1) + " to all channels.");
				broadcast(message.substring(message.indexOf(" ") + 1));
			
			} else if (message.equalsIgnoreCase("!commercial") && sender.equalsIgnoreCase(channel.substring(1))) {


				TwitchUtilities.runCommercial(channel);

			} else if (message.equalsIgnoreCase("!commercial ")
					&& sender.equalsIgnoreCase(channel.substring(1))) {
				int time = 0;
				try {
					time = Integer.valueOf(message.substring(message
							.indexOf(' ')));
				} catch (NumberFormatException e) {
					sendMessage(
							channel,
							message.substring(message.indexOf(' '))
									+ " is not a valid time. Running a default length commercial!");
					TwitchUtilities.runCommercial(channel);
					return;
				}
				if (time <= 180 && time % 30 == 0) {
					TwitchUtilities.runCommercial(channel, time);
				} else {
					sendMessage(
							channel,
							message.substring(message.indexOf(' '))
									+ " is not a valid time. Running a default length commercial!");
					TwitchUtilities.runCommercial(channel);
					return;
				}

			} else if (message.equalsIgnoreCase("!teamspeak")) {

				sendMessage(channel,
						"You can download teamspeak here:  http://www.teamspeak.com/?page=downloads");

			} else if (message.startsWith("!me ")
					&& isMod(channel, sender)) {

				sendMessage(channel,
						"/me " + message.substring(message.indexOf(" ") + 1));

			} else if (message.equalsIgnoreCase("!servers")) {

				sendMessage(channel,
						"http://www.reddit.com/r/pcmasterrace/wiki/servers");

			} else if (message.equalsIgnoreCase("!gabe") && isMod(channel, sender)) {

				sendMessage(
						channel,
						"You can find all of the official pcmasterrace links at http://www.reddit.com/r/pcmasterrace/comments/2verur/check_out_our_official_communities_on_steam/");

			} else if (message.equalsIgnoreCase("!steamsales") && isMod(channel, sender)) {

				sendMessage(channel,
						"You can find the recent steam sales by heading to https://steamdb.info/sales/");

			} else if (message.startsWith("!fight ")) {

				sendMessage(channel, sender
						+ " puts up his digs in preparation to punch "
						+ message.substring(message.indexOf(" ") + 1));

			} else if (message.startsWith("!punch ")) {

				sendMessage(
						channel,
						sender + " punches "
								+ message.substring(message.indexOf(" ") + 1));

			} else if (message.startsWith("!ko ")) {

				sendMessage(
						channel,
						sender + " knocks out "
								+ message.substring(message.indexOf(" ") + 1));

			} else if (message.startsWith("!fatality ")) {

				sendMessage(channel,
						"It turns out that " + sender + " has killed "
								+ message.substring(message.indexOf(" ") + 1)
								+ "... Run, RUN!");

			} else if (message.equalsIgnoreCase("!subscribers")
					&& sender.equals(channel.substring(1))) {

				if (!subscribersOn) {

					sendMessage(channel, "/subscribers");

				} else if (subscribersOn) {

					sendMessage(channel, "/subscribersoff");

				}

			} else if (message.equalsIgnoreCase("!slowclap")) {

				sendMessage(
						channel,
						sender
								+ " claps his hands slowly while walking off into the sunset.");

			} else if (message.equalsIgnoreCase("!clear") && isMod(channel, sender)) {

				sendMessage(channel, "/clear");

			}  else if (message.equalsIgnoreCase("!disableWelcome")
					&& isMod(channel, sender)) {

				this.welcomeEnabled = false;
				sendMessage(channel,
						"Welcome messages have been disabled.");

			} else if (message.equalsIgnoreCase("!enableWelcome")
					&& isMod(channel, sender)) {

				this.welcomeEnabled = true;
				sendMessage(channel,
						"Welcome messages have been enabled.");

			} else if (message.startsWith("!changewelcome ")
					&& isMod(channel, sender)) {

				Database.setOption(channel, Options.welcomeMessage,
						message.substring((message.indexOf(" ") + 1)));
				sendMessage(
						channel,
						"The format has been changed to: "
								+ message.substring((message.indexOf(" ") + 1)));

			} else if (message.equalsIgnoreCase("!disablereplies")
					&& isMod(channel, sender)) {

				this.confirmationReplys = false;
				sendMessage(channel, sender
						+ " has disabled bot replies");

			} else if (message.equalsIgnoreCase("!enablereplies")
					&& isMod(channel, sender)) {

				this.confirmationReplys = true;
				sendMessage(channel, sender
						+ " has enabled bot replies");

			} else if (message.startsWith("!changeoption ")
					&& isMod(channel, sender)) {
				new ChangeOption(channel, message.substring(message.indexOf(" ") + 1));

			} else if (message.startsWith("!votestart ")
					&& isMod(channel, sender)) {

				voting = new ArrayList<>();
				ringazinUsers = new ArrayList<>();
				optionCount = 0;

				message = message.substring(message.indexOf(" ") + 1);
				String[] voteOptions = message.split("[|]");
				String[] answers = new String[voteOptions.length - 2];
				if (answers.length < 2) {
					sendMessage(channel,
							"You must provide at least two answers!");
					return;
				}
				for (int i = 2; i < voteOptions.length; i++) {
					answers[i - 2] = voteOptions[i];
					optionCount++;
				}
				sendMessage(channel, voteOptions[1]);

				for (int i = 0; i < answers.length; i++) {

					sendMessage(channel, (i + 1) + ": " + answers[i]);
					voting.add(new ArrayList<String>());
					voting.get(i).add(answers[i]);

				}

				sendMessage(
						channel,
						"Please input your choice by typing !vote {vote number}. Note, if you choose a number higher or lower than required, your vote will be discarded and you will be prohibited from voting this round.");

				for (int i = 0; i < answers.length; i++) {

					voting.add(new ArrayList<String>());
					voting.get(i).add(voteOptions[i]);

				}
				setVoteCall(true);
				try {
					vote(channel, (long) Integer.valueOf(voteOptions[0]));
				} catch (NumberFormatException e) {

				}
			} else if (message.startsWith("!shorten ")
					&& isMod(channel, sender)) {
				String out = shortenURL(channel, message
						.substring(message.indexOf(" ") + 1));
				if (out == null) {
					sendMessage(
							channel,
							message.substring(message.indexOf(' ') + 1)
									+ " is an invalid url! Make sure you include http(s)://.");
				}
				sendMessage(channel, "URL: " + out);

			} else if (message.startsWith("!slap ")) {
				String target = message.substring(message.indexOf(" ") + 1);
				sendAction(channel, "slaps " + target
						+ " with a raw fish");

			} else if (message.startsWith("!seen ")) {
				String target = message.substring(message.indexOf(" ") + 1);
				if (chatPostSeen.containsKey(target)) {
					// they have a recent message in the chatPostSeen map
					// the info of the message (channel & date)
					String info = chatPostSeen.get(target);

					String[] tokens = info.split("[|]");

					sendMessage(channel, sender + ", I last saw "
							+ target + " in " + tokens[0] + " on " + tokens[1]
							+ ".");
				} else {
					// they haven't chatted (They are not in the map)
					sendMessage(channel, "I'm sorry " + sender
							+ ", I haven't seen " + target + ".");
				}
			} else if (message.startsWith("!votekick ")
					&& !voteKickActive && isMod(channel, sender)) {

				message = message.substring(message.indexOf(" ") + 1);
				voteKick(channel, message);

			} else if (message.equalsIgnoreCase("!votekick") && voteKickActive) {

				addToVoteKickCount(channel, sender);

			} else if (message.startsWith("!vote ") && voteCall) {

				boolean canVote = true;

				for (int i = 0; i < voting.size(); i++) {

					if (voting.get(i).contains(sender)) {

						sendMessage(channel, "I am sorry " + sender
								+ " you cannot vote more than once.");
						canVote = false;
					}

				}

				if (ringazinUsers.contains(sender)) {

					sendMessage(channel, "I am sorry " + sender
							+ " you cannot vote more than once.");
					canVote = false;
				}

				if (canVote) {
					int vote = Integer.valueOf(message.substring(message
							.indexOf(" ") + 1));
					if (message.substring(message.indexOf(" ") + 1)
							.equalsIgnoreCase("random")) {
						vote = new Random().nextInt(optionCount);
					}

					if (vote < optionCount + 1) {
						voting.get(vote - 1).add(sender);
					} else {
						sendMessage(
								channel,
								sender
										+ " tried to break me, may hell forever reign upon him! (You cannot participate in this vote.)");
						ringazinUsers.add(sender);
						return;
					}
					if (confirmationReplys) {
						sendMessage(
								channel,
								sender + " has voted for "
										+ voting.get(vote - 1).get(0));
					}

				}

			} else if (message.startsWith("!enter")
					&& raffleActive) {

				joinRaffle(channel, sender, raffleType);

			} else if (message.startsWith("!addmod ")
					&& sender.equalsIgnoreCase(channel.substring(1))) {


				sendMessage(channel, new AddModerator(message = message.substring(message.indexOf(" ") + 1), channel).getReply());

			} else if (message.equalsIgnoreCase("!join")) {

				sendMessage(channel, new JoinMe(sender, channel.equalsIgnoreCase(MyBotMain.getBotChannel())+"").getReply());

			} else if (message.equalsIgnoreCase("!leave") && sender.equalsIgnoreCase(channel.substring(1))) {

				sendMessage(channel, new LeaveMe(channel).getReply());

			} else if (message.equalsIgnoreCase("!pcmrbot")) {
				
				sendMessage(channel,"I was made by J3wsOfHazard, Donald10101, and Angablade. Source at: http://github.com/jwolff52/PCMRBot");
			
			} else if (message.toLowerCase().startsWith("!addautoreply ")&& isMod(channel, sender)) {

				sendMessage(channel, new AddAutoReply(message, channel).getReply());
				
				//addAutoReply(message);

			} else if (message.startsWith("!raffle ")
					&& isMod(channel, sender)) {

				message = message.substring(message.indexOf(" ") + 1);
				sendMessage(channel, "The raffle has begun for "
						+ message);
				raffle(channel, message);

			}
			autoReplyCheck(channel, message);
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"An error was thrown while executing onMessage() in "
							+ channel, e);
		}
	}

	/**
	 * Sends a message to the channel telling them how long they have to vote
	 * and sets up the timer
	 * 
	 * @param time
	 *            - The amount of time (in seconds) the vote will last.
	 */
	public void vote(String channel, long time) {

		sendMessage(channel, "You have " + time + " seconds to vote.");

		new Timer(channel, time, "vote");

	}

	/**
	 * Tallies and decides what option wins the vote, then reports it to the
	 * channel.
	 */
	public void voteCounter(String channel) {

		int chosen = 0;

		for (int i = 0; i < voting.size(); i++) {

			if (voting.get(i).size() > voting.get(chosen).size())

				chosen = i;

		}

		sendMessage(channel,
				"The community chose " + voting.get(chosen).get(0));

	}

	public boolean isMod(String channel, String sender) {

		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DATABASE + "." + channel.substring(1)
				+ "Mods WHERE userID=\'" + sender + "\'");
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred checking if " + sender
					+ " is a mod of " + channel, e);
			return false;
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

	public static String shortenURL(String channel, String link) {
		BitlyClient client = new BitlyClient(
				"596d69348e5db5711a9f698ed606f4500fe0e766");
		Response<ShortenResponse> repShort = client.shorten().setLongUrl(link)
				.call();
		System.out.println(repShort.toString());

		if (repShort.status_txt.equalsIgnoreCase("ok")) {
			return new JsonParser().parse(repShort.data.toString())
					.getAsJsonObject().getAsJsonPrimitive("url").getAsString();
		}
		return null;
	}

	public void raffle(String channel, String type) {
		raffleActive = true;
		this.raffleType = type;
		inRaffle = new ArrayList<>();
		new Timer(channel, 300, "raffle");

	}

	public void raffleCount(String channel) {

		sendMessage(channel, "There raffle has closed. There are "
				+ inRaffle.size() + " users in the raffle.");
		int selection = new Random().nextInt(inRaffle.size());
		sendMessage(channel,
				"The raffle winner is " + inRaffle.get(selection) + ".");

	}

	public void joinRaffle(String channel, String sender, String raffleType) {
		boolean inRaffleAlready = false;

		if (inRaffle.contains(sender)) {

			inRaffleAlready = true;
			sendMessage(
					channel,
					sender
							+ " is a dirty cheater and tried to join the raffle more than once, may he be smiten.");

		}

		if ((raffleType.equalsIgnoreCase("follower") || raffleType
				.equalsIgnoreCase("followers"))
				&& (isFollower(channel, sender) || sender
						.equalsIgnoreCase(channel.substring(1)))
				&& !inRaffleAlready) {

			inRaffle.add(sender);
			if (confirmationReplys) {
				sendMessage(channel, sender
						+ " has joined the raffle.");
			}
		} else if ((raffleType.equalsIgnoreCase("subscriber") || raffleType
				.equalsIgnoreCase("subscribers"))
				&& (isSubscriber(channel, sender) || sender
						.equalsIgnoreCase(channel.substring(1)))
				&& !inRaffleAlready) {

			inRaffle.add(sender);
			if (confirmationReplys) {
				sendMessage(channel, sender
						+ " has joined the raffle.");
			}
		} else if (raffleType.equals("all") && !inRaffleAlready) {

			inRaffle.add(sender);
			if (confirmationReplys) {
				sendMessage(channel, sender
						+ " has joined the raffle.");
			}
		} else {
			sendMessage(channel, "I am sorry " + sender
					+ " you are not allowed to join this raffle.");
		}

	}

	public void broadcast(String message){
		
		for (String s : getChannels()) {

			if (!s.equalsIgnoreCase("#pcmrbot")) {
				
				sendMessage(s,	message);
				
			}

		}
		
	}

	/**
	 * Checks to see if sender is a follower of the channel we are currently
	 * connected to.
	 * 
	 * @param sender
	 * @return true if sender is a following of channel
	 */
	public boolean isFollower(String channel, String sender) {
		return TwitchUtilities.isFollower(sender, channel);
	}

	/**
	 * Checks to see if sender is a subscriber of the channel we are currently
	 * connected to. (WIP)
	 * 
	 * @param sender
	 * @return true if sender is subscribed to channel
	 */
	public boolean isSubscriber(String channel, String sender) {
		return TwitchUtilities.isSubscriber(sender, channel);
	}

	/**
	 * Sets the boolean voteCall to the value of set.
	 * 
	 * @param set
	 */
	public void setVoteCall(boolean set) {

		this.voteCall = set;

	}

	public void setRaffle(boolean set) {

		this.raffleActive = set;

	}

	public void voteKick(String channel, String toKick) {
		this.toKick = toKick;
		voteKickCount = new ArrayList<>();
		sendMessage(channel, "The channel has begun a vote to kick "
				+ toKick + ". Type !voteKick to place your vote. To vote no"
				+ ", just do not vote.");
		setVoteKickActive(true);
		new Timer(channel, 180, "kick");

	}

	public void setVoteKickActive(boolean set) {

		this.voteKickActive = set;

	}
	
	public boolean isWatchingChannel(String channel) {
		for (String s : getChannels()) {
			if(s.equalsIgnoreCase(channel)) {
				return true;
			}
		}
		return false;
	}

	public void voteKickCount(String channel) {

		if ((voteKickCount.size() / getUsers(channel).length) >= .55) {

			sendMessage(channel, "The community has chosen to kick "
					+ toKick + ".");

		} else {

			sendMessage(channel, "The community has chosen to spare "
					+ toKick + ".");

		}

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
			if (confirmationReplys) {
				sendMessage(channel, sender
						+ " has has voted to kick " + toKick + ".");
			}
			voteKickCount.add(sender);

		}

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

		if (!isMod(channel, sender)) {
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
	
}