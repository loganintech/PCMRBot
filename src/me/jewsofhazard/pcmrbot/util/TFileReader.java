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

package me.jewsofhazard.pcmrbot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TFileReader {
	static Logger logger = Logger.getLogger(TFileReader.class + "");

	public static ArrayList<String> readFile(File f) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE,
					"Error reading the file at location: " + f.getName() + "\n"
							+ e.toString(), e);
		}
		ArrayList<String> buffer = new ArrayList<>();
		try (Scanner scanner = new Scanner(fis)) {
			while (scanner.hasNext()) {
				buffer.add(scanner.nextLine());
			}
		}
		return buffer;
	}

	public static String readFileAsString(File f) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE,
					"Error reading the file at location: " + f.getName() + "\n"
							+ e.toString(), e);
		}
		StringBuilder buffer = new StringBuilder();
		try (Scanner scanner = new Scanner(fis)) {
			while (scanner.hasNext()) {
				buffer.append(scanner.nextLine());
			}
		}
		return buffer.toString();
	}
}