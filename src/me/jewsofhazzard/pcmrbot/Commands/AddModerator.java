package me.jewsofhazzard.pcmrbot.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazzard.pcmrbot.database.Database;

public class AddModerator {
	
	private static final Logger logger = Logger.getLogger(AddModerator.class + "");
	String [] p;
	String reply;
	
	public AddModerator(String... params){
		
		p = params;
		execute();
		
	}
	
	public void execute(){
		
		ResultSet rs = Database.executeQuery("SELECT * FROM "
				+ Database.DATABASE + "." + p[1].substring(1)
				+ "Mods WHERE userID=\'" + p[0] + "\'");
		try {
			if (rs.next()) {
				reply= p[0]
						+ " is already a moderator!";
			} else {
				Database.executeUpdate("INSERT INTO " + Database.DATABASE + "."
						+ p[1].substring(1) + "Mods VALUES(\'"
						+ p[0] + "\')");
				reply = "Successfully added " + p[0]
						+ " to the bots mod list!";
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred adding " + p[0]
					+ " to " + p[1]
					+ "'s Mod List. This can probably be ignored!", e);
		}
		
	}
	
	public String getReply(){
		
		return reply;
		
	}
	
}
