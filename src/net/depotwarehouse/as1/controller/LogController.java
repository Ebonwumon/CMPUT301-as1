package net.depotwarehouse.as1.controller;

import java.util.ArrayList;

import com.google.gson.Gson;

import net.depotwarehouse.as1.model.Log;

public class LogController {
	
	private ArrayList<net.depotwarehouse.as1.model.Log> logs;
	
	public LogController(String json) {
		logs = Log.LogFactory(json);
	}
	
	public void logClick(String clickerID) {
		logs.add(new Log(clickerID));
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(logs);
	}

}
