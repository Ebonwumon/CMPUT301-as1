package net.depotwarehouse.as1.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

import net.depotwarehouse.as1.model.Log;

public class LogController {
	
	private ArrayList<net.depotwarehouse.as1.model.Log> logs;
	
	/**
	 * Stores the deserialized verson of the json of the logs.
	 * @param json : the json string from disk
	 */
	public LogController(String json) {
		logs = Log.LogFactory(json);
	}
	
	/**
	 * Returns an ArrayList of Logs that all have the same clicker id. Clicker ID is passed as a string
	 * @param id : the clicker ID
	 * @return ArrayList<Log>
	 */
	public ArrayList<Log> getById(String id) {
		ArrayList<Log> ret = new ArrayList<Log>();
		for (Log l : logs) {
			if (l.getID().equals(id)) {
				ret.add(l);
			}
		}	
		return ret;
	}
	
	/**
	 * Iterate through all the logs and remove those with matching IDs.
	 * We have to use an iterator because removing items from the List in a 
	 * foreach would be bad mojo
	 */
	public void removeAllById(String id) {
		Iterator<Log> i = logs.iterator();
		while (i.hasNext()) {
		   Log l = i.next();
		   if (l.getID().equals(id)) {
			   i.remove();
		   }
		}
	}
	
	/**
	 * Records a log that a click happened. Takes a clicker ID and will associate it with a date
	 */
	public void logClick(String clickerID) {
		logs.add(new Log(clickerID));
	}
	
	/**
	 * Serialize to JSON. Saves to disk must be done externally.
	 */
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(logs);
	}

}
