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

	private static final String URL = "jdbc:derby://localhost/PCMRBot";

	public static final String DEFAULT_SCHEMA = "PCMRBOT";

	static final Logger logger = Logger.getLogger(Database.class + "");

	/**
	 * Creates a connection to the database.
	 * 
	 * @return - true if connection is successful
	 */
	public static boolean initDBConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			logger.log(
					Level.SEVERE,
					"Unable to find EmbeddedDriver in classpath!\n"
							+ e.toString());
		}
		try {
			conn = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			SQLException f = (SQLException) e;
			while (f.getNextException() != null) {
				f = f.getNextException();
			}
			if (f.getSQLState().equals("XSDB6")) {
				javax.swing.JOptionPane.showMessageDialog(null,
						"The Program is already running in another instance!");
				System.exit(0);
			}
			try {
				conn = DriverManager.getConnection(URL + ";create=true;");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"An Internal Communication Error Occurred With the Database");
				return false;
			}
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
		Statement stmt5;
		try {
			stmt = conn.createStatement();
			stmt.closeOnCompletion();
			stmt.executeUpdate("CREATE SCHEMA " + DEFAULT_SCHEMA);
		} catch (SQLException ex) {
			if (!ex.getSQLState().equals("X0Y68")) {
				logger.log(
						Level.SEVERE,
						"Unable To Create Necessary Database Resources\n"
								+ ex.toString());
			}
		}
		try {
			stmt1 = conn.createStatement();
			stmt1.closeOnCompletion();
			stmt1.executeQuery("SELECT * FROM " + DEFAULT_SCHEMA
					+ ".pcmrbotMods");
			return false;
		} catch (SQLException e) {
			try {
				stmt2 = conn.createStatement();
				stmt2.closeOnCompletion();
				stmt2.executeUpdate("CREATE TABLE "
						+ DEFAULT_SCHEMA
						+ ".pcmrbotMods(userID varchar(25), PRIMARY KEY (userID))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"Unable to create table pcmrbotMods!\n" + ex.toString());
			}
			try {
				stmt3 = conn.createStatement();
				stmt3.closeOnCompletion();
				stmt3.executeUpdate("CREATE TABLE "
						+ DEFAULT_SCHEMA
						+ ".pcmrbotOptions(optionID varchar(25), value varchar(25), PRIMARY KEY (optionID))");
			} catch (SQLException ex) {
				logger.log(
						Level.SEVERE,
						"Unable to create table pcmrbotOptions!\n"
								+ ex.toString());
			}
			try {
				stmt4 = conn.createStatement();
				stmt4.closeOnCompletion();
				stmt4.executeUpdate("CREATE TABLE " + DEFAULT_SCHEMA
						+ ".pcmrbotSpam(word varchar(25), PRIMARY KEY (word))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						"Unable to create table pcmrbotSpam!\n" + ex.toString());
			}
			try {
				stmt5 = conn.createStatement();
				stmt5.closeOnCompletion();
				stmt5.executeUpdate("CREATE TABLE "
						+ DEFAULT_SCHEMA
						+ ".pcmrbotAutoReplies(keyWord varchar(255), reply(255), PRIMARY KEY (keyWord))");
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
			stmt.executeQuery("SELECT * FROM " + DEFAULT_SCHEMA + "." + channel
					+ "Mods");
			return false;
		} catch (SQLException e) {
			try {
				stmt1 = conn.createStatement();
				stmt1.closeOnCompletion();
				stmt1.executeUpdate("CREATE TABLE " + DEFAULT_SCHEMA + "."
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
						+ DEFAULT_SCHEMA
						+ "."
						+ channel
						+ "Options(optionID varchar(25), value varchar(25), PRIMARY KEY (optionID))");
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Unable to create table " + channel
						+ "Options!\n" + ex.toString());
			}
			try {
				stmt3 = conn.createStatement();
				stmt3.closeOnCompletion();
				stmt3.executeUpdate("CREATE TABLE " + DEFAULT_SCHEMA + "."
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
						+ DEFAULT_SCHEMA
						+ "."
						+ channel
						+ "AutoReplies(keyWord varchar(255), reply(255), PRIMARY KEY (keyWord))");
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
					+ DEFAULT_SCHEMA
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
					+ DEFAULT_SCHEMA
					+ "."
					+ channel
					+ "AutoReplies(keyWord varchar(255), reply(255), PRIMARY KEY (keyWord))");
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Unable to create table " + channel
					+ "AutoReplies!\n" + ex.toString());
		}
	}

}
