package net.depotwarehouse.as1;

import java.io.IOException;
import net.depotwarehouse.as1.adapter.ClickerListAdapter;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.model.File;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * ClickerOverViewActivity displays the list of clickers, sorted in decending order by their counts.
 * The user may tap on any of the list items and be brought to Aggregate statistics about the clicker.
 * 
 * The heirarchal parent is the main activity, so tapping in the top left will bring you there.
 * @author tpavlek
 *
 */
public class ClickerOverviewActivity extends Activity {
	// controllers
	private ClickerController clickerController;
	
	// widgets (only one!)
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_overview);
		// Show the Up button in the action bar.
		setupActionBar();
		
		list = (ListView) findViewById(R.id.clicker_list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("clickers.json"));
		} catch (IOException e) {
			System.err.println("error loading clickers from file");
		}
		clickerController = new ClickerController(loadedData);
		
		// We want to bind a modified ArrayAdapter to our list of Clickers
		ClickerListAdapter adapter = new ClickerListAdapter(this, clickerController.sortByCount());
		list.setAdapter(adapter);
		
		// If the user taps on any of the items in the list, we want to call viewStats, referencing the position
		// of the item
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    viewStats(position);
			}
		});
		
	}
	// Launches the statistics activity about a particular clicker
	public void viewStats(int position) {
		Intent intent = new Intent(this, ClickerStatsActivity.class);
		// Once we're over there, we'll need to know which clicker the user is interested in.
		// In this case, it is safe to call .get(position) because we *know* position exists, it was
		// just referenced in a 0-indexed list.
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

}
