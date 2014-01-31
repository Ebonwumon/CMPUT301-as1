package net.depotwarehouse.as1.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeeklyAggregateCount extends TimeAggregateCount {

	public WeeklyAggregateCount(Date d) {
		super(d);
	}
	
	public String toString() {
		return "Week of - " + new SimpleDateFormat("MMM dd").format(super.getDate()) + ": " + super.getCount();
	}

}
