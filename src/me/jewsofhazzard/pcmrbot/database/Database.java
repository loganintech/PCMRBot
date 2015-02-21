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

package me.jewsofhazzard.pcmrbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			conn = DriverManager.getConnection(URL+"user=bot&password="+pass);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Creates the tables and for the bot's channel. Also creates the Schema.
	 * 
	 * @return - true if it has to create the tables
	 */
	public static boolean getMainTables() {
		Statement stmt;
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeQuery("SELECT * FROM " + DATABASE
					+ ".pcmrbotMods");
			return false;
		} catch (SQLException e) {
			try {
				stmt1 = conn.createStatement();
				stmt1.closeOnCompletion();
				stmt1.executeUpdate("CREATE TABLE "
						+ DATABASE
						+ ".pcmrbotMods(userID varchar(25), PRIMARY KEY (userID))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"Unable to create table pcmrbotMods!\n" + ex.toString());
			}
			try {
				stmt2 = conn.createStatement();
				stmt2.closeOnCompletion();
				stmt2.executeUpdate("CREATE TABLE "
						+ DATABASE
						+ ".pcmrbotOptions(optionID varchar(25), value varchar(25), PRIMARY KEY (optionID))");
			} catch (SQLException ex) {
				logger.log(
						Level.SEVERE,
						"Unable to create table pcmrbotOptions!\n"
								+ ex.toString());
			}
			try {
				stmt3 = conn.createStatement();
				stmt3.closeOnCompletion();
				stmt3.executeUpdate("CREATE TABLE " + DATABASE
						+ ".pcmrbotSpam(word varchar(25), PRIMARY KEY (word))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"Unable to create table pcmrbotSpam!\n" + ex.toString());
			}
			try {
				stmt4 = conn.createStatement();
				stmt4.closeOnCompletion();
				stmt4.executeUpdate("CREATE TABLE "
						+ DATABASE
						+ ".pcmrbotAutoReplies(keyWord varchar(255), reply varchar(255), PRIMARY KEY (keyWord))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"Unable to create table pcmrbotAutoReplies!\n" + ex.toString());
			}
			return true;
		}
	}

	/**
	 * Creates the tables for the provided channel
	 * 
	 * @param channel - the channel we are connecting to.
	 * @return - true if it has to create the tables
	 */
	public static boolean getChannelTables(String channel) {
		Statement stmt;
		Statement stmt1;
		Statement stmt2;
		Statement stmt3;
		Statement stmt4;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeQuery("SELECT * FROM " + DATABASE + "." + channel
					+ "Mods");
			return false;
		} catch (SQLException e) {
			try {
				stmt1 = conn.createStatement();
				stmt1.closeOnCompletion();
				stmt1.executeUpdate("CREATE TABLE " + DATABASE + "."
						+ channel
						+ "Mods(userID varchar(25), PRIMARY KEY (userID))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Unable to create table " + channel
						+ "Mods!\n" + ex.toString());
			}
			try {
				stmt2 = conn.createStatement();
				stmt2.closeOnCompletion();
				stmt2.executeUpdate("CREATE TABLE "
						+ DATABASE
						+ "."
						+ channel
						+ "Options(optionID varchar(25), value varchar(255), PRIMARY KEY (optionID))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Unable to create table " + channel
						+ "Options!\n" + ex.toString());
			}
			try {
				stmt3 = conn.createStatement();
				stmt3.closeOnCompletion();
				stmt3.executeUpdate("CREATE TABLE " + DATABASE + "."
						+ channel
						+ "Spam(word varchar(25), PRIMARY KEY (word))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Unable to create table " + channel
						+ "Spam!\n" + ex.toString());
			}
			try {
				stmt4 = conn.createStatement();
				stmt4.closeOnCompletion();
				stmt4.executeUpdate("CREATE TABLE "
						+ DATABASE
						+ "."
						+ channel
						+ "AutoReplies(keyWord varchar(255), reply varchar(255), PRIMARY KEY (keyWord))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Unable to create table " + channel
						+ "AutoReplies!\n" + ex.toString());
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
	public static boolean executeUpdate(String sqlCommand) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,
					"Unable to create connection for SQLCommand: " + sqlCommand
							+ "\n" + e.toString());
			return false;
		}
		try {
			stmt.executeUpdate(sqlCommand);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to execute statment: "
					+ sqlCommand + "\n" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Sends a query to the database (eg. SELECT, etc.)
	 * @param sqlQuery
	 * @return
	 */
	public static ResultSet executeQuery(String sqlQuery) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,
					"Unable to create connection for SQLQuery: " + sqlQuery
							+ "\n" + e.toString());
		}
		try {
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to execute query: " + sqlQuery
					+ "\n" + e.toString());
		}
		return rs;
	}
	
	/**
	 * Clears the auto replies table for the channel provided.
	 * 
	 * @param channel - the channel to clear auto replies for
	 */
	public static void clearAutoRepliesTable(String channel) {
		Statement stmt;
		Statement stmt1;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeUpdate("DROP TABLE "
					+ DATABASE
					+ "."
					+ channel
					+ "AutoReplies");
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Unable to delete table " + channel
					+ "AutoReplies!\n" + ex.toString());
		}
		try {
			stmt1 = conn.createStatement();
			stmt1.closeOnCompletion();
			stmt1.executeUpdate("CREATE TABLE "
					+ DATABASE
					+ "."
					+ channel
					+ "AutoReplies(keyWord varchar(255), reply varchar(255), PRIMARY KEY (keyWord))");
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Unable to create table " + channel
					+ "AutoReplies!\n" + ex.toString());
		}
	}
	
	public static String getUserOAuth(String user) {
		ResultSet rs=executeQuery("SELECT * FROM "+DATABASE+".userOAuth WHERE userID=\'"+user+"\'");
		try {
			if(rs.next()) {
				logger.info("inside if");
				return rs.getString("oAuth");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred getting "+user+"\'s OAuth from the database", e);
		}
		return null;
	}

	public static String getWelcomeMessage(String channel) {
		ResultSet rs=executeQuery("SELECT * FROM "+DATABASE+"."+channel.substring(1)+"Options WHERE optionID=\'welcomeMessage\'");
		try {
			rs.next();
			return rs.getString("value");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to get welcome message for "+channel.substring(1), e);
		}
		return null;
	}

	public static void setWelcomeMessage(String message) {
		executeUpdate("UPDATE "+DATABASE+".Options SET "
				+ "optionID=\'welcomeMessage\'," +
				"value=\'"+message+"\'"
				+"WHERE optionID=\'welcomeMessage\'");
	}

}
