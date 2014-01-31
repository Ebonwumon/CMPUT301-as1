package net.depotwarehouse.as1;

import java.io.IOException;
import java.util.ArrayList;

import net.depotwarehouse.as1.adapter.ClickerListAdapter;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.controller.LogController;
import net.depotwarehouse.as1.model.AggregateCounts;
import net.depotwarehouse.as1.model.Clicker;
import net.depotwarehouse.as1.model.File;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ClickerOverviewActivity extends Activity {
	private ClickerController clickerController;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_overview);
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("clickers.json"));
		} catch (IOException e) {
			System.err.println("error loading from file");
		}
		clickerController = new ClickerController(loadedData);
		
		list = (ListView) findViewById(R.id.clicker_list);
		
		
		
		ClickerListAdapter adapter = new ClickerListAdapter(this, clickerController.sortByCount());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    viewStats(position);
			}
		});
		
	}
	
	public void viewStats(int position) {
		Intent intent = new Intent(this, ClickerStatsActivity.class);
		intent.putExtra("id", clickerController.get(position).getId());
		startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_overview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
