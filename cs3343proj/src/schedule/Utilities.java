package schedule;

import java.util.ArrayList;

public class Utilities {
	/** The first session's start time. */
	public static double firstTime = 8.0;
	
	/** The last session's finish time. */
	public static double lastTime = 23.0;

	
	/**
	 * All courses.
	 *
	 * @param timeslots the timeslots
	 * @return the array list of all courses
	 */
	public static ArrayList<String> allCourses(ArrayList<Timeslot> timeslots) {
		ArrayList<String> courses = new ArrayList<String>();

		for (Timeslot j : timeslots) {
			boolean contains = false;
			for(String i: courses){
				if(j.getCode().equals(i)){
					contains = true;
					break;
				}
			}
			if (!contains)
				courses.add(j.getCode());
		}
		return courses;
	}
	/**
	 * Extract timeslots by day.
	 *
	 * @param timeslots the timeslots
	 * @param day the day
	 * @return the array list of courses of a particular day
	 */
	public static ArrayList<Timeslot> extractTimeslotsByDay(ArrayList<Timeslot> timeslots, Weekday day) {
		ArrayList<Timeslot> t = new ArrayList<Timeslot>();
		for (Timeslot i : timeslots)
			if (i.getDay() == day.getDay())
				t.add(i);
		return t;
	}

	/**
	 * Extract timeslots by course code.
	 *
	 * @param timeslots the timeslots
	 * @param code the course code
	 * @return the array list of courses of a particular course
	 */
	public static ArrayList<Timeslot> extractTimeslotsByCode(ArrayList<Timeslot> timeslots, String code) {
		ArrayList<Timeslot> t = new ArrayList<Timeslot>();
		for (Timeslot i : timeslots)
			if (i.getCode().equals(code))
				t.add(i);
		return t;
	}

	/**
	 * Extract timeslots by session type (lecture/tutorial).
	 *
	 * @param timeslots the timeslots
	 * @param type the type of session
	 * @return the array list of courses of a particular type of session
	 */
	public static ArrayList<Timeslot> extractTimeslotsByType(ArrayList<Timeslot> timeslots, String type) {
		ArrayList<Timeslot> t = new ArrayList<Timeslot>();
		for (Timeslot i : timeslots)
			if (i.getType().equals(type))
				t.add(i);
		return t;
	}

	/**
	 * Permutate.
	 *
	 * @param list1 the first array list to permutate
	 * @param list2 the second array list to permutate
	 * @return the array list of permutating list1 with list2
	 */
	public static ArrayList<ArrayList<Timeslot>> permutate(ArrayList<Timeslot> list1, ArrayList<Timeslot> list2) {
		ArrayList<ArrayList<Timeslot>> res = new ArrayList<ArrayList<Timeslot>>();

		for (Timeslot i: list1) {
			for (Timeslot j: list2) {
				ArrayList<Timeslot> t = new ArrayList<Timeslot>();
				t.add(i);
				t.add(j);
				res.add(t);
			}
		}
		return res;
	}

	/**
	 * Permutate array list of array list.
	 *
	 * @param list1 the first array list of array list to permutate
	 * @param list2 the second array list of array list to permutate
	 * @return the array list of permutating list1 with list2
	 */
	public static ArrayList<ArrayList<Timeslot>> permutateArrayList(ArrayList<ArrayList<Timeslot>> list1, ArrayList<ArrayList<Timeslot>> list2) {
		ArrayList<ArrayList<Timeslot>> res = new ArrayList<ArrayList<Timeslot>>();

		for (ArrayList<Timeslot> i: list1) {
			for (ArrayList<Timeslot> j: list2) {
				ArrayList<Timeslot> t = new ArrayList<Timeslot>();
				t.addAll(i);
				t.addAll(j);
				res.add(t);
			}
		}
		return res;
	}

	/**
	 * Generate permutations by splitting an array list by course code and session type recursively.
	 *
	 * @param list the list storing all sessions
	 * @return the array list of all permutated sessions
	 */
	public static ArrayList<ArrayList<ArrayList<Timeslot>>> GeneratePermutations(ArrayList<ArrayList<ArrayList<Timeslot>>> list)
	{
		if (list.size() == 1)
			return list;

		ArrayList<ArrayList<Timeslot>> t = permutateArrayList(list.get(0), list.get(1));
		list.add(t);
		list.remove(1);
		list.remove(0);

		return GeneratePermutations(list);
	}


	/**
	 * To generate an array list of time before a given time t.
	 *
	 * @param t the given time t
	 * @return the array list of time NOT before time t
	 */
	public static ArrayList<Double> beforeTime(double t) {
		ArrayList<Double> listOfExcludedTime = new ArrayList<Double>();
		for (double i = firstTime; i < t; i++) {
			listOfExcludedTime.add(i);
		}
		return listOfExcludedTime;
	}

	/**
	 * To generate an array list of time after a given time t.
	 *
	 * @param t the given time t
	 * @return the array list of time NOT after time t
	 */
	public static ArrayList<Double> afterTime(double t) {
		ArrayList<Double> listOfExcludedTime = new ArrayList<Double>();
		for (double i = t; i < lastTime; i++) {
			listOfExcludedTime.add(i);
		}
		return listOfExcludedTime;
	}

	/**
	 * To generate an array list of time between 2 given times t1 and t2. (t1, t2]
	 *
	 * @param t1, t2 the given times
	 * @return the array list of time NOT between t1 and t2
	 */
	public static ArrayList<Double> betweenTime(double t1, double t2) {
		ArrayList<Double> listOfExcludedTime = new ArrayList<Double>();
		for (double i = t1; i < t2; i++) {
			listOfExcludedTime.add(i);
		}
		return listOfExcludedTime;
	}

}
