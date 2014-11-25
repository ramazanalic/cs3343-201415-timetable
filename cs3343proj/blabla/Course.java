
public class Course {
	private int crn;
	private String courseCode;
	private String session;
	private String building;
	private String room;
	private int startTime;
	private int endTime;
	private int day;

	
	Course()
	{
		crn = 0;
		courseCode = "";
		session = "";
		building = "";
		room = "";
		startTime = 0;
		endTime = 0;
		day = 0;
	}
	
	Course (String[] temp)
	{
		crn = Integer.parseInt(temp[0]);
		courseCode = temp[1];
		session = temp[2];
		building = temp[3];
		room = temp[4];
		startTime = Integer.parseInt(temp[5]);
		endTime = Integer.parseInt(temp[6]);
		day = Integer.parseInt(temp[7]);
	}
	
	public void courseInfo()
	{
		System.out.println("=============================");
		System.out.println("CRN: " + crn);
		System.out.println("course code: " + courseCode);
		System.out.println("session: " + session);
		System.out.println("building: " + building);
		System.out.println("room: " + room);
		System.out.println("start time: " + startTime);
		System.out.println("end time: " + endTime);
		System.out.println("day: " + day);
		System.out.println("=============================");
		System.out.println();
	}
	
	public int getCRN()
	{
		return crn;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	
	public int getEndTime()
	{
		return endTime;
	}
	
	public int getDay()
	{
		return day;
	}
	
	

}
