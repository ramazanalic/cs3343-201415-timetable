package schedule;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class RequiredConstraint.
 */
public class RequiredConstraint implements Constraint{
	
	/** The fulfilled. */
	private boolean fulfilled = true;
	
	/**
	 * Instantiates a new required constraint.
	 */
	public RequiredConstraint() {}
	
	/**
	 * Instantiates a new required constraint.
	 *
	 * @param timeslots the timeslots
	 * @param listOfCrns the list of crns
	 */
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

	/**
	 * Checks if is fulfilled.
	 *
	 * @return true, if is fulfilled
	 */
	public boolean isFulfilled() {
		return fulfilled;
	}
	
}
