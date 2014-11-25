package schedule;

import java.util.ArrayList;

public class BuildingConstraint implements Constraint{
	private boolean fulfilled = true;
	
	public BuildingConstraint() {}
	
	public BuildingConstraint(ArrayList<Timeslot> timeslots, ArrayList<String> listOfBldgs) {
		boolean found = true;
		for (String i : listOfBldgs) {
			boolean foundi = false;
			for (Timeslot s : timeslots)
				if (i.equals(s.getBuilding())) {
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
