package me.jewsofhazzard.pcmrbot.util;

public enum Options {

	welcomeMessage("welcomeMessage"), numCaps("numCaps"), numSymbols(
			"numSymbols"), numEmotes("numEmotes"), paragraphLength(
			"paragraphLength");

	private final String optionID;

	public String getOptionID() {
		return optionID;
	}

	Options(String id) {
		optionID = id;
	}
}
