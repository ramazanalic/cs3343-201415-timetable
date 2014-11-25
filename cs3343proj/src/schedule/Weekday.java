package schedule;

// TODO: Auto-generated Javadoc
/**
 * The Enum Weekday.
 */
public enum Weekday {
	
	/** The Sun. */
	Sun(0), 
	
	/** The Mon. */
	Mon(1), 
	
	/** The Tue. */
	Tue(2), 
	
	/** The Wed. */
	Wed(3),
    
    /** The Thu. */
    Thu(4), 
    
    /** The Fri. */
    Fri(5), 
    
    /** The Sat. */
    Sat(6);
    
    /** The day. */
    int day;
	
	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Instantiates a new weekday.
	 *
	 * @param day the day
	 */
	private Weekday(int day) {
		this.day = day;
	}
}
