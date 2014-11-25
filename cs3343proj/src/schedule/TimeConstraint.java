package schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeConstraint implements Constraint {
	private boolean fulfilled = true;
	
	public TimeConstraint(ArrayList<Timeslot> t, HashMap<Integer,ArrayList<Double>> daytimeExcluded) {

		for (Timeslot i : t) {
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
	
	public boolean isFulfilled() {
		return fulfilled;
	}

	
}
