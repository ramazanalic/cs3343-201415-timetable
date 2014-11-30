import java.util.ArrayList;
public class Visualize {
	ArrayList<Course> courseList = new ArrayList<Course>();
	int startTime = 800;
	int endTime = 850;
	
	Visualize () {}
	
	Visualize (ArrayList courseList)
	{
		this.courseList = courseList;
	}
	
	public void printSchedule()
	{
		String stringStart;
		String stringEnd;
		boolean filled = false;
		
		System.out.println("                                       |-------------------------|                                       ");
		System.out.println("                                       |   Visualized timetable  |                                       ");
		System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
		System.out.println("|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |");
		//System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
		for (int i = 0; i < 15; i++)
		{
			stringStart = String.valueOf(startTime);
			stringEnd = String.valueOf(endTime);

			if (stringStart.length() == 3)
				stringStart = "0" + stringStart;
		
			if (stringEnd.length() == 3)
				stringEnd = "0" + stringEnd;
			
			System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
			
			System.out.print("|" + stringStart + "-" + stringEnd + "   |");
			
			for (int j = 1; j <= 7; j++)
			{
				for (int k = 0; k < courseList.size(); k++)
				{
					if (j == courseList.get(k).getDay() && courseList.get(k).getStartTime() <= startTime && courseList.get(k).getEndTime() >= endTime)
					{
						System.out.print(courseList.get(k).getCRN() + "       |");
						filled = true;
					}
				}
				
				if (filled == false)
					System.out.print("            |");
				
				filled = false;
			}
			
			System.out.println();
			
			stringStart = null;
			stringEnd = null;
			
			startTime += 100;
			endTime += 100;
		}
		
		System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
	}
}
