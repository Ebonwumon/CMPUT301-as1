package net.depotwarehouse.as1.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Model class representing Log Behaviour.
 * @author tpavlek
 *
 */
public class Log {
	
	// private properties
	private Date date;
	private String ClickerID;
	
	// constructor for new Log
	public Log(String clickerID) {
		ClickerID = clickerID;
		date = new Date();
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getID() {
		return this.ClickerID;
	}
	
	/**
	 * Deserializes a JSON string into an arraylist of logs
	 * @param args : JSON string representing an arraylist of logs.
	 * @return : deserialized arraylist of logs
	 */
	public static ArrayList<Log> LogFactory(String args) {
		ArrayList<Log> arr = new ArrayList<Log>();
		// if input string is null or empty we're done.
		if (args == null || args.isEmpty()) {
			return arr;
		}
		Gson gson = new Gson();
		Type typeOfT = new TypeToken<ArrayList<Log>>(){}.getType();
		arr = gson.fromJson(args, typeOfT);
		return arr;	
	}
	

}
