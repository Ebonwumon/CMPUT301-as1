package net.depotwarehouse.as1.model;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Clicker {
	
	private int Count;
	private String Name;
	
	
	public Clicker(String Name) {
		this.Name = Name;
	}
	
	public Clicker (String Name, int Count) {
		this.Name = Name;
		this.Count = Count;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public int getCount() {
		return this.Count;
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

}
