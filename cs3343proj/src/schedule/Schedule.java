package schedule;

import java.io.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Schedule.
 */
public class Schedule {


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		ArrayList<Timeslot> timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();
		
		//String inputFile = "CS3343_data2.txt"; 

		if (args.length == 0)
		{
			System.out.println("Please enter the data file as an argument.");
			return;
		}

		String inputFile = args[0];
		
		IO.readTimeslots(timeslots, inputFile); //Extract method
		//System.out.println(timeslots.size());
		
		// validate input
		//validateInput(ArrayList<Timeslot> timeslots)
		if (!Utilities.validateInput(timeslots, buildingList))
			return;
		
		// input and validate constraints
		//parseInputFile(inputFile, timeConstraint, timeGapConstraint, requiredConstraint, buildingConstraint);
		

		ArrayList<String> uniqueCourses = Utilities.allCourses(timeslots);
		HashMap<String,HashMap<String,ArrayList<Timeslot>>> uniqueCourseTimeslots = new HashMap<String,HashMap<String,ArrayList<Timeslot>>>();
		for (String i : uniqueCourses) {
			ArrayList<Timeslot> t = Utilities.extractTimeslotsByCode(timeslots, i);

			ArrayList<Timeslot> allLectures = Utilities.extractTimeslotsByType(t, "Lecture");
			ArrayList<Timeslot> allTutorials = Utilities.extractTimeslotsByType(t, "Tutorial");

			// filter Timeslot according to constraints



			//			System.out.println(allLectures.size());
			//			System.out.println(allTutorials.size());

			HashMap<String,ArrayList<Timeslot>> slot = new HashMap<String,ArrayList<Timeslot>>();
			if (allLectures.size()>0)
				slot.put("Lecture", allLectures);
			/*else
			{
				System.out.println("There is no lecture.");
				return;
			}*/
			if (allTutorials.size()>0)
				slot.put("Tutorial", allTutorials);
			/*else
			{
				System.out.println("There is no tutorial.");
				return;
			}*/
			uniqueCourseTimeslots.put(i, slot);

		}
		//System.out.println(uniqueCourseTimeslots);

		HashMap<String,ArrayList<ArrayList<Timeslot>>> permutatedUniqueCourseTimeslots = new HashMap<String,ArrayList<ArrayList<Timeslot>>>();
		ArrayList<ArrayList<ArrayList<Timeslot>>> permutatedUniqueCourseTimeslotsList = new ArrayList<ArrayList<ArrayList<Timeslot>>>();

		for (String i : uniqueCourses) {
			permutatedUniqueCourseTimeslots.put(i, Utilities.permutate(uniqueCourseTimeslots.get(i).get("Lecture"), uniqueCourseTimeslots.get(i).get("Tutorial")));
			permutatedUniqueCourseTimeslotsList.add(Utilities.permutate(uniqueCourseTimeslots.get(i).get("Lecture"), uniqueCourseTimeslots.get(i).get("Tutorial")));
		}

		//		for (String i : uniqueCourses) {
		//			System.out.println(i + " - " + permutatedUniqueCourseTimeslots.get(i).size());
		//		}
		//System.out.println(permutatedUniqueCourseTimeslots);
		//System.out.println("****");
		//System.out.println(permutatedUniqueCourseTimeslotsList);
		//System.out.println("****");
		//		
		//		System.out.println("=========");

		//permutate all lists
		ArrayList<ArrayList<ArrayList<Timeslot>>> allPerm = Utilities.GeneratePermutations(permutatedUniqueCourseTimeslotsList);
		//System.out.println(allPerm.get(0).size());
		//for (ArrayList<Timeslot> i : allPerm.get(0))
		//System.out.println(i);

		ArrayList<Timetable> validPermutatedUniqueCourseTimeslotsList = new ArrayList<Timetable>();
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
			if (!overlap){
				Timetable timetable = new Timetable(i);
				validPermutatedUniqueCourseTimeslotsList.add(timetable);
			}
				
		}

		int numValidCombinations = validPermutatedUniqueCourseTimeslotsList.size();
		if (numValidCombinations == 0)
		{
			System.out.println("There is no possible combination i.e. You should remove at least 1 session.");
			return;
		}
		else
			System.out.println("There are " + numValidCombinations + " possible combinations.");

		// filter Timeslot according to constraints


		ArrayList<String> listOfCrns = IO.readRequiredConstraints("TheseCRNsMustBeIncluded.txt");
		ArrayList<String> listOfBldgs = IO.readBuildingConstraints("NoClassInTheseBuildings.txt");
		double timeDifference = IO.readTimeGapConstraint("MaxTimeBetween2Sessions.txt");
		
		HashMap<Integer,ArrayList<Double>> listOfTime = IO.readTimeConstraints("TimeConstraints.txt");
		//listOfCrns.add("60002");
		//listOfCrns.add("50005");

		ArrayList<Timetable> listOfTimetables = new ArrayList<Timetable>();
		for (int i=0; i < validPermutatedUniqueCourseTimeslotsList.size(); i++) {
			Timetable l = validPermutatedUniqueCourseTimeslotsList.get(i);
			RequiredConstraint rc = new RequiredConstraint(l, listOfCrns);
			BuildingConstraint bc = new BuildingConstraint(l, listOfBldgs);
			TimeGapConstraint tc = new TimeGapConstraint(l, timeDifference);
			//TimeGapConstraint
			TimeConstraint timeConstraint = new TimeConstraint(l, listOfTime);
			
			//if (rc.isFulfilled() && bc.isFulfilled() && tc.isFulfilled())
			if (rc.isFulfilled() && bc.isFulfilled() && tc.isFulfilled() && timeConstraint.isFulfilled())
			{
				listOfTimetables.add(l);
				
			}
		}
		
		int numPosTimetables = listOfTimetables.size();
		if (numPosTimetables == 0)
		{
			System.out.println("There is no possible combination i.e. You should change some of the constraints.");
			return;
		}
		else
			System.out.println("There are " + numPosTimetables + " timetables available.");

		
		
		IO.printTimetable(listOfTimetables);
		
	}

	/**
	 * Validate input.
	 *
	 * @param timeslots the timeslots
	 * @return true, if successful
	 */
	/*
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
	 */
	
}
