package net.depotwarehouse.as1.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HourlyAggregateCount extends TimeAggregateCount {
	
	public HourlyAggregateCount(Date d) {
		super(d);
	}

	public String toString() {
		return new SimpleDateFormat("MMM dd haaa").format(super.getDate()) + ": " + super.getCount();
	}
}
