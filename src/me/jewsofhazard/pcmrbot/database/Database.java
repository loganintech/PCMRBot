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

package me.jewsofhazard.pcmrbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.TOptions;

public class Database {

	private static Connection conn;

	private static final String URL = "jdbc:mysql://localhost:3306/pcmrbot?";

	public static final String DATABASE = "pcmrbot";

	static final Logger logger = Logger.getLogger(Database.class + "");

	/**
	 * Creates a connection to the database.
	 * 
	 * @return - true if connection is successful
	 */
	public static boolean initDBConnection(String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			logger.log(
					Level.SEVERE,
					"Unable to find Driver in classpath!"
							,e);
		}
		try {
			conn = DriverManager.getConnection(String.format("%suser=bot&password=%s", URL, pass));
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Creates the tables for the provided channel
	 * 
	 * @param channelNoHash - the channel we are connecting to.
	 * @return - true if it has to create the tables
	 */
	public static boolean getChannelTables(String channelNoHash) {
		Statement stmt;
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;
		Statement stmt5;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeQuery(String.format("SELECT * FROM %s.%sMods", DATABASE, channelNoHash));
			return false;
		} catch (SQLException e) {
			try {
				stmt1 = conn.createStatement();
				stmt1.closeOnCompletion();
				stmt1.executeUpdate(String.format("CREATE TABLE %s.%sMods(userID varchar(25), PRIMARY KEY (userID))", DATABASE, channelNoHash));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, String.format("Unable to create table %sMods!",DATABASE), ex);
			}
			try {
				stmt2 = conn.createStatement();
				stmt2.closeOnCompletion();
				stmt2.executeUpdate(String.format("CREATE TABLE %s.%sOptions(optionID varchar(50), value varchar(4000), PRIMARY KEY (optionID))", DATABASE, channelNoHash));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, String.format("Unable to create table %sOptions!", channelNoHash), ex );
			}
			try {
				stmt3 = conn.createStatement();
				stmt3.closeOnCompletion();
				stmt3.executeUpdate(String.format("CREATE TABLE %s.%sSpam(word varchar(25), PRIMARY KEY (word))", DATABASE, channelNoHash));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, String.format("Unable to create table %sSpam!", channelNoHash), ex);
			}
			try {
				stmt4 = conn.createStatement();
				stmt4.closeOnCompletion();
				stmt4.executeUpdate(String.format("CREATE TABLE %s.%sAutoReplies(keyWord varchar(255), reply varchar(4000), PRIMARY KEY (keyWord))", DATABASE, channelNoHash));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, String.format("Unable to create table %sAutoReplies!", channelNoHash), ex);
			}
			try {
				stmt5 = conn.createStatement();
				stmt5.closeOnCompletion();
				stmt5.executeUpdate(String.format("CREATE TABLE %s.%sWhitelist(userID varchar(30), PRIMARY KEY (userID))", DATABASE, channelNoHash));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, String.format("Unable to create table %sWhitelist!", channelNoHash), ex);
			}
			try{
                stmt2=conn.createStatement();
                stmt2.closeOnCompletion();
                stmt2.executeUpdate(String.format("CREATE TABLE %s.%sPoints(userID varchar(25), points INTEGER, PRIMARY KEY (userID))", DATABASE, channelNoHash));
            }catch(SQLException ex){
                logger.log(Level.SEVERE, "Unable to create table donald10101Points!\n", ex);
            }
            try{
                stmt3=conn.createStatement();
                stmt3.closeOnCompletion();
                stmt3.executeUpdate(String.format("CREATE TABLE %s.%sRegulars(userID varchar(25), PRIMARY KEY (userID))", DATABASE, channelNoHash));
            }catch(SQLException ex){
                logger.log(Level.SEVERE, "Unable to create table donald10101Points!\n", ex);
            }
			return true;
		}
	}

	/**
	 * Sends an update to the database (eg. INSERT, DELETE, etc.)
	 * 
	 * @param sqlCommand
	 * @return - true if it successfully executes the update
	 */
	private static boolean executeUpdate(String sqlCommand) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to create connection for SQLCommand: %s", sqlCommand), e);
			return false;
		}
		try {
			stmt.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to execute statment: %s", sqlCommand), e);
			return false;
		}
		return true;
	}

	/**
	 * Sends a query to the database (eg. SELECT, etc.)
	 * @param sqlQuery
	 * @return
	 */
	private static ResultSet executeQuery(String sqlQuery) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt=conn.createStatement();
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to create connection for SQLQuery: %s", sqlQuery), e);
		}
		try {
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to execute query: %s", sqlQuery), e);
		}
		return rs;
	}

	/**
	 * Sends an update to the database (eg. INSERT, DELETE, etc.)
	 * 
	 * @param stmt
	 * @return - true if it successfully executes the update
	 */
	private static boolean executeUpdate(PreparedStatement stmt) {
		try {
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to create connection for SQLCommand"), e);
			return false;
		}
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to execute statment"), e);
			return false;
		}
		return true;
	}

	/**
	 * Sends a query to the database (eg. SELECT, etc.)
	 * @param stmt
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ResultSet executeQuery(PreparedStatement stmt) {
		ResultSet rs = null;
		try {
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to create connection for SQLQuery"), e);
		}
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to execute query"), e);
		}
		return rs;
	}
	
	/**
	 * Clears the auto replies table for the channel provided.
	 * 
	 * @param channelNoHash - the channel to clear auto replies for
	 */
	public static void clearAutoRepliesTable(String channelNoHash) {
		executeUpdate(String.format("DROP TABLE %s.%sAutoReplies", DATABASE, channelNoHash));
		executeUpdate(String.format("CREATE TABLE %s.%sAutoReplies(keyWord varchar(255), reply varchar(255), PRIMARY KEY (keyWord))", DATABASE, channelNoHash));
	}
	
	public static String getUserOAuth(String user) {
		ResultSet rs=executeQuery(String.format("SELECT * FROM "+DATABASE+".userOAuth WHERE userID=\'%s\'", user));
		try {
			if(rs.next()) {
				return rs.getString("oAuth");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("An error occurred getting %s\'s OAuth from the database", user), e);
		}
		return null;
	}

	public static int getOption(String channelNoHash, TOptions option) {
		ResultSet rs=executeQuery(String.format("SELECT * FROM %s.%sOptions WHERE optionID=\'%s\'", DATABASE, channelNoHash, option.getOptionID()));
		try {
			if(rs.next()) {
				return Integer.valueOf(rs.getString(2));
			}
			return -1;
		} catch (SQLException | NumberFormatException e) {
			logger.log(Level.SEVERE, String.format("Unable to get welcome message for %s", channelNoHash), e);
		}
		return -1;
	}
	
	public static String getWelcomeMessage(String channelNoHash) {
		ResultSet rs=executeQuery(String.format("SELECT * FROM %s.%sOptions WHERE optionID=\'%s\'", DATABASE, channelNoHash, TOptions.welcomeMessage));
		try {
			if(rs.next()) {
				return rs.getString(2);
			}
			return null;
		} catch (SQLException | NumberFormatException e) {
			logger.log(Level.SEVERE, String.format("Unable to get welcome message for %s", channelNoHash), e);
		}
		return null;
	}

	public static boolean setWelcomeMessage(String channelNoHash, TOptions option, String value) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("UPDATE %s.%sOptions SET optionID=?,value=? WHERE optionID=?", DATABASE, channelNoHash));
			stmt.setString(1, option.getOptionID());
			stmt.setString(2, value);
			stmt.setString(3, option.getOptionID());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred setting the welcome message", e);
		}
		return executeUpdate(stmt);
	}

	public static boolean setOption(String channelNoHash, TOptions option, int value) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("UPDATE %s.%sOptions SET optionID=?,value=? WHERE optionID=?", DATABASE, channelNoHash));
			stmt.setString(1, option.getOptionID());
			stmt.setString(2, value+"");
			stmt.setString(3, option.getOptionID());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to set option", e);
		}
		return executeUpdate(stmt);
	}
	
	public static boolean addOption(String channelNoHash, TOptions option, String value) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("INSERT INTO %s.%sOptions VALUES(? , ?)", DATABASE, channelNoHash));
			stmt.setString(1, option.getOptionID());
			stmt.setString(2, value+"");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to add option", e);
		}
		return executeUpdate(stmt);
	}

	public static boolean isMod(String moderator, String channelNoHash) {
		ResultSet rs = executeQuery(String.format("SELECT * FROM %s.%sMods WHERE userID=\'%s\'", DATABASE, channelNoHash, moderator));
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("An error occurred checking if %s is in %s's Mod List.", moderator, channelNoHash), e);
		}
		return false;
	}
	
	public static void addMod(String moderator, String channelNoHash) {
		executeUpdate(String.format("INSERT INTO %s.%sMods VALUES(\'%s\')", DATABASE, channelNoHash, moderator));
	}
	
	public static void addAutoReply(String channelNoHash, String keywords, String reply) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("INSERT INTO %s.%sAutoReplies VALUES(? , ?)", DATABASE, channelNoHash));
			stmt.setString(1, keywords);
			stmt.setString(2, reply);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to set option", e);
		}
		executeUpdate(stmt);
	}

	public static ResultSet getAutoReplies(String channelNoHash) {
		return executeQuery(String.format("SELECT * FROM %s.%sAutoReplies", DATABASE, channelNoHash));
	}
	
	public static ResultSet getSpam(String channelNoHash) {
		return executeQuery(String.format("SELECT * FROM %s.%sSpam", DATABASE, channelNoHash));
	}

	public static boolean delModerator(String moderator, String channelNoHash) {
		if(!MyBotMain.isDefaultMod(moderator, channelNoHash)) {
			return executeUpdate(String.format("DELETE FROM %s.%sMods WHERE userID=\'%s\'", DATABASE, channelNoHash, moderator));
		}
		return false;
	}

	public static boolean delAutoReply(String channelNoHash, String keywords) {PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("DELETE FROM %s.%sAutoReplies WHERE keyWord=?", DATABASE, channelNoHash));
			stmt.setString(1, keywords);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to set option", e);
		}
		return executeUpdate(stmt);
	}

	public static ResultSet getCustomCommands(String channelNoHash) {
		return executeQuery(String.format("SELECT * FROM %s.%sAutoReplies WHERE keyWord LIKE '!%%'", DATABASE, channelNoHash));
	}

	public static boolean addSpam(String channelNoHash, String word) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("INSERT INTO %s.%sSpam VALUES(?)", DATABASE, channelNoHash));
			stmt.setString(1, word);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to set option", e);
		}
		return executeUpdate(String.format("INSERT INTO %s.%sSpam VALUES(\'%s\')", DATABASE, channelNoHash, word));
	}
	
	public static boolean delSpam(String channelNoHash, String word) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(String.format("DELETE FROM %s.%sSpam WHERE word=?", DATABASE, channelNoHash));
			stmt.setString(1, word);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to set option", e);
		}
		return executeUpdate(String.format("DELETE FROM %s.%sSpam WHERE word=\'%s\'", DATABASE, channelNoHash, word));
	}

	public static boolean addToWhiteList(String channelNoHash, String target) {
		return executeUpdate(String.format("INSERT INTO %s.%sWhitelist VALUES(\'%s\')", DATABASE, channelNoHash, target));
	}

	public static boolean delWhitelist(String channelNoHash, String target) {
		return executeUpdate(String.format("DELETE FROM %s.%sWhitelist WHERE userID=\'%s\'", DATABASE, channelNoHash, target));
	}

	public static boolean isWhitelisted(String sender, String channelNoHash) {
		ResultSet rs = executeQuery(String.format("SELECT * FROM %s.%sWhitelist WHERE userID=\'%s\'", DATABASE, channelNoHash, sender));
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred checking if %user% is witelisted!".replace("%user%", sender), e);
		}
		return false;
	}

	public static void addPoints(String nick, String channelNoHash, int ammount) {
		ResultSet rs = Database.executeQuery(String.format("SELECT * FROM %s.%sPoints WHERE userID=\'%s\'", DATABASE, channelNoHash, nick));
		try {
			if(!rs.next()){
				Database.executeUpdate(String.format("INSERT INTO %s.%sPoints VALUES (\'%s\',1)", DATABASE, channelNoHash, nick));
				return;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An Error occured updating "+nick+"'s points!\n", e);
		}
		try {
			Database.executeUpdate(String.format("UPDATE %s.%sPoints SET userID=\'%s\',points=%d WHERE userID=\'%s\'", DATABASE, channelNoHash, nick, rs.getInt(2)+ammount, nick));
			if(rs.getInt(2)+ammount==72) {
				Database.executeUpdate(String.format("INSERT INTO %s, %sRegulars VALUES (\'%s\')", DATABASE, channelNoHash, nick));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An Error occured updating "+nick+"'s points!\n", e);
		}
	}

	public static String getPoints(String sender, String substring) {
		ResultSet rs = executeQuery(String.format("SELECT * FROM %s.%sPoints WHERE userID=\'%s\'", DATABASE, substring, sender));
		try {
			if(rs.next()) {
				return rs.getInt(2)+"";
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred getting a user's points.", e);
		}
		return null;
	}

	public static String topPlayers(int ammount, String channelNoHash) {
		StringBuilder output = new StringBuilder();
		output.append("The top " + ammount + " points holder(s) are: ");
		ResultSet rs=executeQuery(String.format("SELECT * FROM %s.%sPoints ORDER BY points DESC", DATABASE, channelNoHash));
		try {
			while(rs.next()&&ammount>1){
				if(!rs.getString(1).equalsIgnoreCase("pcmrbot") && !rs.getString(1).equalsIgnoreCase("pcmrbottester") && !rs.getString(1).equalsIgnoreCase("botduck") && !rs.getString(1).equalsIgnoreCase(channelNoHash)) {
					output.append(rs.getString(1)+": "+rs.getInt(2) + ", ");
					ammount--;
				}
			}
			output.append(rs.getString(1)+": "+rs.getInt(2));
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error occurred creating Top list!", e);
		}
		return output.toString();
	}
}
