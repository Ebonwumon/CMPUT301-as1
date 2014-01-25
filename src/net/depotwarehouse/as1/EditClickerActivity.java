package net.depotwarehouse.as1;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.model.Clicker;
import net.depotwarehouse.as1.model.File;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditClickerActivity extends Activity {
	
	private EditText clickerName;
	private EditText clickerCount;
	private Button saveButton;
	private Button deleteButton;
	private ClickerController clickerController;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_clicker);
		
		clickerName = (EditText) findViewById(R.id.clicker_name);
		clickerCount = (EditText) findViewById(R.id.clicker_count);
		
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
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
		
		// if the ID was passed wrong, we're in an unsalvageable state. Get out.
		try {
			clickerController.findById(id);
		} catch (NotFoundException e) {
			System.err.println("Error loading edit page " + e);
			finish();
		}
		
		clickerName.setText(clickerController.current().getName());
		clickerCount.setText(String.valueOf(clickerController.current().getCount()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_clicker, menu);
		return true;
	}
	
	public void save(View v) {
		clickerController.current().setName(clickerName.getText().toString());
		clickerController.current().setCount(Integer.parseInt(clickerCount.getText().toString()));
		try {
			File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
		} catch (FileNotFoundException e) {
			System.err.println("Could not save clicker edits " + e);
		}
		finish();
	}
	
	public void delete(View v) {
		clickerController.remove();
		try {
			File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
		} catch (FileNotFoundException e) {
			System.err.println("Could not delete clicker " + e);
		}
		finish();
	}

}
