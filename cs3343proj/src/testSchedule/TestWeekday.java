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
 * The Class TestWeekday.
 */
public class TestWeekday extends TestCase {
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
	 * Test the constructor of class Weekday.
	 */
	@Test
	public void testWeekday() {
		assertEquals(Weekday.Mon.getDay(), 1);
		assertEquals(Weekday.Tue.getDay(), 2);
		assertEquals(Weekday.Wed.getDay(), 3);
		assertEquals(Weekday.Thu.getDay(), 4);
		assertEquals(Weekday.Fri.getDay(), 5);
		assertEquals(Weekday.Sat.getDay(), 6);
		assertEquals(Weekday.Sun.getDay(), 0);
	}
}
