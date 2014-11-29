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
 * The Class TestSchedule.
 */
public class TestTimeslot extends TestCase{

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
	 * Test the constructor and getters of class Timeslot.
	 */
	@Test
	public void testConstructorAndGetters() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 17, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "MMW", "2480", 14, 17, Weekday.Tue.getDay());

		assertEquals(a.getCrn(), "40001");
		assertEquals(a.getCode(), "CS3332");
		assertEquals(a.getSession(), "C01");
		assertEquals(a.getType(), "Lecture");
		assertEquals(b.getType(), "Tutorial");
		assertEquals(a.getBuilding(), "AC1");
		assertEquals(a.getRoom(), "LT-1");
		assertEquals(a.getStartTime(), 14.0);
		assertEquals(a.getFinishTime(), 17.0);
		assertEquals(a.getDay(), Weekday.Mon.getDay());
	}

	/**
	 * Test sameDay method for both true and false cases.
	 */
	@Test
	public void testSameDay() {

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 10, 13, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Mon.getDay());
		Timeslot c = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		assertEquals(a.sameDay(b), true);
		assertEquals(c.sameDay(b), false);
	}

	/**
	 * Test Timeslot's toString method.
	 */
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

	/**
	 * Test if two sessions are in the same building.
	 */
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


	/**
	 * Test two sessions overlap with each other on the same day.
	 */
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

	/**
	 * Test Two sessions do not overlap with each other on the same day.
	 */
	@Test
	public void testOverlapFalseSameDay() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 14, Weekday.Mon.getDay());

		boolean result = a.overlap(b);
		assertEquals(result, false);
	}

	/**
	 * Test Two sessions do not overlap with each other due to different days.
	 */
	@Test
	public void testOverlapFalseDiffDays() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Tue.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Mon.getDay());

		boolean result = a.overlap(b);
		assertEquals(result, false);
	}


	/**
	 * Test difference between two timeslots.
	 */
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

}


