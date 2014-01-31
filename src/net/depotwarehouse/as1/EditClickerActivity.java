package net.depotwarehouse.as1;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.controller.LogController;
import net.depotwarehouse.as1.model.File;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity allows the user to edit a clicker.
 * 
 * The options available to the user are changing a clicker's name, zeroing a clicker, or deleting a clicker.
 * 
 * Note, all these activites are exclusive from each other, zeroing a clicker or deleting a clicker will end the
 * activity, as will saving an edit. If a user wants to edit a clicker's name, then zero it, he will need to call
 * the activity twice (though why wouldn't he just delete it and make a new one?)
 * 
 * parent of this activity is the main one, clicking in the upper left will bring the user there
 * @author tpavlek
 *
 */
public class EditClickerActivity extends Activity {
	// controllers
	private ClickerController clickerController;
	private LogController logController;
	
	// widgets
	private EditText clickerName;
	private TextView clickerCount;
	
	// id of clicker we're editing
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_clicker);
		setupActionBar();
		
		clickerName = (EditText) findViewById(R.id.clicker_name);
		clickerCount = (TextView) findViewById(R.id.clicker_count);
		
		// We need to get the id of the clicker that the user wants to edit.
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// load our data and then put it in the controller.
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("clickers.json"));
		} catch (IOException e) {
			System.err.println("error loading from file");
		}
		clickerController = new ClickerController(loadedData);
		
		loadedData = "";
		try {
			loadedData = File.readString(openFileInput("log.json"));
		} catch (IOException e) {
			System.err.println("Error loading log file");
		}
		logController = new LogController(loadedData);
		
		// if the ID was passed wrong, we're in an unsalvageable state. Get out.
		try {
			clickerController.findById(id);
		} catch (NotFoundException e) {
			System.err.println("Error loading edit page " + e);
			finish();
		}
		
		// Set the values of the widgets to represent the clicker data
		clickerName.setText(clickerController.current().getName());
		clickerCount.setText(String.valueOf(clickerController.current().getCount()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_clicker, menu);
		return true;
	}
	
	// zero the counter and save to disk.
	public void zero(View v) {
		clickerController.current().setCount(0);
		save();
	}
	
	// rename the counter and save to disk
	public void rename(View v) {
		clickerController.current().setName(clickerName.getText().toString());
		save();
	}
	
	// delete the counter and save to disk
	public void delete(View v) {
		clickerController.remove();
		logController.removeAllById(id);
		save();
	}
	
	// save to disk
	public void save() {
		try {
			File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
			File.writeString(openFileOutput("log.json", MODE_PRIVATE), logController.toJSON());
		} catch (FileNotFoundException e) {
			System.err.println("Could not save clicker edits/removals " + e);
		}
		finish();
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	


}
