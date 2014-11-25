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
	
	public void clear() {
		this.timeslots.clear();
	}
	
	public String toString() {
		return this.timeslots.toString();
	}
	
	//Assume no overlaps
	/**
	 * Sort the given timeslots by start time.
	 *
	 * @param timetable the timeslots
	 * @param result the sorted timeslots by start time
	 */
	/*
	public void sortByStartTime(Timetable timetable, Timetable result) {
		if (timetable.size() == 1) {
			result.add(timetable.get(0));
			return;
		}

		double min = Double.MAX_VALUE;
		int minIdx = 0;
		for (int i=0; i<timetable.size(); i++) {
			if (timetable.get(i).getStartTime()+(timetable.get(i).getDay()-1)*24 < min) {
				min = timetable.get(i).getStartTime()+(timetable.get(i).getDay()-1)*24;
				minIdx = i;
			}
		}

		result.add(timetable.get(minIdx));
		timetable.remove(minIdx);
		sortByStartTime(timetable, result);
	}
	*/
	
	public void sortByStartTime(Timetable result) {
		if (this.size() == 1) {
			result.add(this.get(0));
			return;
		}

		double min = Double.MAX_VALUE;
		int minIdx = 0;
		for (int i=0; i<this.size(); i++) {
			if (this.get(i).getStartTime()+(this.get(i).getDay()-1)*24 < min) {
				min = this.get(i).getStartTime()+(this.get(i).getDay()-1)*24;
				minIdx = i;
			}
		}

		result.add(this.get(minIdx));
		this.remove(minIdx);
		this.sortByStartTime(result);
	}
	
}
