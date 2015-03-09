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

package me.jewsofhazard.pcmrbot.util;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

public class CommandsPage {

	private static String template = getTemplate();
	
	private static final Logger logger = Logger.getLogger(CommandsPage.class+"");
	
	/**
	 * @param channelNoHash - channel without the leading #
	 * @return true if the page is created, false otherwise
	 */
	public static boolean createCommandsHTML(String channelNoHash) {
		String tableBody = generateTableBodyHTML(channelNoHash);
		if(tableBody != null) {
			if(System.getProperty("os.name").toLowerCase().contains("win")) {
				TFileWriter.overWriteFile(new File("C:/Apache2/htdocs/commands/%channel%.html".replace("%channel%", channelNoHash)), template.replace("$tablebody", tableBody));
			} else {
				TFileWriter.overWriteFile(new File("/var/www/commands/%channel%.html".replace("%channel%", channelNoHash)), template.replace("$tablebody", tableBody));
			}
			return true;
		}
		return false;
	}

	/**
	 * @return template based on OS
	 */
	private static String getTemplate() {
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			return TFileReader.readFileAsString(new File("C:/Apache2/htdocs/commands/commandsTemplate.html"));
		}
		return TFileReader.readFileAsString(new File("/var/www/commands/commandsTemplate.html"));
	}

	/**
	 * @param channelNoHash - channel without the leading #
	 * @return table body
	 */
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
	
	/**
	 * @param channelNoHash - channel without the leading #
	 * @return true if the channel has a commands page
	 */
	public static boolean pageExists(String channelNoHash) {
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			return new File("C:/Apache2/htdocs/commands/%channel%.html".replace("%channel%", channelNoHash)).exists();
		}
		return new File("/var/www/commands/commandsTemplate.html".replace("%channel%", channelNoHash)).exists();
	}

	/**
	 * @param command - command to put in the table
	 * @param reply - reply to go in column two
	 * @return the table
	 */
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
