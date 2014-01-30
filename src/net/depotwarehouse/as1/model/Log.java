package net.depotwarehouse.as1.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Log {
	
	private Date date;
	private String ClickerID;
	
	public Log(String clickerID) {
		ClickerID = clickerID;
		date = new Date();
	}
	
	
	public static ArrayList<Log> LogFactory(String args) {
		ArrayList<Log> arr = new ArrayList<Log>();
		if (args == null || args.isEmpty()) {
			return arr;
		}
		Gson gson = new Gson();
		Type typeOfT = new TypeToken<ArrayList<Log>>(){}.getType();
		arr = gson.fromJson(args, typeOfT);
		return arr;	
	}
	

}
