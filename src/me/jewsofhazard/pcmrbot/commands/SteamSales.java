package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class SteamSales extends Command implements ICommand {

	private CLevel level=CLevel.Normal;

	@Override
	public CLevel getCommandLevel() {
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
