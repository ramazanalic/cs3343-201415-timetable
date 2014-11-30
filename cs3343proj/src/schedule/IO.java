package schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class IO.
 */
public class IO {

	/** The input string. */
	private static String inputString;

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
				//int dia = 0;
				int dia = -1;
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
			System.out.println("The datas file doest not exist.");
		}

	}


	/**
	 * Prints the schedule to console.
	 * @author YC Yau
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

	/**
	 * Prints the cr ns.
	 *
	 * @param timetable the timetable
	 */
	public static void printCRNs(Timetable timetable)
	{
		System.out.print("List of CRNs: [");
		for (int i=0; i<timetable.size(); i++)
		{
			Timeslot timeslot = timetable.get(i);
			System.out.print(timeslot.getCrn());
			if (i < timetable.size()-1)
				System.out.print(", ");
		}
		System.out.println("]");
	}

	/**
	 * Prints the timetable.
	 *
	 * @param listOfTimetables the list of timetables
	 */
	public static void printTimetable(ArrayList<Timetable> listOfTimetables)
	{
		int numPosTimetables = listOfTimetables.size();
		//int chosen = -1;

		/*
		while (true)
		{
			System.out.println("\n\nPlease enter a number between 1 and " + numPosTimetables + ", or -1 to finish.");
			inputString = readInputFromConsole();
			try
			{
				chosen = Integer.parseInt(inputString);
			}
			catch (NumberFormatException e) {
				System.out.println("You can only input a valid integer.");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("You can only input a valid integer.");
			}
			if (chosen == -1)
				break;
			printSchedule(listOfTimetables.get(chosen-1));
			printCRNs(listOfTimetables.get(chosen-1));
		}
		 */
		int randomIdx = (int) (Math.random() * numPosTimetables);
		System.out.println("Timetable " + (randomIdx+1) + ":");

		printSchedule(listOfTimetables.get(randomIdx));
		printCRNs(listOfTimetables.get(randomIdx));

	}
	/*
	public static String readInputFromConsole()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("An error occured.");
			//e.printStackTrace();
		}
		return "-1";
	}
	 */

	/**
	 * Read time constraints.
	 *
	 * @param filename the filename
	 * @return the hash map
	 */
	public static HashMap<Integer,ArrayList<Double>> readTimeConstraints(String filename)
	{
		HashMap<Integer, ArrayList<Double>> dayTimeMap = new HashMap <Integer, ArrayList<Double>>();

		int[][] tempTime = new int[7][16];



		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 16; j++)
				tempTime[i][j] = -1;

		String currentLine;

		String[] constraint = new String[4];

		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			while ((currentLine = br.readLine()) != null)
			{	
				String[] temp = currentLine.split("\t");

				if (currentLine.startsWith("#"))
					continue;

				for (int i = 0; i < temp.length; i++)
					constraint[i] = temp[i];

				//System.out.println("keyword:" + constraint[0]);
				//System.out.println("value1:" + constraint[1]);
				//System.out.println("value2:" + constraint[2]);
				//System.out.println("value3:" + constraint[3]);

				if (constraint[0].equals("Before"))
				{
					if (constraint[1].equals("Everyday"))
						for (int i = 0; i < 7; i++)
							Utilities.beforeTime(i, Integer.parseInt(constraint[2]), tempTime);

					else
					{
						for (Weekday dayi : Weekday.values())
						{
							if (dayi.toString().equals(constraint[1]))
							{ 
								Utilities.beforeTime(dayi.getDay(), Integer.parseInt(constraint[2]), tempTime);
							}
						}
					}
				}
				else if (constraint[0].equals("After"))
				{
					if (constraint[1].equals("Everyday"))
						for (int i = 0; i < 7; i++)
							Utilities.afterTime(i, Integer.parseInt(constraint[2]), tempTime);

					else
					{
						for (Weekday dayi : Weekday.values())
						{
							if (dayi.toString().equals(constraint[1]))
							{ 
								Utilities.afterTime(dayi.getDay(), Integer.parseInt(constraint[2]), tempTime);
							}
						}
					}
				}
				else if (constraint[0].equals("Between"))
				{
					if (constraint[1].equals("Everyday"))
						for (int i = 0; i < 7; i++)
							Utilities.betweenTime(i, Integer.parseInt(constraint[2]), Integer.parseInt(constraint[3]), tempTime);

					else
					{
						for (Weekday dayi : Weekday.values())
						{
							if (dayi.toString().equals(constraint[1]))
							{ 
								Utilities.betweenTime(dayi.getDay(), Integer.parseInt(constraint[2]), Integer.parseInt(constraint[3]), tempTime);
							}
						}
					}
				}
				else if (constraint[0].equals("Dayoff"))
				{
					for (Weekday dayi : Weekday.values())
					{
						if (dayi.toString().equals(constraint[1]))
						{ 
							Utilities.betweenTime(dayi.getDay(), 8, 23, tempTime);
						}
					}
				}
				else 
				{
					throw new IOException();
				}

			}

			for (int i = 0; i < 7; i++)
			{
				ArrayList<Double> tempTimeList = new ArrayList<Double>();

				for (int j = 0; j < 16; j++)
				{
					if (tempTime[i][j] != -1)
						tempTimeList.add((double)tempTime[i][j]);
				}

				Collections.sort(tempTimeList);

				dayTimeMap.put(i, tempTimeList);
			}
		}

		catch (IOException e)
		{
			System.out.println("You should input valid data in \"TimeConstraints.txt\".");
		}

		return dayTimeMap;
	}

	/**
	 * Read required constraints.
	 *
	 * @param filename the filename
	 * @return the array list
	 */
	public static ArrayList<String> readRequiredConstraints(String filename)
	{
		ArrayList<String> ret = new ArrayList<String>();
		String currentLine;

		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			while ((currentLine = br.readLine()) != null)
			{
				ret.add(currentLine.replace("\r\n", "").replace("\n", ""));
			}
		}
		catch (IOException e)
		{
			System.out.println("You should input valid data in \"TheseCRNsMustBeIncluded.txt\".");
		}

		return ret;
	}

	/**
	 * Read building constraints.
	 *
	 * @param filename the filename
	 * @return the array list
	 */
	public static ArrayList<String> readBuildingConstraints(String filename)
	{
		ArrayList<String> ret = new ArrayList<String>();
		String currentLine;

		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			while ((currentLine = br.readLine()) != null)
			{
				ret.add(currentLine.replace("\r\n", "").replace("\n", ""));
			}
		}
		catch (IOException e)
		{
			System.out.println("You should input valid data in \"NoClassInTheseBuildings.txt\".");
		}

		return ret;
	}

	/**
	 * Read time gap constraint.
	 *
	 * @param filename the filename
	 * @return the double
	 */
	public static double readTimeGapConstraint(String filename)
	{
		ArrayList<String> ret = new ArrayList<String>();
		String currentLine;

		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			currentLine = br.readLine();
			if (currentLine != null)
			{
				currentLine = currentLine.replace("\r\n", "").replace("\n", "").replace(" ", "");
				return Double.parseDouble(currentLine); 
			}
		}
		catch (IOException e)
		{
			System.out.println("You should input a valid number to indicate the maximum time between 2 sessions in \"MaxTimeBetween2Sessions.txt\".");
		}
		catch (NumberFormatException e)
		{
			System.out.println("You should input a valid number to indicate the maximum time between 2 sessions in \"MaxTimeBetween2Sessions.txt\".");
		}

		return -1;
	}


}
