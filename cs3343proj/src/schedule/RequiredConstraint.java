package schedule;

import java.util.ArrayList;

public class RequiredConstraint implements Constraint{
	private boolean fulfilled = true;
	
	public RequiredConstraint() {}
	
	public RequiredConstraint(ArrayList<Timeslot> timeslots, ArrayList<String> listOfCrns) {
		boolean found = true;
		for (String i : listOfCrns) {
			boolean foundi = false;
			for (Timeslot s : timeslots)
				if (i.equals(s.getCrn())) {
					foundi = true;
				}
			found &= foundi;
		}
		
		this.fulfilled = found;
	}

	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
