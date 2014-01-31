package net.depotwarehouse.as1.model;

import java.util.Date;

/**
 * Superclass for aggregates. Contains all the behaviour of all aggregates, Hourly, Daily, Monthly and Weekly
 * all simply override toString to more accurately represent the object's identity.
 * @author tpavlek
 *
 */
public class TimeAggregateCount {
	// private properties
	private Date date;
	private int count;
	
	// constructor for new Count
	public TimeAggregateCount(Date d) {
		this.date = d;
		this.count = 1;
	}
	
	// Increment the count by one.
	// This will be called when we find a log that has the same ID, but different date ranges.
	public void increment() {
		this.count++;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getCount() {
		return this.count;
	}

}
