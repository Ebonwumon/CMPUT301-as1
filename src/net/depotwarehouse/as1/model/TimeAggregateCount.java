package net.depotwarehouse.as1.model;

import java.util.Date;

public class TimeAggregateCount {
	
	private Date date;
	private int count;
	
	public TimeAggregateCount(Date d) {
		this.date = d;
		this.count = 1;
	}
	
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
