import java.util.ArrayList;

public class Preferences {
	ArrayList<Course> courseList = new ArrayList<Course>();
	String choices;
	Preferences ()
	{
		choices = "";
	}
	
	Preferences (ArrayList courseList, String choices)
	{
		this.courseList = courseList;
		this.choices = choices;
	}
	
	public void sorting ()
	{
		
	}
}
