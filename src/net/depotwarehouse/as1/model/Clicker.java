package net.depotwarehouse.as1.model;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Model that represents common Clicker behaviour
 * @author tpavlek
 *
 */
public class Clicker implements Comparable<Clicker> {
	
	// private properties
	private int Count;
	private String Name;
	private String Id;
	
	// Constructor for new clicker
	public Clicker(String Name) {
		this.Name = Name;
		this.Count = 0;
		// Our ID needs to be both random, and unique. That's the contract given by UUID - I hope it didn't lie.
		this.Id = UUID.randomUUID().toString();
	}
	
	// If we want to instantiate an existing clicker instead of a new one.
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
	
	// increment count by one.
	public void increment() {
		this.Count++;
	}
	
	// serialize clicker to JSON string
	public String toJSON() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
	/**
	 * This class handles the generation of clickers, so it recieves a JSON string after being read from disk and
	 * generates clickers.
	 * @param args : JSON string representing an ArrayList of Clickers
	 * @return Deserialized ArrayList of Clickers
	 */
	public static ArrayList<Clicker> ClickerFactory(String args) {
		ArrayList<Clicker> arr = new ArrayList<Clicker>();
		// If the string passed is empty, we're done, return.
		if (args == null || args.isEmpty()) {
			return arr;
		}
		Gson gson = new Gson();
		Type typeOfT = new TypeToken<ArrayList<Clicker>>(){}.getType();
		arr = gson.fromJson(args, typeOfT);
		return arr;	
	}
	
	/**
	 * Comparable override - clickers are compared based on their Count property.
	 */
	@Override
	public int compareTo(Clicker arg0) {
		return this.Count - arg0.getCount();
	}
	
	// toString: ClickerName: Count
	public String toString() {
		return this.Name + ": " + String.valueOf(this.Count);
	}

}
