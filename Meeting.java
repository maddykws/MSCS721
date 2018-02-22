/**
 * This is Madhav Venigalla software program
 */

package com.marist.mscs721;

import java.sql.Timestamp;
/**
 * This contains the author information for the Project
 * @author maddy
 *
 */
public class Meeting {
	
	private Timestamp startTime = null;
	private Timestamp stopTime = null;
	private String subject = null;
/**
 * This loads the constructor for the program
 * @param newStartTime
 * @param newStopTime
 * @param newSubject
 */
	
	public Meeting(Timestamp newStartTime, Timestamp newEndTime, String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		if (newSubject.isEmpty()) {
			setSubject("");
		}
		else {
			setSubject(newSubject);
		}
	}
/**
 * JSON string for the meeting. 
 */
	public Meeting (String data)
	{
		data = data.substring(data.indexOf("startTime"));
		String[] datapieces = data.split(",");
		setStartTime(parseTime(datapieces[0].substring(10)));
		setStopTime(parseTime(datapieces[1].substring(10)));
		setSubject(datapieces[2].substring(9));
	}
	/**
	 * Function to return the Json string has been written below. 
	 * returns the string of a meeting
	 */
	public String toString() {
		return this.getStartTime().toString() + " - " + this.getStopTime() + ": " + getSubject();
	}
	
	/**
	 * Function to state the starttime of a meeting
	 * @return
	 */
	public Timestamp getStartTime() {
		return startTime;
	}
/**
 * Function to state the end time of a meeting
 * @param startTime
 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
/**
 * Function to handle the stop time of a meeting
 * @return
 */
	public Timestamp getStopTime() {
		return stopTime;
	}
/**
 * Function to handle the stop time of a meeting
 * @param stopTime
 */
	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}
/**
 * Function to get the subject of a meeting
 * @return
 */
	public String getSubject() {
		return subject;
	}
/**
 * Function to set the subject of a meeting
 * @param subject
 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

/**
 * Function to parse time that is string Data
 * @param data
 * @return
 */
public Timestamp parseTime(String data)
{
	return Timestamp.valueOf(data);
}
}
  
