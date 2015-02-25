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

package me.jewsofhazard.pcmrbot.tests;

import static org.junit.Assert.assertNotNull;
import me.jewsofhazard.pcmrbot.commands.Command;
import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.commands.Setup;

import org.junit.Test;

/**
 * Tests for {@link CommandParser}
 */
public class CommandParserTests {

	@Test
	public static void test() {
		Command helpCommand = new Setup();
		CommandParser.init();
		
		String result1 = helpCommand.execute("channel1", "#channel1", "arg1");
		String result2 = CommandParser.parse("setup", "channel1", "#channel1", "arg1");
		
		assertNotNull(result1);
		assertNotNull(result2);
		
		System.out.println(result1);
		System.out.println(result2);
		System.out.println(result1.equals(result2));
	}
}
