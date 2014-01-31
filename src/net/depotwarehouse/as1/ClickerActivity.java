package net.depotwarehouse.as1;

import java.io.FileNotFoundException;
import java.io.IOException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.controller.LogController;
import net.depotwarehouse.as1.model.File;
/**
 * The primary activity of the application. This activity has two primary tasks:
 *  1) Allow the user to navigate between and increment existing clickers
 *  2) Serve as a landing page so that the user can explore the application further using the menu.
 * @author tpavlek
 *
 */
public class ClickerActivity extends Activity {
	
	// our controllers
	private ClickerController clickerController;
	private LogController logController;
	
	// our widgets
	private TextView counterName;
	private TextView count;
	private Button incButton;
	private Button nextButton;
	private Button prevButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker);
		
		
		
		// instantiation of active view items
		counterName = (TextView) findViewById(R.id.counter_name);
		count = (TextView) findViewById(R.id.counter_count);
		incButton = (Button) findViewById(R.id.increment);
		nextButton = (Button) findViewById(R.id.next_button);
		prevButton = (Button) findViewById(R.id.prev_button);
		
		incButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				increment();
			}
		});
	}
	
	/**
	 * OnResume we will completely reload the controllers from disk.
	 * This helps in the case where we just came from the newclicker activity,
	 * and added a new clicker serialized to disk.
	 */
	protected void onResume() {
		super.onResume();
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("log.json"));
		} catch (IOException e) {
			System.err.println("Error loading log file");
		}
		logController = new LogController(loadedData);
		
		loadedData = "";
		try {
			loadedData = File.readString(openFileInput("clickers.json"));
		} catch (IOException e) {
			System.err.println("error loading clickers file");
		}
		clickerController = new ClickerController(loadedData);
		
		// insantiate the views.
		refreshClicker();
	}
	
	/**
	 * refreshClicker() refreshes the view to reflect most up-to-date clicker data.
	 * Must be called after clickercontroller is initialized.
	 */
	public void refreshClicker() {
		// There is at least one clicker in the controller, we want to render it onscreen.
		if (clickerController.any()) {
			counterName.setText(clickerController.current().getName());
			count.setText(String.valueOf(clickerController.current().getCount()));
			incButton.setEnabled(true);
		// otherwise, we need a placeholder text and disable the buttons.
		} else {
			counterName.setText("No Counters");
			count.setText("");
			incButton.setEnabled(false);
		}
		
		// We can only navigate between clickers if there are more of them
		nextButton.setEnabled(clickerController.hasNext());
		prevButton.setEnabled(clickerController.hasPrevious());
	}
	
	public void increment() {
		/* if we don't have anything in the clickercontroller, attempting to increment would be
		 * at best wasteful, and at worst, application-breaking. The button _should_ be disabled anyway
		 * but I'm not going to be making assumptions about the client's implementation of android's widgets.
		 */
		if (clickerController.any()) {
			clickerController.current().increment();
			logController.logClick(clickerController.current().getId());
			try {
				// We need to commit immediately
				File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
				File.writeString(openFileOutput("log.json", MODE_PRIVATE), logController.toJSON());
			} catch (FileNotFoundException e) {
				System.err.println("error writing file - clicker not saved");
			}
			// refresh the view, our clicker has changed.
			refreshClicker();
		}
	}
	
	// bound to the next button on screen. View the next clicker in the controller
	public void next(View v) {
		try {
			clickerController.next();
			refreshClicker();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Next Clicker out of bounds: " + e);
		}
	}
	
	// bound to the previous button on screen. View the previous clicker in the controller
	public void prev(View v) {
		try {
			clickerController.prev();
			refreshClicker();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Prev Clicker out of bounds: " + e);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.new_counter:
	        	intent = new Intent(this, NewClickerActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.edit_counter:
	        	// If there are no clickers in the controller we have nothing to edit
	        	if (!clickerController.any()) { return true; }
	        	intent = new Intent(this, EditClickerActivity.class);
	        	// We'll need to know which counter to edit
	        	intent.putExtra("id", clickerController.current().getId());
	        	startActivity(intent);
	        	return true;
	        case R.id.list_counters:
	        	intent = new Intent(this, ClickerOverviewActivity.class);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
