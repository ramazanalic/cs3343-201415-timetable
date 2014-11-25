package schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO {


	/**
	 * Read timeslots from a file.
	 *
	 * @param timeslots the array list storing timeslots
	 * @param fn the file name
	 */
	public static void readTimeslots(ArrayList<Timeslot> timeslots, String fn) {
		String currentLine;
		String[][] courseData = new String[255][8];
		int counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fn)))
		{
			while ((currentLine = br.readLine()) != null)
			{
				String[] temp = currentLine.split(",");

				for (int i=0; i<8; i++)
					courseData[counter][i] = temp[i];

				double st = 
						Math.round(Double.parseDouble(courseData[counter][5].substring(0, 2)) +
								Double.parseDouble(courseData[counter][5].substring(3, 5)) / 60);
				double ft =
						Math.round(Double.parseDouble(courseData[counter][6].substring(0, 2)) +
								Double.parseDouble(courseData[counter][6].substring(3, 5)) / 60);
				int dia = 0;
				for (Weekday dayi : Weekday.values()) {
					if (dayi.toString().equals(courseData[counter][7])) { 
						dia = dayi.day;
					}
				}

				Timeslot t = new Timeslot(
						courseData[counter][0],
						courseData[counter][1],
						courseData[counter][2],
						courseData[counter][3],
						courseData[counter][4],
						st,
						ft,
						dia
						);
				timeslots.add(t);

				counter++;
			}
		}
		
		catch (IOException e)
		{
			e.printStackTrace(); //1% statement coverage
		}
		
	}


	/**
	 * Prints the schedule to console.
	 *
	 * @param timetable the entire schedule
	 */
	public static void printSchedule(Timetable timetable)
	{
		int startTime = 8;

		String stringStart;
		String stringEnd;
		boolean filled = false;

		System.out.println("                                       |-------------------------|                                       \n                                       |   Visualized timetable  |                                       \n|------------|------------|------------|------------|------------|------------|------------|------------|\n|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |");
		//System.out.println("                                       |-------------------------|                                       ");
		//System.out.println("                                       |   Visualized timetable  |                                       ");
		//System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
		//System.out.println("|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |");
		
		//System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
		for (int i = 0; i < 15; i++)
		{
			stringStart = String.valueOf(startTime) + "00";
			stringEnd = String.valueOf(startTime) + "50";

			while (stringStart.length() < 4)
				stringStart = "0" + stringStart;

			while (stringEnd.length() < 4)
				stringEnd = "0" + stringEnd;

			System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");

			System.out.print("|" + stringStart + "-" + stringEnd + "   |");

			for (int j = 1; j <= 7; j++)
			{
				for (int k = 0; k < timetable.size(); k++)
				{
					if (j == timetable.get(k).getDay() && timetable.get(k).getStartTime() <= startTime && timetable.get(k).getFinishTime() >= startTime+1)
					{
						//System.out.print(timeslots.get(k).getCrn() + "       |");
						System.out.print(timetable.get(k).getCode() + "-" + timetable.get(k).getSession() + "  |");
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

			startTime += 1;
		}

		System.out.println("|------------|------------|------------|------------|------------|------------|------------|------------|");
	}

	
}
