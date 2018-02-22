/**
 * room.java
 * Author Madhav
 * Assignment 1 
 * 
 */
package com.marist.mscs721;

import java.util.ArrayList;

public class Room {	
	
	private String name;
	private int capacity;
	private String building;
	@SuppressWarnings("unused")
	private String location;
	private ArrayList<Meeting> meetings;
	/**
	 * Constructor Loading Function
	 * @param newName
	 * @param newCapacity
	 */
	
	public Room(String newName, String newbuilding, String newlocation, int newCapacity) {
		setName(newName);
		setCapacity(newCapacity);
		setBuilding(newbuilding);
		setLocation(newlocation);
		setMeetings(new ArrayList<Meeting>());
	}
	/**
	 * Constructor overloading with string
	 * @param capacity2 
	 * @param newMeeting
	 */
	public Room(String data, int capacity2)
	{
		/**
		 * This Integer declares the name of the person and their characters
		 */
		int person = data.indexOf("Name") + 4;
/**
 * Sets the name of the person and index of the data
 */
		setName(data.substring(person + 1, data.indexOf(",")));
		/**
		 * Adds the Data to the Substring
		 */
		data = data.substring(data.indexOf(",")+ 1, data.length());
		int capacity = data.indexOf("capacity") + 8;
		setCapacity((int)Double.parseDouble(data.substring(capacity + 1, data.indexOf(","))));
		data = data.substring(data.indexOf(",")+ 1, data.length());
		int personmeeting = data.indexOf("meetings") + 8;
		data = data.substring(0, data.length() - 1);
		if(data.length() > 14)
		{
			String[] meetingStrings = data.substring(personmeeting + 3, data.length() - 1).split("}");
			ArrayList<Meeting> meetingroom = new ArrayList<Meeting>();
			for(int i = 0; i < meetingStrings.length; i++)
			{
				meetingroom.add(new Meeting(meetingStrings[i]));
			}
			setMeetings(meetingroom);
		}
		else
		{
			setMeetings(new ArrayList<Meeting>());
		}
	}

/**
 * 	Function to add meeting to the new name
 * @param newMeeting
 */
	
	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}
/**
 * Function to get the New Name
 * @return
 */
	public String getName() {
		return name;
	}
/**
 * Function to set name
 * @param name
 */

	public void setName(String name) {
		this.name = name;
	}
/**
 * Function to set the capacity
 * @return
 */
	public int getCapacity() {
		return capacity;
	}
/**
 * Function to get the save and get the capacity of the meeting
 * @param capacity
 */

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

/**
 * Function that lists the array for the meetings
 * @return
 */
	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}

/**
 * Function that sets the meetings
 * @param meetings
 */
	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}
	
	public void setBuilding(String building){
		this.building = building;
	}
	
	public String getBuilding(){
		return building; 
	}
	
	public void setLocation(String location){
		this.location = location;
	}
}

