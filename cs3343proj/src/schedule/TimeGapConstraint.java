package schedule;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class TimeGapConstraint.
 */
public class TimeGapConstraint implements Constraint {
	
	/** The fulfilled. */
	private boolean fulfilled = true;

	//Assume no overlaps
	/**
	 * Instantiates a new time gap constraint.
	 *
	 * @param t the t
	 * @param timeDifference the time difference
	 */
	public TimeGapConstraint(Timetable timetable, double timeDifference) {
		ArrayList<Timeslot> r = new ArrayList<Timeslot>();
		Schedule.sortByStartTime(timetable, r);
		
		for (int i=0; i<r.size()-1; i++) {
			if ((r.get(i).getDay() == r.get(i+1).getDay()) && (r.get(i+1).getStartTime() - r.get(i).getFinishTime() > timeDifference)) {
				this.fulfilled = false;
				break;
			}
		}
	}

	/**
	 * Checks if the time gap constraint is fulfilled.
	 *
	 * @return true, if is fulfilled
	 */
	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
