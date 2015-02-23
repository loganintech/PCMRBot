package me.jewsofhazard.pcmrbot.tests;

import static org.junit.Assert.assertNotNull;
import me.jewsofhazard.pcmrbot.commands.Command;
import me.jewsofhazard.pcmrbot.commands.CommandParser;
import me.jewsofhazard.pcmrbot.commands.Join;

import org.junit.Test;

/**
 * Tests for {@link CommandParser}
 */
public class CommandParserTests {

	@Test
	public void InitFindsAndRegistersCommands() {
		Command helpCommand = new Join();
		CommandParser.init();
		
		String result1 = helpCommand.execute("channel1", "sender1", "arg1");
		String result2 = CommandParser.parse("join", "sender1", "channel1", "arg1");
		
		assertNotNull(result1);
		assertNotNull(result2);
		
		System.out.println(result1);
		System.out.println(result2);
		System.out.println(result1.equals(result2));
	}
}
