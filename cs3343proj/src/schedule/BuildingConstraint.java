package schedule;

import java.util.ArrayList;

public class BuildingConstraint implements Constraint{
	private boolean fulfilled = true;
	
	public BuildingConstraint() {}
	
	public BuildingConstraint(ArrayList<Timeslot> timeslots, ArrayList<String> listOfBldgs) {
		for (String i : listOfBldgs) {
			for (Timeslot s : timeslots) {
				if (i.equals(s.getBuilding())) {
					System.out.println("Found " + i);
					this.fulfilled = false;
					break;
				}
			}
		}
	}

	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
