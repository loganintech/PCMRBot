package me.jewsofhazard.pcmrbot.customcommands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

public class CustomCommandParser {
	private static final Logger logger = Logger.getLogger(CustomCommandParser.class+"");
	
	/**
	 * @param command - command without the leading !
	 * @param sender - person who sent the command
	 * @param channel - channel the command was sent in
	 * @param parameters - parameters passed with the command
	 * @return formatted if the command is valid, null otherwise
	 */
	public static String parse(String command, String sender, String channel, String[] parameters) {
		ResultSet rs = Database.getCustomCommands(channel.substring(1));
		try {
			while(rs.next()) {
				if(rs.getString(1).substring(1).equalsIgnoreCase(command)) {
					String reply = rs.getString(3);
					ArrayList<String> passed = new ArrayList<>();
					String[] params = rs.getString(2).split(" ");
					if(params.length == 1) {
						if(params[0].equalsIgnoreCase("")) {
							params = new String[0];
						}
					}
					int i=0;
					while(i < parameters.length) {
						if(parameters[i].startsWith("\"")) {
							String temp=parameters[i].replace("\"", "") + " ";
							if(parameters[i].endsWith("\"")) {
								passed.add(parameters[i].replace("\"", ""));
								continue;
							}
							i++;
							boolean endQuote = true;
							while(!parameters[i].endsWith("\"")) {
								temp+=parameters[i] + " ";
								i++;
								if(i >= parameters.length) {
									endQuote = false;
								}
							}
							if(endQuote) {
								temp+=parameters[i].replace("\"", "");
							}
							i++;
							passed.add(temp);
							continue;
						}
						passed.add(parameters[i]);
						i++;
					}
					if(passed.size() == params.length) {
						for(i = 0;i < passed.size();i++) {
							reply = reply.replace(params[i], passed.get(i));
						}
						return reply;
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an issue executing a custom command", e);
		}
		return null;
	}
}
