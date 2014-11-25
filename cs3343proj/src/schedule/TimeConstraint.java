package schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeConstraint implements Constraint {
	private boolean fulfilled = true;

	public TimeConstraint(Timeslot t, HashMap<Integer,ArrayList<Double>> daytimeExcluded) {
		
		//daytimeExcluded.put(key, value);
		
		// no course before
		// no course after
		// no course between
	}
	
	private ArrayList<Double> before(ArrayList<Timeslot> list, double t) {
		double firstTime = 8.0;
		double lastTime = 22.0;
		// eg < 10am = 8, 9
		// eg >= 3pm = 15, 17, 18, .., 22
		return new ArrayList<Double>();
	}
	
	private ArrayList<Double> after(ArrayList<Timeslot> list, double t) {
		double firstTime = 8.0;
		double lastTime = 22.0;
		// eg < 10am = 8, 9
		// eg >= 3pm = 15, 17, 18, .., 22
		return new ArrayList<Double>();
	}
	
	private ArrayList<Double> between(ArrayList<Timeslot> list, double t) {
		double firstTime = 8.0;
		double lastTime = 22.0;
		// eg < 10am = 8, 9
		// eg >= 3pm = 15, 17, 18, .., 22
		return new ArrayList<Double>();
	}
}
