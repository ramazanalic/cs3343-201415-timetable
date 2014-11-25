package schedule;

public class Timeslot {
	private String crn = "";
	private String code = "";
	private String session = "";
	private String type = "";
	private String building = "";
	private String room = "";
	private double startTime = 0;
	private double finishTime = 0;
	private int day = 0;
	//private String crn = "";
	//private boolean required = false;

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

	public String toString() {
		return this.code + "-" + this.session;
	}
	
	public Timeslot() {

	}

	public boolean sameDay(Timeslot a) {
		return this.day == a.day;
	}
	
	public boolean sameBuilding(Timeslot a) {
		return this.building.equals(a.building);
	}

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

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
