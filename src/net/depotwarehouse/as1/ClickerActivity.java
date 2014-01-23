package net.depotwarehouse.as1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import net.depotwarehouse.as1.controller.ClickerController;
import net.depotwarehouse.as1.model.Clicker;

public class ClickerActivity extends Activity {
	
	public static ClickerController clickerController;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker);
		clickerController = new ClickerController(loadFromFile("clickers.json"));
		
		TextView counterName = (TextView) findViewById(R.id.counter_name);
		Button incButton = (Button) findViewById(R.id.increment);
		if (clickerController.any()) {
			Clicker clicker = clickerController.current();
			counterName.setText(clicker.getName());
		}
		
	}
	
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker, menu);
		return true;
	}
	
	private String loadFromFile(String filename) {
		FileInputStream fin;
		String ret = "";
		
		try {
			fin = openFileInput(filename);
			
			if ( fin != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(fin);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            fin.close();
	            ret = stringBuilder.toString();
			}
		} catch (Exception e) {
			System.err.println("error reading from file");
		}
		return ret;
	}
	
	private boolean saveToStorage() {
		FileOutputStream fout;
		
		try {
			fout = openFileOutput("clickers.json", Context.MODE_PRIVATE);
			fout.write(new Clicker("Bob", 9).toJSON().getBytes());
		} catch (Exception e) {
			System.err.println("Error writing file");
		}
		return true;
	}

}
