//************************Creation History***********************
//* Program name      : main.java
//* Creation date     : 10-9-2014
//* Creator           : Katy
//* Function          : main function with file I/O
//*********************Modification History**********************
//* Modification date : 10-9-2014
//* Programmer        : Jason
//* Change log        : 001
//* Change detail     : fix the read file function
//*                     create the checking output
//***************************************************************
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main
{
	public static void main(String[] args)
	{
		// Start log#001, Jason, 10-9-2014
		
		//String sCurrentLine;
		String currentLine;
		String[][] courseData = new String[255][8];
		int counter = 0;
		
		String choice;
		
		//try (BufferedReader br = new BufferedReader(new FileReader("data.txt")))
		try (BufferedReader br = new BufferedReader(new FileReader("CS3343_data_test.txt")))
		{
			ArrayList<Course> courseList = new ArrayList<Course>();
			//while ((sCurrentLine = br.readLine()) != null) {
			while ((currentLine = br.readLine()) != null)
			{
				//String[] information = sCurrentLine.split(",");
				String[] temp = currentLine.split(",");
								
				Course course = new Course(temp);
				courseList.add(course);
				
				//courseList.get(counter).courseInfo();
				/*int i = 0;
				
				while (i < 8)
				{
					courseData[counter][i] = temp[i];
					i++;
				}*/
				
				/*
				String course_code = information[0];
				String session = information[1];
				String location = information[2];
				String start_time = information[3];
				String end_time = information[4];
				String week = information[5];
				*/
				
				/*
				System.out.println(counter+1);
				System.out.println("=============================");
				System.out.println("CRN: " + courseData[counter][0]);
				System.out.println("course code: " + courseData[counter][1]);
				System.out.println("session: " + courseData[counter][2]);
				System.out.println("building: " + courseData[counter][3]);
				System.out.println("room: " + courseData[counter][4]);
				System.out.println("start time: " + courseData[counter][5]);
				System.out.println("end time: " + courseData[counter][6]);
				System.out.println("day: " + courseData[counter][7]);
				System.out.println("=============================");
				System.out.println();
				*/
				
				counter++;
				//data.put(course_code, session, location, start_time, end_time, week);
			}
			
			Scanner input = new Scanner(System.in);
			
			System.out.println("1. No lessons on X-day (eg Monday input '1.1')");
			
			System.out.print("Please enter your preference input: ");
			System.out.println("");
			choice = input.next();
			
			Preferences preferences = new Preferences(courseList, choice);
			
			Visualize visualize = new Visualize(courseList);
			
			visualize.printSchedule();
		}
		
		// End log#001, Jason, 10-9-2014
		
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}