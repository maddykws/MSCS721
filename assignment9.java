import static org.junit.Assert.*;

import java.security.Timestamp;
import java.util.ArrayList;

import org.junit.Test;


public class Assignment6Test {
	
	@Test
	public void getBuildingTest() {
		@SuppressWarnings("unused")
		Room r = new Room("Room A",10,"Building XYZ","Location B");
	}
	
	@Test
	public void getLocationTest() {
		Room r = new Room("Room A",10,"Building XYZ","Location B");
		String res = r.getLocation();
		assertEquals("Location B", res);
	}
	
	@Test
	public void setBuildingTest() {
		Room r = new Room("Room A",10,"Building XYZ","Location B");
		r.setBuilding("Building Y");	
	}
	
	@Test
	public void setLocationTest() {
		Room r = new Room("Room A",10,"Building XYZ","Location B");
		r.setLocation("Location Y");	
	}
	
	@SuppressWarnings("unused")
	@Test
	public void returnAvailableRooms_Roomscheduler() {
		@SuppressWarnings("unused")
		ArrayList<Room> rooms = new ArrayList<Room>();
		Room r = new Room("Room A",10,"Building XYZ","Location B");
		RoomScheduler rs = new RoomScheduler();
		Timestamp start_time = null;
		Timestamp end_time = null;
		//rs.listAvailableRoom(rooms, start_time, end_time);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void checkSchdeule_Roomscheduler() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		Room r = new Room("Room A",10,"Building XYZ","Location B");
		RoomScheduler rs = new RoomScheduler();
		Timestamp start_time = null;
		Timestamp end_time = null;
		//rs.checkSchedule(rooms, "Room A", start_time, end_time);
	}
}
