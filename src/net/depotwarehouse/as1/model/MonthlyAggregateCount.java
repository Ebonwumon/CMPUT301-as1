package net.depotwarehouse.as1.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MonthlyAggregateCount extends TimeAggregateCount {

	public MonthlyAggregateCount(Date d) {
		super(d);
	}
	
	public String toString() {
		return "Month of - " + new SimpleDateFormat("MMM dd").format(super.getDate()) + ": " + super.getCount();
	}

}
