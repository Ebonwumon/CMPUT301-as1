package net.depotwarehouse.as1.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Aggregate counts is a static class that will return lists of timeAggregatecounts aggregated by hour, days, etc.
 * depending on the methods. The methods are quite similar and really ought to be refactored, however they _work_ and
 * developing a nice extensible factory would have taken longer than was budgeted to complete the assignment.
 * 
 * I've documented the first method only, the comments are applicable to all the methods in the class
 * @author tpavlek
 *
 */
public class AggregateCounts {
	/**
	 * @param logs : List of logs - all logs are for a counter with a single ID.
	 * @return List of TimeAggregateCounts
	 */
	public static ArrayList<TimeAggregateCount> getByHours(ArrayList<Log> logs) {
		// If there are no logs in the list, we're done, return empty list
		if (logs.size() == 0) {
			return new ArrayList<TimeAggregateCount>();
		} else {
			ArrayList<TimeAggregateCount> count = new ArrayList<TimeAggregateCount>();
			
			// We instantiate a base date for the first log object. We'll compare to this later.
			Date d = logs.get(0).getDate();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
			int prevDay = calendar.get(Calendar.DAY_OF_YEAR);
			int prevYear = calendar.get(Calendar.YEAR);
			
			// since we have at least one log, we'll have at least one count.
			count.add(new HourlyAggregateCount(d));
			
			// Iterate through the remainder of the logs and aggregate
			for (int i = 1; i < logs.size(); i++) {
				calendar.setTime(logs.get(i).getDate());
				int curHour = calendar.get(Calendar.HOUR_OF_DAY);
				int curDay = calendar.get(Calendar.DAY_OF_YEAR);
				int curYear = calendar.get(Calendar.YEAR);
				
				// If we're on the same hour, we want to increment the last count in the list.
				if (curHour == prevHour && curDay == prevDay && curYear == prevYear) {
					count.get(count.size() -1).increment();
				} else {
					count.add(new HourlyAggregateCount(logs.get(i).getDate()));
				}
				
				// the current is now the previous
				prevHour = curHour;
				prevDay = curDay;
				prevYear = curYear;
			}
			return count;
		}
		
		
	}
	
	public static ArrayList<TimeAggregateCount> getByDays(ArrayList<Log> logs) {
		if (logs.size() == 0) {
			return new ArrayList<TimeAggregateCount>();
		} else {
			ArrayList<TimeAggregateCount> count = new ArrayList<TimeAggregateCount>();
			Date d = logs.get(0).getDate();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			int prevDay = calendar.get(Calendar.DAY_OF_YEAR);
			int prevYear = calendar.get(Calendar.YEAR);
			count.add(new DailyAggregateCount(d));
			
			for (int i = 1; i < logs.size(); i++) {
				calendar.setTime(logs.get(i).getDate());
				int curDay = calendar.get(Calendar.DAY_OF_YEAR);
				int curYear = calendar.get(Calendar.YEAR);
				
				if (curDay == prevDay && curYear == prevYear) {
					count.get(count.size() -1).increment();
				} else {
					count.add(new DailyAggregateCount(logs.get(i).getDate()));
				}
				
				prevDay = curDay;
				prevYear = curYear;
			}
			return count;
		}
	}
		
	public static ArrayList<TimeAggregateCount> getByWeeks(ArrayList<Log> logs) {
		if (logs.size() == 0) {
			return new ArrayList<TimeAggregateCount>();
		} else {
			ArrayList<TimeAggregateCount> count = new ArrayList<TimeAggregateCount>();
			Date d = logs.get(0).getDate();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			int prevWeek = calendar.get(Calendar.WEEK_OF_MONTH);
			int prevMonth = calendar.get(Calendar.MONTH);
			int prevYear = calendar.get(Calendar.YEAR);
			count.add(new WeeklyAggregateCount(d));
			
			for (int i = 1; i < logs.size(); i++) {
				calendar.setTime(logs.get(i).getDate());
				int curMonth = calendar.get(Calendar.MONTH);
				int curYear = calendar.get(Calendar.YEAR);
				int curWeek = calendar.get(Calendar.WEEK_OF_MONTH);
				
				if (curWeek == prevWeek && curMonth == prevMonth && curYear == prevYear) {
					count.get(count.size() -1).increment();
				} else {
					count.add(new WeeklyAggregateCount(logs.get(i).getDate()));
				}
				
				prevMonth = curMonth;
				prevYear = curYear;
				prevWeek = curWeek;
			}
			return count;
		}
		
	}
	
	public static ArrayList<TimeAggregateCount> getByMonths(ArrayList<Log> logs) {
		if (logs.size() == 0) {
			return new ArrayList<TimeAggregateCount>();
		} else {
			ArrayList<TimeAggregateCount> count = new ArrayList<TimeAggregateCount>();
			Date d = logs.get(0).getDate();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			int prevMonth = calendar.get(Calendar.MONTH);
			int prevYear = calendar.get(Calendar.YEAR);
			count.add(new MonthlyAggregateCount(d));
			
			for (int i = 1; i < logs.size(); i++) {
				calendar.setTime(logs.get(i).getDate());
				int curMonth = calendar.get(Calendar.MONTH);
				int curYear = calendar.get(Calendar.YEAR);
				
				if (curMonth == prevMonth && curYear == prevYear) {
					count.get(count.size() -1).increment();
				} else {
					count.add(new MonthlyAggregateCount(logs.get(i).getDate()));
				}
				
				prevMonth = curMonth;
				prevYear = curYear;
			}
			return count;
		}
		
	}
		
		
}

