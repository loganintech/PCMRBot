/*	  It's a Twitch bot, because we can.
 *    Copyright (C) 2015  Logan Ssaso, James Wolff, Angablade
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.util.Options;

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
				stmt2.executeUpdate(String.format("CREATE TABLE %s.%sOptions(optionID varchar(25), value varchar(4000), PRIMARY KEY (optionID))", DATABASE, channelNoHash));
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
			stmt = conn.createStatement();
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
	 * Clears the auto replies table for the channel provided.
	 * 
	 * @param channelNoHash - the channel to clear auto replies for
	 */
	public static void clearAutoRepliesTable(String channelNoHash) {
		Statement stmt;
		Statement stmt1;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeUpdate(String.format("DROP TABLE %s.%sAutoReplies", DATABASE, channelNoHash));
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, String.format("Unable to delete table %sAutoReplies!", channelNoHash), ex);
		}
		try {
			stmt1 = conn.createStatement();
			stmt1.closeOnCompletion();
			stmt1.executeUpdate(String.format("CREATE TABLE %s.%sAutoReplies(keyWord varchar(255), reply varchar(255), PRIMARY KEY (keyWord))", DATABASE, channelNoHash));
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, String.format("Unable to create table %sAutoReplies!", channelNoHash), ex);
		}
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

	public static String getOption(String channelNoHash, Options option) {
		ResultSet rs=executeQuery(String.format("SELECT * FROM %s.%sOptions WHERE optionID=\'%s\'", DATABASE, channelNoHash, option.getOptionID()));
		try {
			if(rs.next()) {
				return rs.getString(2);
			}
			return null;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("Unable to get welcome message for %s", channelNoHash), e);
		}
		return null;
	}

	public static boolean setOption(String channelNoHash, Options option, String value) {
		return executeUpdate(String.format("UPDATE %s.%sOptions SET optionID=\'%s\',value=\'%s\' WHERE optionID=\'%s\'", DATABASE, channelNoHash, option.getOptionID(), value, option.getOptionID()));
	}
	
	public static boolean addOption(String channelNoHash, Options option, String value) {
		return executeUpdate(String.format("INSER INTO %s.%sOptions VALUES(\'%s\',\'%s\')", DATABASE, channelNoHash, option.getOptionID(), value));
	}

	public static boolean isMod(String moderator, String channelNoHash) {
		ResultSet rs = executeQuery(String.format("SELECT * FROM %s.%sMods WHERE userID=\'%s\'", DATABASE, channelNoHash, moderator));
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, String.format("An error occurred adding %s to %s's Mod List. This can probably be ignored!", moderator, channelNoHash), e);
		}
		return false;
	}
	
	public static void addMod(String moderator, String channelNoHash) {
		executeUpdate(String.format("INSERT INTO %s.%sMods VALUES(\'%s\')", DATABASE, channelNoHash, moderator));
	}
	
	public static void addAutoReply(String channelNoHash, String keywords, String reply) {
		Database.executeUpdate(String.format("INSERT INTO %s.%sAutoReplies VALUES(\'%s\' , '%s\')", DATABASE, channelNoHash, keywords, reply));
	}

	public static ResultSet getAutoReplies(String channelNoHash) {
		return executeQuery(String.format("SELECT * FROM %s.%sAutoReplies", DATABASE, channelNoHash));
	}
	
	public static ResultSet getSpam(String channelNoHash) {
		return executeQuery(String.format("SELECT * FROM %s.%sSpam", DATABASE, channelNoHash));
	}

}
