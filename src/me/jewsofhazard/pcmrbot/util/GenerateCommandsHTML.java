package me.jewsofhazard.pcmrbot.util;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

public class GenerateCommandsHTML {

	private static final String template = TFileReader.readFileAsString(new File("/var/www/commands/template.html"));
	
	private static final Logger logger = Logger.getLogger(GenerateCommandsHTML.class+"");
	
	public static boolean createCommandsHTML(String channelNoHash) {
		String tableBody = generateTableBodyHTML(channelNoHash);
		if(tableBody != null) {
			TFileWriter.writeFile(new File("/var/www/commands/%channel%.html".replace("%channel%", channelNoHash)), template.replace("$tablebody", tableBody));
			return true;
		}
		return false;
	}

	private static String generateTableBodyHTML(String channelNoHash) {
		StringBuilder tableBody = new StringBuilder();
		ResultSet rs=Database.getCustomCommands(channelNoHash);
		try {
			while(rs.next()) {
				tableBody.append(generateTableRow(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred getting the table body for %channel%".replace("%channel%", channelNoHash), e);
			return null;
		}
		if(tableBody.toString() != null && tableBody.length() > 0) {
			return tableBody.toString();
		}
		return null;
	}

	private static String generateTableRow(String command, String reply) {
		StringBuilder tr = new StringBuilder();
		tr.append("<tr id=\"%com%\">".replace("%com%", command))
			.append("<td>").append(command).append("</td>")
			.append("<td>").append(reply).append("</td>")
			.append("<td>").append("Everyone").append("</td>")
		.append("</tr>");
		return tr.toString();
	}
	
}
