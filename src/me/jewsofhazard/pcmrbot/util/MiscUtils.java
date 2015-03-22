package me.jewsofhazard.pcmrbot.util;

public class MiscUtils {
	public static int[] parseTime(String time) {
		int hour = Integer.valueOf(time.substring(0, time.indexOf(":")));
		int minute = Integer.valueOf(time.substring(time.indexOf(":") + 1, time.indexOf(" ")));
		String amPM = time.substring(time.indexOf(' ') + 1);
		if (amPM.equalsIgnoreCase("pm")) {
			if (hour != 12) {
				hour +=12;
			}
			if (hour > 24) {
				hour -= 24;
			}
		} else {
			if (hour == 12) {
				hour = 0;
			}
		}
		return new int[] {hour, minute};
	}
}
