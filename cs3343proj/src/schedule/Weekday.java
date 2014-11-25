package schedule;

public enum Weekday {
	Sun(0), 
	Mon(1), 
	Tue(2), 
	Wed(3),
    Thu(4), 
    Fri(5), 
    Sat(6);
    
    int day;
	
	public int getDay() {
		return day;
	}

	private Weekday(int day) {
		this.day = day;
	}
}
