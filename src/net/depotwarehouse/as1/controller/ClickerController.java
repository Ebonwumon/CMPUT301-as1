package net.depotwarehouse.as1.controller;

import java.util.ArrayList;
import java.util.Collections;

import android.content.res.Resources.NotFoundException;

import com.google.gson.Gson;

import net.depotwarehouse.as1.model.Clicker;

public class ClickerController {
	
	private ArrayList<Clicker> clickers;
	private int current;
	
	public ClickerController(String json) {
		clickers = Clicker.ClickerFactory(json);
		current = 0;
	}
	
	// current clicker according to pointer
	public Clicker current() {
		return clickers.get(current);
	}
	
	// next clicker according to pointer.
	public Clicker next() throws IndexOutOfBoundsException {
		if (this.hasNext()) {
			current += 1;
			return clickers.get(current);
		}
		throw new IndexOutOfBoundsException(
				"Next Clicker out of bounds: current is: " + String.valueOf(current) +
				" size is " + clickers.size()
				);
	}
	
	
	// check if there is another clicker after this one.
	public boolean hasNext() {
		return (current < (clickers.size() - 1));
	}
	
	
	// check if there is a previous clicker before this one
	public boolean hasPrevious() {
		return (current > 0 && current < clickers.size());
	}
	
	// returns previous clicker.
	public Clicker prev() throws IndexOutOfBoundsException {
		if (this.hasPrevious()) {
			current -= 1;
			return clickers.get(current);
		}
		throw new IndexOutOfBoundsException("Prev Clicker out of bounds: current is: " + String.valueOf(current) +
				" size is " + clickers.size());
	}
	
	// checks if there are any clickers in the controller
	public boolean any() {
		if (clickers.size() > 0) return true;
		else return false;
	}
	
	// add clicker to controller
	public void add(Clicker clicker) {
		clickers.add(clicker);
	}
	
	// remove clicker from controller
	public void remove() {
		clickers.remove(current);
	}
	
	/**
	 * Gets the clicker at position and returns it to a user. This is only used when the user knows for certain
	 * that there is a clicker at that position (eg, position zero when the user has called .any()). Only for niche
	 * uses, otherwise use the safer .next(), .prev() with .hasNext() and .hasPrevious(), etc.
	 * @param position : index of clicker to retrieve
	 * @return Clicker at position.
	 */
	public Clicker get(int position) {
		return clickers.get(position);
	}
	
	/**
	 * Sort in place, by size, descending order. This alters the order of the internally stored clickers after the call.
	 * @return
	 */
	public ArrayList<Clicker> sortByCount() {
		Collections.sort(clickers);
		Collections.reverse(clickers);
		return clickers;
	}
	
	// Serialize the controller
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(clickers);
	}
	
	// Get clicker with a particular ID
	public Clicker findById(String id) throws NotFoundException {
		int i = 0;
		for (Clicker c : clickers) {
			if (c.getId().equals(id)) {
				current = i;
				return c;
			}
			i++;
		}
		
		throw new NotFoundException("Could not find clicker with Id " + id);
	}

}
