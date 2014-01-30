package net.depotwarehouse.as1.model;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Clicker implements Comparable {
	
	private int Count;
	private String Name;
	private String Id;
	
	
	public Clicker(String Name) {
		this.Name = Name;
		this.Count = 0;
		this.Id = UUID.randomUUID().toString();
	}
	
	public Clicker (String Name, int Count, String Id) {
		this.Name = Name;
		this.Count = Count;
		this.Id = Id;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public String getId() {
		return this.Id;
	}
	
	public int getCount() {
		return this.Count;
	}
	
	public void setCount(int count) {
		this.Count = count;
	}
	
	public void increment() {
		this.Count++;
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
	public static ArrayList<Clicker> ClickerFactory(String args) {
		ArrayList<Clicker> arr = new ArrayList<Clicker>();
		if (args == null || args.isEmpty()) {
			return arr;
		}
		Gson gson = new Gson();
		Type typeOfT = new TypeToken<ArrayList<Clicker>>(){}.getType();
		arr = gson.fromJson(args, typeOfT);
		return arr;	
	}

	@Override
	public int compareTo(Object arg0) {
		Clicker c = (Clicker) arg0;
		return this.Count - c.getCount();
	}
	
	public String toString() {
		return this.Name + ": " + String.valueOf(this.Count);
	}

}
