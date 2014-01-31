package net.depotwarehouse.as1;

import java.io.IOException;
import java.util.ArrayList;

import net.depotwarehouse.as1.adapter.AggregateListAdapter;
import net.depotwarehouse.as1.adapter.ClickerListAdapter;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.controller.LogController;
import net.depotwarehouse.as1.model.AggregateCounts;
import net.depotwarehouse.as1.model.File;
import net.depotwarehouse.as1.model.Log;
import net.depotwarehouse.as1.model.TimeAggregateCount;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class ClickerStatsActivity extends Activity {
	private LogController logController;
	private String id;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_stats);
		setupActionBar();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		list = (ListView)findViewById(R.id.stats_list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("log.json"));
		} catch (IOException e) {
			System.err.println("error loading from file");
		}
		logController = new LogController(loadedData);
		
		// we instantiate a list of aggregate counters, and add all the corresponding aggregates to a
		// master list. We'll render that.
		ArrayList<TimeAggregateCount> agglist = new ArrayList<TimeAggregateCount>();
		ArrayList<Log> logById = logController.getById(id);
		agglist.addAll(AggregateCounts.getByHours(logById));
		agglist.addAll(AggregateCounts.getByDays(logById));
		agglist.addAll(AggregateCounts.getByWeeks(logById));
		agglist.addAll(AggregateCounts.getByMonths(logById));
					
		AggregateListAdapter adapter = new AggregateListAdapter(this, agglist);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_stats, menu);
		return true;
	}
	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

}
