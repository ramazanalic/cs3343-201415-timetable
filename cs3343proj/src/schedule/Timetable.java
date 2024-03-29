package schedule;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Timetable.
 */
public class Timetable {

	/** The timeslots. */
	private ArrayList<Timeslot> timeslots;

	/**
	 * Instantiates a new timetable.
	 *
	 * @param timeslots the timeslots
	 */
	public Timetable(ArrayList<Timeslot> timeslots)
	{
		this.timeslots = timeslots;
	}

	/**
	 * Instantiates a new timetable.
	 */
	public Timetable() {
		// TODO Auto-generated constructor stub
		this.timeslots = new ArrayList<Timeslot>();
	}

	/**
	 * Gets the timeslots.
	 *
	 * @return the timeslots
	 */
	public ArrayList<Timeslot> getTimeslots() {
		return this.timeslots;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		// TODO Auto-generated method stub
		return this.timeslots.size();
	}

	/**
	 * Gets the.
	 *
	 * @param k the k
	 * @return the timeslot
	 */
	public Timeslot get(int k) {
		// TODO Auto-generated method stub
		return this.timeslots.get(k);
	}

	/**
	 * Adds the.
	 *
	 * @param timeslot the timeslot
	 */
	public void add(Timeslot timeslot) {
		this.timeslots.add(timeslot);
	}

	/**
	 * Removes the.
	 *
	 * @param timeslot the timeslot
	 */
	public void remove(Timeslot timeslot) {
		this.timeslots.remove(timeslot);
	}

	/**
	 * Removes the.
	 *
	 * @param index the index
	 */
	public void remove(int index) {
		// TODO Auto-generated method stub
		this.timeslots.remove(index);
	}

	/**
	 * Clear.
	 */
	public void clear() {
		this.timeslots.clear();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.timeslots.toString();
	}

	//Assume no overlaps
	/**
	 * Sort the given timeslots by start time.
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

	public void sortByStartTime() {
		ArrayList<Timeslot> result = new ArrayList<Timeslot>();
		
		while (this.timeslots.size() > 0)
		{
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
		}
		this.timeslots = result;
	}

}
