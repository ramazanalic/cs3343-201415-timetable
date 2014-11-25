package schedule;

import java.util.ArrayList;

public class TimeGapConstraint implements Constraint {
	private boolean fulfilled = true;

	//Assume no overlaps
	public TimeGapConstraint(ArrayList<Timeslot> t, double timeDifference) {
		ArrayList<Timeslot> r = new ArrayList<Timeslot>();
		Schedule.sortByStartTime(t, r);
		
		for (int i=0; i<r.size()-1; i++) {
			if ((r.get(i).getDay() == r.get(i+1).getDay()) && (r.get(i+1).getStartTime() - r.get(i).getFinishTime() > timeDifference)) {
				this.fulfilled = false;
				break;
			}
		}
	}

	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
