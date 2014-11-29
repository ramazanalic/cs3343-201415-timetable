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
 * The Class TestConstraints.
 */
public class TestConstraints extends TestCase {
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
	 * Test required constraint, for both true and false cases.
	 */
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

	/**
	 * Test time gap constraint for both true and false cases, including the situation of overlapping sessions.
	 */
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


	/**
	 * Test time constraint.
	 */
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
	
	
	/**
	 * Test building constraint.
	 */
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

	/**
	 * Test required and time gap constraints.
	 */
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

}
