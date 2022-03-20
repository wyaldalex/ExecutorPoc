package com.tudux.threading.Lecture1.callables;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CODSDAOImpl implements Callable<LinkedList<String>> {
	private LinkedList<String> events;

	
	public LinkedList<String> getEvents() {
		return events;
	}


	public void setEvents(LinkedList<String> events) {
		this.events = events;
	}
	
	

	public CODSDAOImpl(LinkedList<String> events) {
		super();
		this.events = events;
	}


	public LinkedList<String> call() throws Exception {
		
		LinkedList<String> modevents = new LinkedList<String>();
		TimeUnit.MILLISECONDS.sleep(800);
		for (String x : this.events) {
			System.out.println("Processing");
			modevents.add(x.concat("Modified String"));
		}
    	
    	return modevents;

    }
}