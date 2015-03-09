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
	
	/**
	 * Creates the delayed task for the pcmr titles
	 */
	public static void createDelayedTasks() {
		switch(Calendar.getInstance(TimeZone.getTimeZone("PST")).get(Calendar.DAY_OF_WEEK)) {
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
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(3));
				if(rs.getInt(1) >= i && !rs.getString(3).equalsIgnoreCase("null")) {
					Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
					int date = c.get(Calendar.DATE) + (rs.getInt(1) - i);
					int month = c.get(Calendar.MONTH);
					int year = c.get(Calendar.YEAR);
					if(date / c.getActualMaximum(Calendar.MONTH) > 0) {
						month++;
						if(month / c.getActualMaximum(Calendar.YEAR) > 0) {
							year++;
							month = 0;
						}
						date = date % c.getActualMaximum(Calendar.MONTH);
					}
					System.out.println(rs.getInt(1));
					System.out.println(rs.getString(4));
					int hour = Integer.valueOf(rs.getString(4).substring(0, rs.getString(4).indexOf(":")));
					int minute = Integer.valueOf(rs.getString(4).substring(rs.getString(4).indexOf(":") + 1, rs.getString(4).indexOf(" ")));
					c.set(Calendar.AM_PM, Calendar.AM);
					c.set(year, month, date, hour, minute, 0);
					new DelayedTitleTask(rs.getString(2)+" playing "+rs.getString(3), rs.getString(3), c.getTimeInMillis() - Calendar.getInstance(TimeZone.getTimeZone("PST")).getTimeInMillis());
					minute -= 30;
					if(minute < 0) {
						hour--;
						minute = 60 - Math.abs(minute - 30);
					}
					c.set(year, month, date, hour, minute, 0);
					c.set(Calendar.AM_PM, Calendar.AM);
					new DelayedTitleTask("Starting soon "+rs.getString(2)+" playing "+rs.getString(3), rs.getString(3), c.getTimeInMillis() - Calendar.getInstance(TimeZone.getTimeZone("PST")).getTimeInMillis());
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an issue scheduling the title changes", e);
		}
	}
	
}
