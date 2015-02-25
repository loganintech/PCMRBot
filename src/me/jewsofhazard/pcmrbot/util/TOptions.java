package me.jewsofhazard.pcmrbot.util;

public enum TOptions {

	welcomeMessage("welcomeMessage"), numCaps("numCaps"), numSymbols(
			"numSymbols"), numEmotes("numEmotes"), paragraphLength(
			"paragraphLength"), link("link");

	private final String optionID;

	public String getOptionID() {
		return optionID;
	}

	TOptions(String id) {
		optionID = id;
	}
}
