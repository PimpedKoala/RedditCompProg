package com.RedditCompProg;

import java.util.ArrayList;

public class Competitor {
	
	private String username = "";
	private String comment = "";
	private ArrayList<String> events;
	private ArrayList<String> times;
	private int numEvents;
	private int points = 0;
	private boolean isXP = false;
	
	public Competitor(String text, ArrayList<String> myEvents) { //normal constructor
		comment = text;
		events = myEvents;
		numEvents = findNumberOfEvents(comment);
		username = findUser(comment);
		times = findAverages(comment);
	}
	
	public Competitor(String name, ArrayList<String> myEvents, ArrayList<String> myTimes) { //constructor for imports
		events = new ArrayList<String>();
		for(int a = 0; a < myEvents.size(); a++) {
			events.add(myEvents.get(a));
		}
		numEvents = myEvents.size();
		username = name;
		times = new ArrayList<String>();
		for(int a = 0; a < myTimes.size(); a++) {
			times.add(myTimes.get(a));
		}
	}
	
	public Competitor(String name, int points2) { //I think I used this for debugging a while back not sure
		comment = "";
		events = new ArrayList<String>();
		numEvents = 0;
		username = name;
		times = events = new ArrayList<String>();
		points = points2;
	}
	
	public String findUser(String s) { //takes first line of text - should have already been checked by process()
		String[] lines = s.split("\n");
		return lines[0];
	}
	
	public int findNumberOfEvents(String s) { //useless but for some reason I have and make use of it
		return events.size();
	}
	
	public String findNextTime(String s, int pos, String e) throws java.lang.NumberFormatException { 
		String time = s.substring(pos + e.length() + 1).trim();
		
		if(time.substring(0, time.indexOf(".") + 3).contains(":")) { //if the time is over a minute, convert to seconds
			try {
				int mins = Integer.parseInt(time.substring(0, time.indexOf(":")));
				int secs = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(".")));
				int dec = Integer.parseInt(time.substring(time.indexOf(".") + 1, time.indexOf(".") + 3));
							
				secs += mins * 60;
				if(dec < 10)
					return secs + ".0" + dec;
				else 
					return secs + "." + dec;
			}catch(NumberFormatException e1) {
				return "Error." + e1.getMessage(); //Should never happen because of testComment()
			}
		}
		else
			return time.substring(0, time.indexOf(".") + 3);	
	}
	
	public ArrayList<String> findAverages(String s) { //stores all the averages as Strings for easier manipulation
		ArrayList<String> times = new ArrayList<String>();
		int pos;
		for(int a = 0; a < numEvents; a++) {
			pos = s.indexOf(events.get(a) + ":");
			times.add(findNextTime(s, pos, events.get(a)));
		}
		return times;
	}
	
	public void addPoints(int a) {
		points += a;
	}
	
	public void setPoints(int a) {
		points = a;
	}
	
	public int getPoints() {
		return points;
	}
	
	public String getName() {
		return username;
	}
	
	public ArrayList<String> getEvents() {
		return events;
	}
	
	public ArrayList<String> getTimes() {
		return times;
	}
	
	public int getNumTimes() {
		return numEvents;
	}
	
	public void isXP() {
		isXP = true;
	}
	
	public boolean getIsXP() { //is their name in totalScores.dat?
		return isXP;
	}
	
	public String toString() {
		return "Username" + username + "Events" + events + "Times" + times + "Number of events" + numEvents;
	}
}














































