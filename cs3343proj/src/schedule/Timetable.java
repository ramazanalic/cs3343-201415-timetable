package schedule;

import java.util.ArrayList;

public class Timetable {

	private ArrayList<Timeslot> timeslots;
	
	public Timetable(ArrayList<Timeslot> timeslots)
	{
		this.timeslots = timeslots;
	}

	public Timetable() {
		// TODO Auto-generated constructor stub
		this.timeslots = new ArrayList<Timeslot>();
	}

	public ArrayList<Timeslot> getTimeslots() {
		return this.timeslots;
	}

	public int size() {
		// TODO Auto-generated method stub
		return this.timeslots.size();
	}

	public Timeslot get(int k) {
		// TODO Auto-generated method stub
		return this.timeslots.get(k);
	}
	
	public void add(Timeslot timeslot) {
		this.timeslots.add(timeslot);
	}
	
	public void remove(Timeslot timeslot) {
		this.timeslots.remove(timeslot);
	}

	public void remove(int index) {
		// TODO Auto-generated method stub
		this.timeslots.remove(index);
	}
	
}
