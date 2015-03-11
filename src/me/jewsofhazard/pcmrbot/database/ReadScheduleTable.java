package me.jewsofhazard.pcmrbot.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.util.DelayedTitleTask;

public class ReadScheduleTable {
	private static final Logger logger = Logger.getLogger(ReadScheduleTable.class+"");
	
	private static final Calendar PST_CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("PST8PDT"));
	
	/**
	 * Creates the delayed task for the pcmr titles
	 */
	public static void createDelayedTasks() {
		switch(PST_CALENDAR.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: 
			readTable(1);
			break;
		case Calendar.MONDAY: 
			readTable(2);
			break;
		case Calendar.TUESDAY: 
			readTable(3);
			break;
		case Calendar.WEDNESDAY: 
			readTable(4);
			break;
		case Calendar.THURSDAY: 
			readTable(5);
			break;
		case Calendar.FRIDAY: 
			readTable(6);
			break;
		case Calendar.SATURDAY: 
			readTable(7);
		}
	}

	/**
	 * @param i - the day of the week in number form
	 */
	private static void readTable(int i) {
		ResultSet rs = Database.executeQuery(String.format("SELECT * FROM %s.pcmrSchedule", Database.DATABASE));
		try {
			while(rs.next()) {
				if(rs.getInt(1) >= i && !rs.getString(3).equalsIgnoreCase("null")) {
					Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST8PDT"));
					int day = c.get(Calendar.DATE) + (rs.getInt(1) - i);
					int month = c.get(Calendar.MONTH);
					int year = c.get(Calendar.YEAR);
					if(day / c.getMaximum(Calendar.DATE) > 0) {
						month++;
						if(month / c.getMaximum(Calendar.MONTH) > 0) {
							year++;
							month = 0;
						}
						if(day > c.getMaximum(Calendar.DATE)) {
							day = day % c.getMaximum(Calendar.DATE);
						}
					}
					int hour = Integer.valueOf(rs.getString(4).substring(0, rs.getString(4).indexOf(":"))) + 12;
					int minute = Integer.valueOf(rs.getString(4).substring(rs.getString(4).indexOf(":") + 1, rs.getString(4).indexOf(" ")));
					c.set(year, month, day, hour, minute, 0);
					new DelayedTitleTask(rs.getString(2)+" playing "+rs.getString(3), rs.getString(3), c.getTimeInMillis() - PST_CALENDAR.getTimeInMillis());
					minute -= 30;
					if(minute < 0) {
						hour--;
						minute = 60 - Math.abs(minute);
					}
					c.set(year, month, day, hour, minute, 0);
					new DelayedTitleTask("Starting soon "+rs.getString(2)+" playing "+rs.getString(3), rs.getString(3), c.getTimeInMillis() - PST_CALENDAR.getTimeInMillis());
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an issue scheduling the title changes", e);
		}
	}
	
}
