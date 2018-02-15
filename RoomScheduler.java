package com.marist.mscs721;

/**
 * Scheduler class responsible for allocating amd managing rooms
 * This project is built on code from {@link https://github.com/gildmi/RoomScheduler.git.}
 * This is a part of assignment for class MSCS721-Software verification and maintainance
 * 
 * @author - Praneeth Manubolu
 * @date Feb 1st 2018
 **/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class RoomScheduler {
	protected static Scanner keyboard = new Scanner(System.in);
	static boolean valid = false;
	static ArrayList<Room> rooms = new ArrayList<>();

	/*
	 * Entry point loops through the menu each menu item is a feature of this
	 * application
	 * mainMenu() - handles input range to fall between 1 and 7
	 */
	public static void main(String[] args) {
		Boolean end = false;

		while (!end) {
			switch (mainMenu()) {

			case 1:
				System.out.println(addRoom(rooms));
				break;
			case 2:
				System.out.println(removeRoom(rooms));
				break;
			case 3:
				System.out.print(scheduleRoom(rooms));
				break;
			case 4:
				System.out.println(listSchedule(rooms));
				break;
			case 5:
				System.out.println(listRooms(rooms));
				break;
			case 6:
				System.out.println(importData(rooms));
				break;
			case 7:
				System.out.println(exportData(rooms));
				break;
			}

		}

	}

	/**
	 * Feature - Export all the stored data (rooms & schedule) to a json file
	 * Requires User to specify the destination path This method uses naming
	 * convention "info.json" to name the file
	 * 
	 * @param rooms
	 * @return
	 */
	private static String exportData(ArrayList<Room> rooms) {

		String path;

		System.out.println("Please enter path for backup(only location - Do not include file name)");
		path = validateInput("");

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(path + "/info.json");

			fileWriter.write(createJSON(rooms));
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			return "falied to save file to given location, Please try later";

		}

		return "success";
	}

	/**
	 * Helper method for exportData(ArrayList<Room> rooms), responsible for
	 * generating a JSONString out of the Rooms list
	 * 
	 * @param rooms
	 * @return
	 */
	private static String createJSON(ArrayList<Room> rooms) {

		Gson gson = new Gson();
		return gson.toJson(rooms);

	}

	/**
	 * Take Location of the backup file and restores all the data from the file
	 * Warning - this feature uses -processData(String str, ArrayList<Room> rooms)
	 * will delete existing data and overides with the new data.
	 * 
	 * @param rooms
	 * @return
	 */
	private static String importData(ArrayList<Room> rooms) {
		System.err.println("importing will erase stored data");
		System.out.println("Please enter path for export(only location - Do not include file name)");
		String path = validateInput("");
		try {
			File file = new File(path);
			if (file.length() > 32000)
				return "File too large to process";
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String str = new String(data, "UTF-8");
			processData(str, rooms);
			System.out.println("file size " + file.length());

		} catch (FileNotFoundException e) {

			return "failed to import";
		} catch (IOException e) {
			e.printStackTrace();
			return "failed to import";
		}
		return null;
	}

	/**
	 * Helper function for importData(ArrayList<Room> rooms), Triggered when the
	 * file is available
	 * 
	 * @param str
	 * @param rooms
	 */
	private static void processData(String str, ArrayList<Room> rooms) {
		ArrayList<Room> newRooms = new ArrayList<>();
		Type listType = new TypeToken<ArrayList<Room>>() {
		}.getType();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		newRooms = gson.fromJson(str, listType);
		rooms.clear();
		for (Room room : newRooms) {
			System.out.println(room.getName());
			rooms.add(room);
		}

	}

	/**
	 * Feature that displays the schedule for a particular room
	 * 
	 * @param roomList
	 * @return
	 */
	protected static String listSchedule(ArrayList<Room> roomList) {
		String roomName = getRoomName();
		System.out.println(roomName + " Schedule");
		System.out.println("---------------------");
		Room room = getRoomFromName(roomList, roomName);
		if (null == room)
			return "could not find room " + roomName;
		displayMeeting(room);
		return "";
	}

	/**
	 * display schedule for a room
	 */
	private static void displayMeeting(Room room) {
		for (Meeting m : room.getMeetings()) {
			System.out.println(m.toString());
		}
	}

	/**
	 * Responsible for displaying and retrieving the function that has to be called
	 * 
	 * @return
	 */
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("  6 - Import data");
		System.out.println("  7 - Export data");
		System.out.println("Enter your selection: ");
		int result = 0;
		// Loop runs till a valid input is provided
		do {
			valid = true;
			result = validateInput(1);
			if (result < 1 || result > 7) {
				System.out.println("Please enter a valid input");
				valid = false;
			}
		} while (!valid);
		return result;
	}

	/**
	 * Add room functionality
	 * 
	 * @param roomList
	 * @return
	 */
	protected static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name;
		// Loop runds till valid input
		// Validation - room with same name does not exit
		do {
			valid = true;
			name = getRoomName();
			for (Room room : roomList) {
				if (room.getName().equalsIgnoreCase(name))
					valid = false;
			}
			if (!valid)
				System.out.println("Room name already taken, Please enter a different name");
		} while (!valid);
		System.out.println("Room capacity?");
		int capacity = validateInput(1);
		while (capacity < 1) {
			System.out.println("room capacity should be greater than 0");
			capacity = validateInput(1);
		}

		Room newRoom = new Room(name, capacity);
		roomList.add(newRoom);

		return "Room '" + newRoom.getName() + "' added successfully!";
	}

	/**
	 * Removes a room if present
	 * 
	 * @param roomList
	 * @return
	 */
	protected static String removeRoom(ArrayList<Room> roomList) {
		System.out.println("Remove a room:");
		int index = findRoomIndex(roomList, getRoomName());
		// index -1 means that the room is not present
		if (index == -1)
			return "Room could not be found";
		roomList.remove(index);
		return "Room removed successfully!";
	}

	protected static String listRooms(ArrayList<Room> roomList) {
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println("---------------------");

		return roomList.size() + " Room(s)";
	}

	/**
	 * Schedules a room if the room name is present
	 * 
	 * @param roomList
	 * @return
	 */
	protected static String scheduleRoom(ArrayList<Room> roomList) {
		// No point in creating a schedule when there are no rooms
		if (roomList.isEmpty()) {
			System.out.println("No rooms available, Please add a room first");
			System.out.println(addRoom(rooms));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Schedule a room:");

		String name;
		System.out.println("Please enter room name");
		// loops till user enters a room name that exists
		do {

			valid = true;
			name = getRoomName();
			int index = findRoomIndex(roomList, name);
			if (index == -1) {
				System.out.println("Room name does not exist, Please enter a valid room name");
				valid = false;
			}
		} while (!valid);
		Room curRoom = getRoomFromName(roomList, name);
		String startDate;
		String endDate;
		System.out.println("Present timestamp: " + new Date());
		Date sDate = null;
		boolean success = false;
		Timestamp startTimestamp;
		Timestamp endTimestamp;
		// Loop to check if scheduled timings clash with previous reservations
		// Outer loop runs till all inner loops pass
		// All inner loop data is used as one set of data
		do {
			// First inner loop that validated Start date and time
			do {
				System.out.println("Start Date and time? (yyyy-MM-dd HH:mm:ss):");
				valid = true;

				startDate = validateInput("");
				try {
					sdf.setLenient(false);
					sDate = sdf.parse(startDate);
				} catch (ParseException e) {
					System.out.println("Please enter valid date as mentioned");
					valid = false;
				}
				// checks if the start data and time is 30 mins ahead of present time
				if (null != sDate && (new Date().compareTo(sDate)) > 0
						&& ((sDate.getTime() - (new Date()).getTime()) / 60000) < 30) { // && ((sDate.getTime() - (new
																						// Date()).getTime()) / 60000) >
																						// 30
					System.out.println("Please enter start date&time  30 mins ahead of present time ");
					valid = false;
				}
				// tartDate.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")
			} while (!valid);
			System.out.println("Start date timestamp: " + startDate);
			Date eDate = null;
			// Second inner loop that validates end date and time
			do {
				valid = true;

				System.out.println("End Date and time? (yyyy-MM-dd HH:mm:ss):");
				endDate = validateInput("");
				try {
					sdf.setLenient(false);
					eDate = sdf.parse(endDate);
				} catch (ParseException e) {
					System.out.println("Please enter valid date as mentioned");
					valid = false;
				}
				// condition to see if meeting is at least 30min of duration
				if (null == eDate || ((eDate.getTime() - sDate.getTime()) / 60000) < 30) { // &&
																							// ((sDate.getTime()
																							// -
																							// (new
																							// Date()).getTime())
																							// /
																							// 60000)
																							// >
																							// 30
					System.out.println("Please enter end date&time 30 mins ahead of start date "
							+ ((eDate.getTime() - sDate.getTime()) / 60000));
					valid = false;
				} // 2018-02-01 13:00:00
			} while (!valid);
			startTimestamp = Timestamp.valueOf(startDate);
			endTimestamp = Timestamp.valueOf(endDate);
			success = validateAvailability(startTimestamp, endTimestamp, curRoom);
			if (!success) {
				System.out.println("Entered timings clash above listed revervations, please enter different timings");
			}

		} while (!success);

		System.out.println("Subject?");
		String subject = validateInput("");

		Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

		curRoom.addMeeting(meeting);

		return "Successfully scheduled meeting!";
	}

	/**
	 * Helper function to retrieve room name
	 * 
	 * @param roomList
	 * @param name
	 * @return
	 */
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		int location = findRoomIndex(roomList, name);
		if (location == -1)
			return null;
		return roomList.get(location);
	}

	/**
	 * Helper function that returns the index of the room in the ArrayList return -1
	 * if the room is not present
	 * 
	 * @param roomList
	 * @param roomName
	 * @return
	 */
	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;
		boolean found = false;

		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
				found = true;
				break;
			}
			roomIndex++;
		}

		return found ? roomIndex : -1;
	}

	protected static String getRoomName() {
		System.out.println("Room Name?");
		return validateInput("");
	}

	/**
	 * Used by the schedule method - this methods checks if the new dates collides
	 * with the existing dates
	 * 
	 * @param start
	 * @param end
	 * @param room
	 * @return
	 */
	private static boolean validateAvailability(Timestamp start, Timestamp end, Room room) {
		if (room.getMeetings().isEmpty())
			return true;
		for (Meeting meeting : room.getMeetings()) {
			if (start.after(meeting.getStopTime()) && end.after(meeting.getStopTime())) {
				return true;
			} else if (start.before(meeting.getStartTime()) && end.before(meeting.getStartTime())) {
				return true;
			}
		}
		displayMeeting(room);
		return false;
	}

	/**
	 * Universal input retriever and validator Basic validation thats unique to all
	 * inputs
	 * 
	 * @param input
	 * @return
	 */

	@SuppressWarnings("unchecked")
	private static <E> E validateInput(E input) {
		valid = false;
		while (!valid) {
			try {
				if (input instanceof Integer) {
					input = (E) Integer.valueOf(keyboard.nextLine());
				} else
					input = (E) keyboard.nextLine();
				valid = true;
			} catch (Exception e) {
				System.out.println("Please enter a valid Input");
			}
		}
		return input;
	}

}
