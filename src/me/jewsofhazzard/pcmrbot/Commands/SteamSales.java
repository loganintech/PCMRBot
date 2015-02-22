package me.jewsofhazzard.pcmrbot.Commands;

import me.jewsofhazzard.pcmrbot.util.CommandLevel;

public class SteamSales implements Command {

	private CommandLevel level=CommandLevel.Normal;

	@Override
	public CommandLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "steamsales";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		return "You can find the recent steam sales by heading to https://steamdb.info/sales/";
	}

}
