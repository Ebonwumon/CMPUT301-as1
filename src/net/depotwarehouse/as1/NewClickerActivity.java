package net.depotwarehouse.as1;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.model.Clicker;
import net.depotwarehouse.as1.model.File;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewClickerActivity extends Activity {
	
	private TextView nameText;
	private Button createButton;
	public static ClickerController clickerController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_clicker);
		setupActionBar();
		
		String loadedData = "";
		try {
			loadedData = File.readString(openFileInput("clickers.json"));
		} catch (IOException e) {
			System.err.println("error loading from file");
		}
		clickerController = new ClickerController(loadedData);
		
		nameText = (EditText)findViewById(R.id.new_clicker_name);
		createButton = (Button)findViewById(R.id.create_button);
		
		createButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String name = nameText.getText().toString();
				Clicker clicker = new Clicker(name);
				clickerController.add(clicker);
				try {
					File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
				} catch (FileNotFoundException e) {
					System.err.println("file not found for writing");
				}
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_clicker, menu);
		return true;
	}
	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

}
