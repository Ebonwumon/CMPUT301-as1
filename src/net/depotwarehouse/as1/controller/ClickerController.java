package net.depotwarehouse.as1.controller;

import java.util.ArrayList;

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
	
	public Clicker current() {
		return clickers.get(current);
	}
	
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
	
	public boolean hasNext() {
		return (current < (clickers.size() - 1));
	}
	
	public boolean hasPrevious() {
		return (current > 0 && current < clickers.size());
	}
	
	public Clicker prev() throws IndexOutOfBoundsException {
		if (this.hasPrevious()) {
			current -= 1;
			return clickers.get(current);
		}
		throw new IndexOutOfBoundsException("Prev Clicker out of bounds: current is: " + String.valueOf(current) +
				" size is " + clickers.size());
	}
	
	public boolean any() {
		if (clickers.size() > 0) return true;
		else return false;
	}
	
	public void add(Clicker clicker) {
		clickers.add(clicker);
	}
	
	public void remove() {
		clickers.remove(current);
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(clickers);
	}
	
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
