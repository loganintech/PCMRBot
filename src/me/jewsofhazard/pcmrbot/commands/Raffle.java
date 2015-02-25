package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.MyBotMain;
import me.jewsofhazard.pcmrbot.util.CLevel;
import me.jewsofhazard.pcmrbot.util.ULevel;

public class Raffle extends Command implements ICommand {

	private CLevel level=CLevel.Mod;

	@Override
	public CLevel getCommandLevel() {
		return level;
	}
	
	@Override
	public String getCommandText() {
		return "raffle";
	}
	
	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(MyBotMain.getBot().getRaffle(channel) != null) {
			return "There is already a raffle in progress!";
		}
		ULevel level=ULevel.getTypeFromString(parameters[0]);
		if(level == null) {
			return "Improper raffle type! Valid choices are \"everyone\", \"followers\" \"subscribers\"";
		}
		MyBotMain.getBot().addRaffle(channel, new me.jewsofhazard.pcmrbot.util.Raffle(channel, level));
		return "Raffle started for %level%".replace("%level%", level.toString());
	}

}
