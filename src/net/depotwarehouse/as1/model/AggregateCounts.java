package net.depotwarehouse.as1.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AggregateCounts {
	
	protected ArrayList<Log> logs;
	
	public AggregateCounts(ArrayList<Log> logs) {
		this.logs = logs;
	}

	public static ArrayList<TimeAggregateCount> getByHours(ArrayList<Log> logs) {
		if (logs.size() == 0) {
			return new ArrayList<TimeAggregateCount>();
		} else {
			ArrayList<TimeAggregateCount> count = new ArrayList<TimeAggregateCount>();
			Date d = logs.get(0).getDate();
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(d);
			int prevHour = calendar.get(Calendar.HOUR_OF_DAY);
			int prevDay = calendar.get(Calendar.DAY_OF_YEAR);
			int prevYear = calendar.get(Calendar.YEAR);
			count.add(new HourlyAggregateCount(d));
			
			for (int i = 1; i < logs.size(); i++) {
				calendar.setTime(logs.get(i).getDate());
				int curHour = calendar.get(Calendar.HOUR_OF_DAY);
				int curDay = calendar.get(Calendar.DAY_OF_YEAR);
				int curYear = calendar.get(Calendar.YEAR);
				
				if (curHour == prevHour && curDay == prevDay && curYear == prevYear) {
					count.get(count.size() -1).increment();
				} else {
					count.add(new HourlyAggregateCount(logs.get(i).getDate()));
				}
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
			}
			return count;
		}
		
	}
		
		
}

