package schedule;

import java.io.*;
import java.util.*;

public class Schedule {

	public static void main(String[] args) {

		ArrayList<Timeslot> timeslots = new ArrayList<Timeslot>();
		readTimeslots(timeslots, "CS3343_data2.txt"); //Extract method
		//System.out.println(timeslots.size());
		
		
		// validate input

		// input constraints

		ArrayList<String> uniqueCourses = allCourses(timeslots);
		HashMap<String,HashMap<String,ArrayList<Timeslot>>> uniqueCourseTimeslots = new HashMap<String,HashMap<String,ArrayList<Timeslot>>>();
		for (String i : uniqueCourses) {
			ArrayList<Timeslot> t = extractTimeslotsByCode(timeslots, i);
			
			ArrayList<Timeslot> allLectures = extractTimeslotsByType(t, "Lecture");
			ArrayList<Timeslot> allTutorials = extractTimeslotsByType(t, "Tutorial");

			// filter Timeslot according to constraints
			
			
			
//			System.out.println(allLectures.size());
//			System.out.println(allTutorials.size());
			
			HashMap<String,ArrayList<Timeslot>> slot = new HashMap<String,ArrayList<Timeslot>>();
			if (allLectures.size()>0)
				slot.put("Lecture", allLectures);
			if (allTutorials.size()>0)
				slot.put("Tutorial", allTutorials);
			
			uniqueCourseTimeslots.put(i, slot);
			
		}
		System.out.println(uniqueCourseTimeslots);
		
		HashMap<String,ArrayList<ArrayList<Timeslot>>> permutatedUniqueCourseTimeslots = new HashMap<String,ArrayList<ArrayList<Timeslot>>>();
		ArrayList<ArrayList<ArrayList<Timeslot>>> permutatedUniqueCourseTimeslotsList = new ArrayList<ArrayList<ArrayList<Timeslot>>>();
		
		for (String i : uniqueCourses) {
			permutatedUniqueCourseTimeslots.put(i, permutate(uniqueCourseTimeslots.get(i).get("Lecture"), uniqueCourseTimeslots.get(i).get("Tutorial")));
			permutatedUniqueCourseTimeslotsList.add(permutate(uniqueCourseTimeslots.get(i).get("Lecture"), uniqueCourseTimeslots.get(i).get("Tutorial")));
		}
		
//		for (String i : uniqueCourses) {
//			System.out.println(i + " - " + permutatedUniqueCourseTimeslots.get(i).size());
//		}
		//System.out.println(permutatedUniqueCourseTimeslots);
		//System.out.println(permutatedUniqueCourseTimeslotsList);
//		
//		System.out.println("=========");
						
		//permutate all lists
		ArrayList<ArrayList<ArrayList<Timeslot>>> allPerm = GeneratePermutations(permutatedUniqueCourseTimeslotsList);
		//System.out.println(allPerm.get(0).size());
		//for (ArrayList<Timeslot> i : allPerm.get(0))
			//System.out.println(i);
		
		ArrayList<ArrayList<Timeslot>> validPermutatedUniqueCourseTimeslotsList = new ArrayList<ArrayList<Timeslot>>();
		for (ArrayList<Timeslot> i : allPerm.get(0)) {
			boolean overlap = false;
			for (Timeslot j : i) {
				for (Timeslot k : i) {
					if (j.equals(k)) {
						break;
					}
					if (j.overlap(k)) {
						overlap = true;
						break;
					}
				}
			}
			if (!overlap)
				validPermutatedUniqueCourseTimeslotsList.add(i);
		}
		
		int numValidCombinations = validPermutatedUniqueCourseTimeslotsList.size();
		if (numValidCombinations == 0)
			System.out.println("There is no possible combination i.e. You should remove at least 1 course");
		else
			System.out.println("There are " + numValidCombinations + " possible combinations.");
		
		
		
		// filter Timeslot according to constraints
		
		
		ArrayList<String> listOfCrns = new ArrayList<String>();
		listOfCrns.add("60002");
		listOfCrns.add("50005");
		
		int count = 0;
		for (int i=0; i < validPermutatedUniqueCourseTimeslotsList.size(); i++) {
			ArrayList<Timeslot> l = validPermutatedUniqueCourseTimeslotsList.get(i);
			RequiredConstraint rc = new RequiredConstraint(l, listOfCrns);
			//System.out.println("slot 0 fulfilled: " + rc.isFulfilled());
			if (rc.isFulfilled()) {printSchedule(validPermutatedUniqueCourseTimeslotsList.get(i)); System.out.println(); count++;}
		}
		System.out.println(count);
	}


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
			e.printStackTrace();
		}
	}

	public static boolean validateInput(ArrayList<Timeslot> timeslots) { //Extract Method
		for (Timeslot i : timeslots) {





		}

		//Extract MethodSSSS

		// check session first character
		
		// check sufficient lecture/tutorials

		// check building

		// check start/finish time format and range

		// check weekday

		return true;
	}

	public static ArrayList<String> allCourses(ArrayList<Timeslot> timeslots) {
		ArrayList<String> courses = new ArrayList<String>();

		for (Timeslot j : timeslots) {
			boolean contains = false;
			for(String i: courses){
				if(j.getCode().equals(i)){
					contains = true;
					break;
				}
			}
			if (!contains)
				courses.add(j.getCode());
		}
		return courses;
	}

	


public static ArrayList<Timeslot> extractTimeslotsByDay(ArrayList<Timeslot> timeslots, Weekday day) {
	ArrayList<Timeslot> t = new ArrayList<Timeslot>();
	for (Timeslot i : timeslots)
		if (i.getDay() == day.getDay())
			t.add(i);
	return t;
}

public static ArrayList<Timeslot> extractTimeslotsByCode(ArrayList<Timeslot> timeslots, String code) {
	ArrayList<Timeslot> t = new ArrayList<Timeslot>();
	for (Timeslot i : timeslots)
		if (i.getCode().equals(code))
			t.add(i);
	return t;
}

public static ArrayList<Timeslot> extractTimeslotsByType(ArrayList<Timeslot> timeslots, String type) {
	ArrayList<Timeslot> t = new ArrayList<Timeslot>();
	for (Timeslot i : timeslots)
		if (i.getType().equals(type))
			t.add(i);
	return t;
}

public static ArrayList<ArrayList<Timeslot>> permutate(ArrayList<Timeslot> list1, ArrayList<Timeslot> list2) {
	ArrayList<ArrayList<Timeslot>> res = new ArrayList<ArrayList<Timeslot>>();
	
	for (Timeslot i: list1) {
		for (Timeslot j: list2) {
			ArrayList<Timeslot> t = new ArrayList<Timeslot>();
			t.add(i);
			t.add(j);
			res.add(t);
		}
	}
	return res;
}

public static ArrayList<ArrayList<Timeslot>> permutateArrayList(ArrayList<ArrayList<Timeslot>> list1, ArrayList<ArrayList<Timeslot>> list2) {
	ArrayList<ArrayList<Timeslot>> res = new ArrayList<ArrayList<Timeslot>>();
	
	for (ArrayList<Timeslot> i: list1) {
		for (ArrayList<Timeslot> j: list2) {
			ArrayList<Timeslot> t = new ArrayList<Timeslot>();
			t.addAll(i);
			t.addAll(j);
			res.add(t);
		}
	}
	return res;
}

public static ArrayList<ArrayList<ArrayList<Timeslot>>> GeneratePermutations(ArrayList<ArrayList<ArrayList<Timeslot>>> list)//, ArrayList<Timeslot> result, int depth, ArrayList<Timeslot> current)
{
	if (list.size() == 1)
		return list;

	ArrayList<ArrayList<Timeslot>> t = permutateArrayList(list.get(0), list.get(1));
	list.add(t);
	list.remove(1);
	list.remove(0);
	
	return GeneratePermutations(list);
	
//    if(depth == allTimeslots.size())
//    {
//       result.add(current);
//       return;
//     }
//
//    for(int i = 0; i < Lists.get(depth).size(); ++i)
//    {
//        GeneratePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
//    }
}

public static void printSchedule(ArrayList<Timeslot> timeslots)
{
	int startTime = 8;

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
			for (int k = 0; k < timeslots.size(); k++)
			{
				if (j == timeslots.get(k).getDay() && timeslots.get(k).getStartTime() <= startTime && timeslots.get(k).getFinishTime() >= startTime+1)
				{
					//System.out.print(timeslots.get(k).getCrn() + "       |");
					System.out.print(timeslots.get(k).getCode() + "-" + timeslots.get(k).getSession() + "  |");
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
