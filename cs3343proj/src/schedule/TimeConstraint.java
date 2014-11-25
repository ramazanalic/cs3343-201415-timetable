package schedule;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeConstraint.
 */
public class TimeConstraint implements Constraint {
	
	/** The fulfilled. */
	private boolean fulfilled = true;
	
	/**
	 * Instantiates a new time constraint.
	 *
	 * @param t the t
	 * @param daytimeExcluded the daytime excluded
	 */
	public TimeConstraint(Timetable timetable, HashMap<Integer,ArrayList<Double>> daytimeExcluded) {
		for (Timeslot i : timetable.getTimeslots()) {
			if (daytimeExcluded.containsKey(i.getDay())) {
				for (double j : daytimeExcluded.get(i.getDay())) {
					if (j < i.getFinishTime() && j >= i.getStartTime()) {
						this.fulfilled = false;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Checks if the time constraint is fulfilled.
	 *
	 * @return true, if is fulfilled
	 */
	public boolean isFulfilled() {
		return fulfilled;
	}

	
}
