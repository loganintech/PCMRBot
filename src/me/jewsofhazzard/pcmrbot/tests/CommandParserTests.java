package me.jewsofhazzard.pcmrbot.tests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import me.jewsofhazzard.pcmrbot.Commands.CommandParser;
import me.jewsofhazzard.pcmrbot.Commands.Command;
import me.jewsofhazzard.pcmrbot.Commands.Help;

/**
 * Tests for {@link CommandParser}
 */
public class CommandParserTests {

	@Test
	public void InitFindsAndRegistersCommands() {
		Command helpCommand = new Help();
		CommandParser.init();
		
		String result1 = helpCommand.execute("channel1", "sender1", "arg1");
		String result2 = CommandParser.parse(helpCommand.getCommandText(), "sender1", "channel1", "arg1");
		
		assertNotNull(result1);
		assertNotNull(result2);
		
		assertTrue("command outputs are the same", result1.equals(result2));
	}
}
