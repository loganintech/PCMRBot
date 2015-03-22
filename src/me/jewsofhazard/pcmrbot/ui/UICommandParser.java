package me.jewsofhazard.pcmrbot.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.database.Database;

public class UICommandParser implements Runnable{

	private static final Logger logger = Logger.getLogger(UICommandParser.class + "");
	
	public static void init() {
		new Thread(new UICommandParser()).start();
	}
	
	@Override
	public void run() {
		while (true) {
			ResultSet rs = Database.readUICommandsTable();
			if (rs != null) {
				try {
					while (rs.next()) {
						CommandParser.parse(rs.getString(3), rs.getString(2), "#" + rs.getString(2), rs.getString(4).split(" "));
						Database.deleteUICommand(rs.getInt(1));
					}
				} catch (SQLException e) {
					logger.log(Level.WARNING, "There was an issue parsing a command from the database", e);
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "There was an issue sleeping", e);
			}
		}
	}
	
	
}
