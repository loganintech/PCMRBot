package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.util.CLevel;

public class Google extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Mod;
	}

	@Override
	public String getCommandText() {
		return "google";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://google.com/?q=");
		for(int i=1;i<parameters.length;i++) {
			sb.append(parameters[i]+"+");
		}
		sb.append(parameters[parameters.length-1]);
		return sb.toString();
	}

}
