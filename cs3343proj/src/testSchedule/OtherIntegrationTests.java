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

// TODO: Auto-generated Javadoc
/**
 * The Class OtherIntegrationTests.
 */
public class OtherIntegrationTests extends TestCase {
	/** The outcontent for testing console output. */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	/** The timeslots. */
	private ArrayList<Timeslot> timeslots;

	/** The timetable. */
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
	public void tearDown() {timeslots = null; timetable = null; System.setOut(null);}

	/**
	 * Test time constraint with helper functions.
	 *
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
	 */


	/**
	 * Test required constraints with no constraint.
	 */
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

	/**
	 * Test building constraints with no constraints.
	 */
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

	/**
	 * Test time gap constraint with no constraint.
	 */
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


	/**
	 * Test Extract by day (Monday).
	 */
	@Test
	public void testReadTimeslotsAndExtractByDay() {
		IO.readTimeslots(timeslots, "CS3343_data.txt");
		Weekday expected = Weekday.Mon;
		ArrayList<Timeslot> t = Utilities.extractTimeslotsByDay(timeslots, expected);
		boolean result = true;
		for (Timeslot i : t)
			if (i.getDay() != expected.getDay())
				result = false;
		assertEquals(result, true);
	}

	/*
	// Test case 7: Extract by day
	 **
	 * Test extract by day false.
	 *
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
	 */


	/**
	 * Test There exists a session of CS2205 on Friday
	 * Test There doesn't exist a session of CS2201 on Tuesday.
	 */
	@Test
	public void testReadTimeslots() {
		IO.readTimeslots(timeslots, "CS3343_data.txt");
		boolean result = false;
		for (Timeslot i : timeslots) {
			if (i.getCode().equals("CS2205") && i.getDay() == Weekday.Fri.getDay())
				result = true;
		}
		assertEquals(result, true);

		result = false;
		for (Timeslot i : timeslots) {
			if (i.getCode().equals("CS2201") && i.getDay() == Weekday.Tue.getDay())
				result = true;
		}
		assertEquals(result, false);
	}

}
