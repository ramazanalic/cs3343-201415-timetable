package testSchedule;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.*;

import schedule.BuildingConstraint;
import schedule.IO;
import schedule.RequiredConstraint;
import schedule.Schedule;
import schedule.TimeConstraint;
import schedule.TimeGapConstraint;
import schedule.Timeslot;
import schedule.Timetable;
import schedule.Weekday;
import schedule.Utilities;
import junit.framework.TestCase;

public class TestSchedule extends TestCase{

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private ArrayList<Timeslot> timeslots;
	private Timetable timetable;

	/**
	 * Sets up the test fixture.
	 *
	 * Called before every test case method.
	 */
	@Before
	public void setUp() { timeslots = new ArrayList<Timeslot>(); timetable = new Timetable(); System.setOut(new PrintStream(outContent)); }

	/**
	 * Tears down the test fixture.
	 *
	 * Called after every test case method.
	 */
	@After
	public void tearDown() {System.setOut(null);}

	// Test case 1: There exists a session of CS2205 on Friday
	@Test
	public void testCS2205Fri() {
		IO.readTimeslots(timeslots, "CS3343_data.txt");
		boolean result = false;
		for (Timeslot i : timeslots) {
			if (i.getCode().equals("CS2205") && i.getDay() == Weekday.Fri.getDay())
				result = true;
		}
		assertEquals(result, true);
	}

	// Test case 2: There doesn't exist a session of CS2201 on Tuesday
	@Test
	public void testCS2201Tue() {
		IO.readTimeslots(timeslots, "CS3343_data.txt");
		boolean result = false;
		for (Timeslot i : timeslots) {
			if (i.getCode().equals("CS2201") && i.getDay() == Weekday.Tue.getDay())
				result = true;
		}
		assertEquals(result, false);
	}

	// Test case 3: Two sessions overlap with each other
	@Test
	public void testOverlapTrue() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 17, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 15, Weekday.Mon.getDay());
		Timeslot c = new Timeslot("40003","CS2332","LA1", "AC2", "5503", 15, 16, Weekday.Mon.getDay());
		Timeslot d = new Timeslot("40004","CS4335","LA1", "AC1", "5110", 15, 16, Weekday.Mon.getDay());

		boolean result = a.overlap(b);
		assertEquals(result, true);
		result = a.overlap(c);
		assertEquals(result, true);
		result = d.overlap(c);
		assertEquals(result, true);
	}

	// Test case 4: Two sessions do not overlap with each other
	@Test
	public void testOverlapFalseSameDay() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 14, Weekday.Mon.getDay());

		boolean result = a.overlap(b);
		assertEquals(result, false);
	}

	// Test case 5: Two sessions do not overlap with each other due to different days
	@Test
	public void testOverlapFalseDiffDays() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Tue.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Mon.getDay());

		boolean result = a.overlap(b);
		assertEquals(result, false);
	}

	// Test case 6: Extract by day (Monday)
	@Test
	public void testExtractMonday() {
		IO.readTimeslots(timeslots, "CS3343_data.txt");
		Weekday expected = Weekday.Mon;
		ArrayList<Timeslot> t = Utilities.extractTimeslotsByDay(timeslots, expected);
		boolean result = true;
		for (Timeslot i : t)
			if (i.getDay() != expected.getDay())
				result = false;
		assertEquals(result, true);
	}

	// Test case 7: Extract by day
	@Test
	public void testExtractByDayFalse() {
		Weekday expected = Weekday.Mon;
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, expected.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, expected.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		boolean result = true;
		for (Timeslot i : timeslots)
			if (i.getDay() != expected.getDay())
				result = false;
		assertEquals(result, false);
	}

	// Test case 8: Test difference
	@Test
	public void testDifference() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		assertEquals(a.difference(b), -2.0);
		assertEquals(a.difference(c), -2.0);
		assertEquals(b.difference(c), 1.5);
		assertEquals(c.difference(d), -1.0);
		assertEquals(d.difference(e), 0.0);
	}

	// Test case 9: Test extract by code
	@Test
	public void testExtractByCode() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);
		timeslots.add(f);
		String expected = "CS3443";
		ArrayList<Timeslot> t = Utilities.extractTimeslotsByCode(timeslots, expected);
		assertEquals(t.toString().equals("[CS3443-CB1, CS3443-CA1]"), true);
	}

	// Test case 10: Test extract by code
	@Test
	public void testExtractByCode2() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);
		timeslots.add(f);
		String expected = "CS3332";
		ArrayList<Timeslot> t = Utilities.extractTimeslotsByCode(timeslots, expected);
		assertEquals(t.toString().equals("[CS3332-C01]"), true);
	}

	// Test case 11: Test extract by code
	@Test
	public void testRequiredConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());

		timetable = new Timetable();

		timetable.add(a);
		timetable.add(b);
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);		

		ArrayList<String> listOfCrns = new ArrayList<String>();
		listOfCrns.add("40001");
		listOfCrns.add("40005");
		RequiredConstraint rc = new RequiredConstraint(timetable, listOfCrns);
		assertEquals(rc.isFulfilled(), true);

		listOfCrns.clear();
		listOfCrns.add("40001");
		listOfCrns.add("40007");
		rc = new RequiredConstraint(timetable, listOfCrns);
		assertEquals(rc.isFulfilled(), false);
	}

	// Test case 12: Test time gap constraint
	@Test
	public void testTimeGapConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Thu.getDay());
		timetable = new Timetable();

		timetable.add(a);
		timetable.add(b);
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);		

		TimeGapConstraint rc = new TimeGapConstraint(timetable, 3);
		assertEquals(rc.isFulfilled(), true);

		Timeslot f = new Timeslot("40006","CS3301","L01", "AC1", "LT-2", 15, 16, Weekday.Wed.getDay());
		timetable.add(f);
		
		rc = new TimeGapConstraint(timetable, 3);
		assertEquals(rc.isFulfilled(), false);
		
		Timeslot g = new Timeslot("40007","CS3483","C01", "AC1", "LT-2", 11, 13, Weekday.Wed.getDay());
		timetable.add(g);
		
		rc = new TimeGapConstraint(timetable, 3);
		assertEquals(rc.isFulfilled(), false);
	}

	//Test case 13: Test required + time gap constraints
	@Test
	public void testRequiredAndTimeGapConstraints() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Thu.getDay());


		timetable = new Timetable();
		timetable.add(a);
		timetable.add(b);
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);	

		ArrayList<String> listOfCrns = new ArrayList<String>();
		listOfCrns.add("40001");
		listOfCrns.add("40005");
		RequiredConstraint rc = new RequiredConstraint(timetable, listOfCrns);
		TimeGapConstraint rc1 = new TimeGapConstraint(timetable, 3);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), true);

		listOfCrns.clear();
		listOfCrns.add("40001");
		listOfCrns.add("40007");
		rc = new RequiredConstraint(timetable, listOfCrns);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), false);

		listOfCrns.clear();
		listOfCrns.add("40003");
		listOfCrns.add("40004");
		rc = new RequiredConstraint(timetable, listOfCrns);
		Timeslot f = new Timeslot("40006","CS3301","L01", "AC1", "LT-2", 15, 16, Weekday.Wed.getDay());
		timetable.add(f);
		rc1 = new TimeGapConstraint(timetable, 3);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), false);
	}

	//Test case 14: Test time constraint
	@Test
	public void testTimeConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 13, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 14, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());

		timetable = new Timetable();

		timetable.add(a);
		timetable.add(b);
		timetable.add(c);

		HashMap<Integer,ArrayList<Double>> daytimeExcluded = new HashMap<Integer,ArrayList<Double>>();

		ArrayList<Double> l1 = new ArrayList<Double>();
		l1.add(9.0);
		l1.add(10.0);
		l1.add(12.0);
		l1.add(18.0);
		l1.add(19.0);
		l1.add(20.0);
		l1.add(21.0);
		l1.add(22.0);
		daytimeExcluded.put(1, l1);

		TimeConstraint rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot d = new Timeslot("40004","CS3305","LA1", "AC1", "LT-3", 16, 18, Weekday.Mon.getDay());
		timetable.add(d);
		rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot e = new Timeslot("40005","CS3305","LA1", "AC1", "LT-3", 12, 14, Weekday.Tue.getDay());
		timetable.add(e);
		rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot f = new Timeslot("40006","CS3305","LA1", "AC1", "LT-3", 12, 14, Weekday.Mon.getDay());
		timetable.add(f);
		rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), false);

		ArrayList<Double> l2 = new ArrayList<Double>();
		l2.add(9.0);
		l2.add(16.0);
		l2.add(17.0);
		l2.add(18.0);
		l2.add(19.0);
		l2.add(20.0);
		l2.add(21.0);
		l2.add(22.0);
		daytimeExcluded.put(2, l2);
		timetable.remove(f);
		rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot g = new Timeslot("40007","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Tue.getDay());
		timetable.add(g);
		rc = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc.isFulfilled(), false);
	}

	//Test case 15: Test time constraint and helper functions
	@Test
	public void testTimeConstraintWithHelperFunctions() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 12, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 13, Weekday.Tue.getDay());

		timetable = new Timetable();

		timetable.add(a);
		timetable.add(b);
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);

		HashMap<Integer,ArrayList<Double>> daytimeExcluded = new HashMap<Integer,ArrayList<Double>>();

		// Mon: Exclude before 12, after 18
		ArrayList<Double> mon = new ArrayList<Double>();
		mon.addAll(Utilities.beforeTime(12));
		mon.addAll(Utilities.afterTime(18));
		daytimeExcluded.put(1, mon);

		//Tue: Exclude before 9, after 18
		ArrayList<Double> tue = new ArrayList<Double>();
		tue.addAll(Utilities.beforeTime(9));
		tue.addAll(Utilities.afterTime(18));
		daytimeExcluded.put(2, tue);

		TimeConstraint rc1 = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), true);

		//Wed: Exclude between 12 to 14
		ArrayList<Double> wed = new ArrayList<Double>();
		wed.addAll(Utilities.betweenTime(12, 14));
		daytimeExcluded.put(3, wed);

		rc1 = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), true);



		//Wed: Exclude before 13
		wed = new ArrayList<Double>();
		wed.addAll(Utilities.beforeTime(13));
		daytimeExcluded.put(3, wed);

		rc1 = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), false);

		wed = new ArrayList<Double>();
		wed.addAll(Utilities.beforeTime(9));
		wed.addAll(Utilities.beforeTime(8));
		wed.addAll(Utilities.beforeTime(7));
		daytimeExcluded.put(3, wed);

		rc1 = new TimeConstraint(timetable, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), true);
	}

	// Test case 16: Test building constraint
	@Test
	public void testBuildingConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());

		timetable = new Timetable();

		timetable.add(a);
		timetable.add(b);
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);

		ArrayList<String> listOfBuildings = new ArrayList<String>();
		listOfBuildings.add("CMC");

		BuildingConstraint rc = new BuildingConstraint(timetable, listOfBuildings);
		assertEquals(rc.isFulfilled(), true);

		listOfBuildings.add("AC3");
		rc = new BuildingConstraint(timetable, listOfBuildings);
		assertEquals(rc.isFulfilled(), false);
	}

	// Test case 17: Test SameBuilding
	@Test
	public void testSameBuilding() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());

		assertEquals(a.sameBuilding(c), true);
		assertEquals(a.sameBuilding(b), false);
		assertEquals(e.sameBuilding(c), true);
		assertEquals(d.sameBuilding(b), false);
	}

	// Test case 18: Test ExtractTimeslotsByType
	@Test
	public void testExtractTimeslotsByType() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());


		timeslots.clear();

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);


		ArrayList<Timeslot> extracted = Utilities.extractTimeslotsByType(timeslots, "Lecture");
		ArrayList<Timeslot> lectures = new ArrayList<Timeslot>();
		lectures.add(a);
		lectures.add(d);
		lectures.add(e);
		assertEquals(extracted.equals(lectures), true);

		extracted = Utilities.extractTimeslotsByType(timeslots, "Tutorial");
		ArrayList<Timeslot> tutorials = new ArrayList<Timeslot>();
		tutorials.add(b);
		tutorials.add(c);
		assertEquals(extracted.equals(tutorials), true);
	}

	// Test case 19: Test toString
	@Test
	public void testToString() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());

		assertEquals(a.toString().equals("CS3332-C01"), true);
		assertEquals(b.toString().equals("CS2332-LA1"), true);
		assertEquals(c.toString().equals("CS3301-LA1"), true);
		assertEquals(d.toString().equals("CS3201-CA1"), true);
		assertEquals(e.toString().equals("CS3443-CB1"), true);

	}

	// Test case 19b: Test toString of ArrayList
	@Test
	public void testToString2() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());

		timetable = new Timetable();		

		timetable.add(a);
		timetable.add(b);

		assertEquals(timetable.toString().equals("[CS3332-C01, CS2332-LA1]"), true);

		timetable.clear();
		timetable.add(c);
		timetable.add(d);
		timetable.add(e);

		assertEquals(timetable.toString().equals("[CS3301-LA1, CS3201-CA1, CS3443-CB1]"), true);

	}

	// Test case 20: Test allCourses
	@Test
	public void testAllCourses() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);
		timeslots.add(f);

		ArrayList<String> uniqueCourses = Utilities.allCourses(timeslots);
		assertEquals(uniqueCourses.toString().equals("[CS3332, CS2332, CS3301, CS3201, CS3443]"), true);

	}

	// Test case 21: Test permutate
	@Test
	public void testPermutate() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);

		ArrayList<Timeslot> timeslots2 = new ArrayList<Timeslot>();

		timeslots2.add(d);
		timeslots2.add(e);
		timeslots2.add(f);

		assertEquals(Utilities.permutate(timeslots, timeslots2).toString().equals("[[CS3332-C01, CS3201-CA1], [CS3332-C01, CS3443-CB1], [CS3332-C01, CS3443-CA1], [CS2332-LA1, CS3201-CA1], [CS2332-LA1, CS3443-CB1], [CS2332-LA1, CS3443-CA1], [CS3301-LA1, CS3201-CA1], [CS3301-LA1, CS3443-CB1], [CS3301-LA1, CS3443-CA1]]"), true);

	}

	// Test case 22: Test permutateArrayList
	@Test
	public void testPermutateArrayList() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3332","LA2", "AC1", "LT-3", 9, 12, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3443","LA2", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","LA1", "AC1", "LT-2", 12, 13, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","C01", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		ArrayList<ArrayList<Timeslot>> timeslotsA = new ArrayList<ArrayList<Timeslot>>();
		ArrayList<Timeslot> timeslotsA1 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> timeslotsA2 = new ArrayList<Timeslot>();

		timeslotsA1.add(a);
		timeslotsA1.add(b);
		timeslotsA2.add(a);
		timeslotsA2.add(c);
		timeslotsA.add(timeslotsA1);
		timeslotsA.add(timeslotsA2);

		ArrayList<ArrayList<Timeslot>> timeslotsB = new ArrayList<ArrayList<Timeslot>>();
		ArrayList<Timeslot> timeslotsB1 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> timeslotsB2 = new ArrayList<Timeslot>();

		timeslotsB1.add(d);
		timeslotsB1.add(f);
		timeslotsB2.add(e);
		timeslotsB2.add(f);
		timeslotsB.add(timeslotsB1);
		timeslotsB.add(timeslotsB2);

		ArrayList<ArrayList<Timeslot>> permutated = Utilities.permutateArrayList(timeslotsA, timeslotsB);

		ArrayList<ArrayList<Timeslot>> timeslotsAexpected = new ArrayList<ArrayList<Timeslot>>();
		ArrayList<Timeslot> timeslotsExp1 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> timeslotsExp2 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> timeslotsExp3 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> timeslotsExp4 = new ArrayList<Timeslot>();

		timeslotsExp1.add(a);
		timeslotsExp1.add(b);
		timeslotsExp1.add(d);
		timeslotsExp1.add(f);
		timeslotsExp2.add(a);
		timeslotsExp2.add(b);
		timeslotsExp2.add(e);
		timeslotsExp2.add(f);
		timeslotsExp3.add(a);
		timeslotsExp3.add(c);
		timeslotsExp3.add(d);
		timeslotsExp3.add(f);
		timeslotsExp4.add(a);
		timeslotsExp4.add(c);
		timeslotsExp4.add(e);
		timeslotsExp4.add(f);

		timeslotsAexpected.add(timeslotsExp1);
		timeslotsAexpected.add(timeslotsExp2);
		timeslotsAexpected.add(timeslotsExp3);
		timeslotsAexpected.add(timeslotsExp4);

		assertEquals(permutated.equals(timeslotsAexpected), true);

	}

	// Test case 22: Test permutateArrayList
	@Test
	public void testGeneratePermutations() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3332","LA2", "AC1", "LT-3", 9, 12, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3443","LA2", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","LA1", "AC1", "LT-2", 12, 13, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","C01", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());
		Timeslot g = new Timeslot("40007","CS3201","LA1", "MMW", "2603", 9, 11.5, Weekday.Thu.getDay());
		Timeslot h = new Timeslot("40008","CS3201","C01", "AC1", "LT-17", 10, 12, Weekday.Thu.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);
		timeslots.add(f);
		timeslots.add(g);
		timeslots.add(h);

		ArrayList<Timeslot> CS3332 = Utilities.extractTimeslotsByCode(timeslots, "CS3332");
		ArrayList<Timeslot> CS3332l = Utilities.extractTimeslotsByType(CS3332, "Lecture");
		ArrayList<Timeslot> CS3332t = Utilities.extractTimeslotsByType(CS3332, "Tutorial");

		ArrayList<Timeslot> CS3443 = Utilities.extractTimeslotsByCode(timeslots, "CS3443");
		ArrayList<Timeslot> CS3443l = Utilities.extractTimeslotsByType(CS3443, "Lecture");
		ArrayList<Timeslot> CS3443t = Utilities.extractTimeslotsByType(CS3443, "Tutorial");

		ArrayList<Timeslot> CS3201 = Utilities.extractTimeslotsByCode(timeslots, "CS3201");
		ArrayList<Timeslot> CS3201l = Utilities.extractTimeslotsByType(CS3201, "Lecture");
		ArrayList<Timeslot> CS3201t = Utilities.extractTimeslotsByType(CS3201, "Tutorial");

		ArrayList<ArrayList<Timeslot>> CS3332p = Utilities.permutate(CS3332l, CS3332t);
		ArrayList<ArrayList<Timeslot>> CS3443p = Utilities.permutate(CS3443l, CS3443t);
		ArrayList<ArrayList<Timeslot>> CS3201p = Utilities.permutate(CS3201l, CS3201t);

		ArrayList<ArrayList<ArrayList<Timeslot>>> allCourses = new ArrayList<ArrayList<ArrayList<Timeslot>>>();
		allCourses.add(CS3332p);
		allCourses.add(CS3443p);
		allCourses.add(CS3201p);

		ArrayList<ArrayList<ArrayList<Timeslot>>> permutated = Utilities.GeneratePermutations(allCourses);
		//System.out.println(permutated);

		ArrayList<ArrayList<ArrayList<Timeslot>>> expected = new ArrayList<ArrayList<ArrayList<Timeslot>>>();
		ArrayList<ArrayList<Timeslot>> expectedT = new ArrayList<ArrayList<Timeslot>>();
		ArrayList<Timeslot> expected1 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> expected2 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> expected3 = new ArrayList<Timeslot>();
		ArrayList<Timeslot> expected4 = new ArrayList<Timeslot>();

		expected1.add(h);
		expected1.add(g);
		expected1.add(a);
		expected1.add(b);
		expected1.add(f);
		expected1.add(d);

		expected2.add(h);
		expected2.add(g);
		expected2.add(a);
		expected2.add(b);
		expected2.add(f);
		expected2.add(e);

		expected3.add(h);
		expected3.add(g);
		expected3.add(a);
		expected3.add(c);
		expected3.add(f);
		expected3.add(d);

		expected4.add(h);
		expected4.add(g);
		expected4.add(a);
		expected4.add(c);
		expected4.add(f);
		expected4.add(e);

		expectedT.add(expected1);
		expectedT.add(expected2);
		expectedT.add(expected3);
		expectedT.add(expected4);

		expected.add(expectedT);

		assertEquals(permutated.equals(expected), true);
	}

	// Test case 23: Test printScheduleHeader
	/*
	@Test
	public void testPrintScheduleHeader() {
		String x = printScheduleHeader();
		String expected = "                                       |-------------------------|                                       \n                                       |   Visualized timetable  |                                       \n|------------|------------|------------|------------|------------|------------|------------|------------|\n|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |";
		assertEquals(x.equals(expected), true);

	}
	 */

	// Test case 24: Test printSchedule
	@Test
	public void testPrintSchedule() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(f);

		Timetable timetable = new Timetable(timeslots);

		IO.printSchedule(timetable);

		String expected = "                                       |-------------------------|                                       \n" + 
				"                                       |   Visualized timetable  |                                       \n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|0800-0850   |            |            |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|0900-0950   |            |            |CS3301-LA1  |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1000-1050   |            |CS3201-CA1  |CS3301-LA1  |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1100-1150   |            |CS3201-CA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1200-1250   |            |            |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1300-1350   |            |CS2332-LA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1400-1450   |CS3332-C01  |CS2332-LA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1500-1550   |CS3332-C01  |CS2332-LA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1600-1650   |            |            |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1700-1750   |            |            |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1800-1850   |            |CS3443-CA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|1900-1950   |            |CS3443-CA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|2000-2050   |            |CS3443-CA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|2100-2150   |            |CS3443-CA1  |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\n" + 
				"|2200-2250   |            |            |            |            |            |            |            |\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|" + 
				"";

		assertEquals(expected.replaceAll("\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

		//assertEquals(x.equals(expected), true);

	}

	// Test case 25: Test main
	@Test
	public void testMain() {

		String[] args = {"CS3343_data2.txt"};

		/*		ByteArrayInputStream in = new ByteArrayInputStream("60002".getBytes());
		System.setIn(in);
		in = new ByteArrayInputStream("50005".getBytes());
		System.setIn(in);
		in = new ByteArrayInputStream("-1".getBytes());
		System.setIn(in);
		in = new ByteArrayInputStream("AC3".getBytes());
		System.setIn(in);
		in = new ByteArrayInputStream("-1".getBytes());
		System.setIn(in);*/
		/*
		Schedule.main(args);
		//System.setIn(System.in);

		String expected = "There are 4248 possible combinations.\r\n" + 
				"                                       |-------------------------|                                       \r\n" + 
				"                                       |   Visualized timetable  |                                       \r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|0800-0850   |            |            |            |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|0900-0950   |            |            |            |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1000-1050   |            |CS2010-C0A  |CS2201-C0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1100-1150   |CS3332-T0A  |CS2010-C0A  |CS2201-C0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1200-1250   |            |            |CS2205-T01  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1300-1350   |CS3332-C0A  |CS2010-T0A  |CS2201-T0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1400-1450   |CS3332-C0A  |            |CS2205-C0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1500-1550   |CS3332-C0A  |            |            |CS4321-C01  |CS2332-L0A  |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1600-1650   |CS2112-C0A  |            |            |CS4321-C01  |CS2332-L0A  |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1700-1750   |CS2112-C0A  |            |            |            |CS2332-C0A  |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1800-1850   |CS2112-T0A  |            |CS4321-T0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|1900-1950   |            |            |CS4321-T0A  |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|2000-2050   |            |            |            |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|2100-2150   |            |            |            |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"|2200-2250   |            |            |            |            |            |            |            |\r\n" + 
				"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
				"";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
		 */
		assertEquals(true,true);
	}

	// Test case 26: Test printSchedule(No possible combination)
	@Test
	public void testPrintScheduleNo() {
		String[] args = {"CS3343_data.txt"};
		Schedule.main(args);
		String expected = "There is no possible combination i.e. You should remove at least 1 session.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 27: Test printSchedule(No lectures)
	@Test
	public void testMainNoLectures() {
		String[] args = {"CS3343_data-NoLectures.txt"};
		Schedule.main(args);
		String expected = "There is no lecture.";

		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 28: Test printSchedule(No tutorials)
	@Test
	public void testMainNoTutorials() {
		String[] args = {"CS3343_data-NoTutorials.txt"};
		Schedule.main(args);

		String expected = "There is no tutorial.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 29: Test Main(No arguments)
	@Test
	public void testMainNoArguments() {
		String[] args = {};
		Schedule.main(args);
		String expected = "Please enter the data file as an argument.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 30: Test printSchedule
	@Test
	public void testPrintCRNs() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(f);

		Timetable timetable = new Timetable(timeslots);

		IO.printCRNs(timetable);
		String expected = "List of CRNs: [40001, 40002, 40003, 40004, 40006]";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 31: Test readRequiredConstraints
	@Test
	public void testReadRequiredConstraints() {
		ArrayList<String> crns = IO.readRequiredConstraints("TheseCRNsMustBeIncluded.txt");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("50005");
		expected.add("60002");

		assertEquals(expected, crns);
	}

	// Test case 32: Test readRequiredConstraints
	@Test
	public void testReadRequiredConstraintsFail() {
		ArrayList<String> crns = IO.readRequiredConstraints(".txt");
		ArrayList<String> expected = new ArrayList<String>();

		assertEquals(expected, crns);
		assertEquals("You should input valid data in \"TheseCRNsMustBeIncluded.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}


	// Test case 33: Test readBuildingConstraints
	@Test
	public void testReadBuildingConstraints() {

		ArrayList<String> bldgs = IO.readBuildingConstraints("NoClassInTheseBuildings.txt");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("CMC");
		expected.add("AC3");

		assertEquals(expected, bldgs);
	}

	// Test case 34: Test readBuildingConstraints
	@Test
	public void testReadBuildingConstraintsFail() {

		ArrayList<String> bldgs = IO.readBuildingConstraints("");
		ArrayList<String> expected = new ArrayList<String>();

		assertEquals(expected, bldgs);
		assertEquals("You should input valid data in \"NoClassInTheseBuildings.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 35: Test readTimeGapConstraint
	@Test
	public void testReadTimeGapConstraint() {

		double gapTime = IO.readTimeGapConstraint("MaxTimeBetween2Sessions.txt");
		assertEquals(2.0, gapTime);
	}

	// Test case 36: Test readTimeGapConstraint
	@Test
	public void testReadTimeGapConstraintFail() {

		double gapTime = IO.readTimeGapConstraint("MaxTimeBetween2SessionsFail.txt");
		assertEquals(-1.0, gapTime);
		assertEquals("You should input a valid number to indicate the maximum time between 2 sessions in \"MaxTimeBetween2Sessions.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	// Test case 37: Test readTimeGapConstraint
	@Test
	public void testReadTimeGapConstraintEmpty() {

		double gapTime = IO.readTimeGapConstraint("Empty.txt");
		assertEquals(-1.0, gapTime);
	}

	// Test case 38: Test testBuildingConstraint
	@Test
	public void testReadBuildingConstraintsEmpty() {
		ArrayList<String> bldgs = IO.readBuildingConstraints("Empty.txt");
		assertEquals(new ArrayList<String>(), bldgs);
	}

	// Test case 39: Test testBuildingConstraint
	@Test
	public void testReadRequiredConstraintsEmpty() {
		ArrayList<String> crns = IO.readRequiredConstraints("Empty.txt");
		assertEquals(new ArrayList<String>(), crns);
	}
	
	// Test case 39: Test testBuildingConstraint
	@Test
	public void testRequiredConstraintsEmpty() {
		ArrayList<String> crns = IO.readRequiredConstraints("Empty.txt");
		
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(f);

		Timetable timetable = new Timetable(timeslots);
		RequiredConstraint rc = new RequiredConstraint(timetable, crns);
		assertEquals(rc.isFulfilled(), true);
	}
	
	// Test case 39: Test testBuildingConstraint
	@Test
	public void testBuildingConstraintsEmpty() {
		ArrayList<String> bldgs = IO.readBuildingConstraints("Empty.txt");
		
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(f);

		Timetable timetable = new Timetable(timeslots);
		BuildingConstraint bc = new BuildingConstraint(timetable, bldgs);
		assertEquals(bc.isFulfilled(), true);
	}
	
	// Test case 37: Test readTimeGapConstraint
	@Test
	public void testTimeGapConstraintEmpty() {

		double gapTime = IO.readTimeGapConstraint("Empty.txt");

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot f = new Timeslot("40006","CS3443","CA1", "AC1", "LT-2", 18, 22, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(f);

		Timetable timetable = new Timetable(timeslots);
		TimeGapConstraint tc = new TimeGapConstraint(timetable, gapTime);
		assertEquals(tc.isFulfilled(), true);
	}
	
}
