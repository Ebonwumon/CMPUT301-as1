package net.depotwarehouse.as1.controller;

import java.util.ArrayList;

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
		throw new IndexOutOfBoundsException("You cannot access that!");
	}
	
	public boolean hasNext() {
		return (current < clickers.size() -1);
	}
	
	public boolean hasPrevious() {
		return (current - 1 > 0 && current < clickers.size());
	}
	
	public Clicker prev() throws IndexOutOfBoundsException {
		if (this.hasPrevious()) {
			current -= 1;
			return clickers.get(current);
		}
		throw new IndexOutOfBoundsException("You cannot access that!");
	}
	
	public boolean any() {
		if (clickers.size() > 0) return true;
		else return false;
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(clickers);
	}

}
