package net.depotwarehouse.as1.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Aggregate count for Daily Counts.
 * @author tpavlek
 *
 */
public class DailyAggregateCount extends TimeAggregateCount {
	
	public DailyAggregateCount(Date d) {
		super(d);
	}
	
	public String toString() {
		return new SimpleDateFormat("MMM dd").format(super.getDate()) + ": " + super.getCount();
	}

}
