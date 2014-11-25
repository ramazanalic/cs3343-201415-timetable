package schedule;

// TODO: Auto-generated Javadoc
/**
 * The Class Timeslot.
 */
public class Timeslot {
	
	/** The crn. */
	private String crn = "";
	
	/** The code. */
	private String code = "";
	
	/** The session. */
	private String session = "";
	
	/** The type. */
	private String type = "";
	
	/** The building. */
	private String building = "";
	
	/** The room. */
	private String room = "";
	
	/** The start time. */
	private double startTime = 0;
	
	/** The finish time. */
	private double finishTime = 0;
	
	/** The day. */
	private int day = 0;
	//private String crn = "";
	//private boolean required = false;

	/**
	 * Instantiates a new timeslot.
	 *
	 * @param crn the crn
	 * @param code the code
	 * @param session the session
	 * @param building the building
	 * @param room the room
	 * @param startTime the start time
	 * @param finishTime the finish time
	 * @param day the day
	 */
	public Timeslot(String crn, String code, String session, String building, String room, double startTime, double finishTime, int day) {
		this.crn = crn;
		this.code = code;
		this.session = session;
		if (session.substring(0, 1).equals("C"))
			this.type = "Lecture";
		else
			this.type = "Tutorial"; // or Lab or Seminar?
		this.building = building;
		this.room = room;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.day = day;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.code + "-" + this.session;
	}
	
	/**
	 * Instantiates a new timeslot.
	 */
	public Timeslot() {

	}

	/**
	 * Same day.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	public boolean sameDay(Timeslot a) {
		return this.day == a.day;
	}
	
	/**
	 * Same building.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	public boolean sameBuilding(Timeslot a) {
		return this.building.equals(a.building);
	}

	/**
	 * Overlap.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	public boolean overlap(Timeslot a) {
		if (this.sameDay(a)) {
			if (this.finishTime == a.finishTime) {
				return true;
			} else if (a.finishTime > this.finishTime) {
				if (a.startTime < this.finishTime) return true;
			} else {
				if (this.startTime < a.finishTime) return true;
			}
		}
		return false;
	}

//	public boolean finishBefore(Timeslot a) {
//		return this.finishTime < a.finishTime;
//	}
	
	/**
 * Difference.
 *
 * @param a the a
 * @return the double
 */
public double difference(Timeslot a) {
		if (!this.sameDay(a))
			return -2.0;

		if (this.overlap(a))
			return -1.0;

		if (a.finishTime > this.finishTime)
			return a.startTime - this.finishTime;
		else
			return this.startTime - a.finishTime;

	}

	/**
	 * Gets the crn.
	 *
	 * @return the crn
	 */
	public String getCrn() {
		return crn;
	}

	/**
	 * Sets the crn.
	 *
	 * @param crn the new crn
	 */
	public void setCrn(String crn) {
		this.crn = crn;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public String getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 *
	 * @param session the new session
	 */
	public void setSession(String session) {
		this.session = session;
	}

	/**
	 * Gets the building.
	 *
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * Sets the building.
	 *
	 * @param building the new building
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * Gets the room.
	 *
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * Sets the room.
	 *
	 * @param room the new room
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the finish time.
	 *
	 * @return the finish time
	 */
	public double getFinishTime() {
		return finishTime;
	}

	/**
	 * Sets the finish time.
	 *
	 * @param finishTime the new finish time
	 */
	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets the day.
	 *
	 * @param day the new day
	 */
	public void setDay(int day) {
		this.day = day;
	}
}
