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
	 * @param timetable the timetable
	 * @param timeDifference the time difference
	 */
	public TimeGapConstraint(Timetable timetable, double timeDifference) {
		if (timeDifference == -1)
		{
			this.fulfilled = true;
			return;
		}
		
		timetable.sortByStartTime();
		
		Timetable sorted = timetable;
		
		
		for (int i=0; i<sorted.size()-1; i++) {
			if ((sorted.get(i).overlap(sorted.get(i+1))) || ((sorted.get(i).getDay() == sorted.get(i+1).getDay()) && (sorted.get(i+1).getStartTime() - sorted.get(i).getFinishTime() > timeDifference))) {
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
