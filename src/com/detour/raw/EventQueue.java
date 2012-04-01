package com.detour.raw;

import java.util.ArrayList;

public class EventQueue {
	//Supposedly, I'm going to implement the Command design pattern to better manage input.
	
	private static EventQueue mEventQueue = new EventQueue();
	
	private ArrayList<Event> mQueue = new ArrayList<Event>();
	
	private EventQueue(){
		
	}
	
	public static EventQueue getEventQueue(){ //TODO synchronized?
		return mEventQueue;
	}
	
	public void queue(Event event){
		mQueue.add(event);
	}
	
	public Event getAndRemoveNextEvent(){
		return mQueue.remove(0);
	}
	
}
