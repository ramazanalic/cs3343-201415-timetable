package schedule;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildingConstraint.
 */
public class BuildingConstraint implements Constraint{
	
	/** The fulfilled. */
	private boolean fulfilled = true;
	
	/**
	 * Instantiates a new building constraint.
	 */
	public BuildingConstraint() {}
	
	/**
	 * Instantiates a new building constraint.
	 *
	 * @param timeslots the timeslots
	 * @param listOfBldgs the list of bldgs
	 */
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

	/**
	 * Checks if is fulfilled.
	 *
	 * @return true, if is fulfilled
	 */
	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
