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
public class TestSchedule extends TestCase{

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

	/*
	 * Test extract by code2.
	 *
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
	 */

	// Test case 23: Test printScheduleHeader
	/*
	@Test
	public void testPrintScheduleHeader() {
		String x = printScheduleHeader();
		String expected = "                                       |-------------------------|                                       \n                                       |   Visualized timetable  |                                       \n|------------|------------|------------|------------|------------|------------|------------|------------|\n|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |";
		assertEquals(x.equals(expected), true);

	}
	 */

	/**
	 * Test main method given correct input.
	 */
	@Test
	public void testMain() {

		String[] args = {"CS3343_data3.txt"};
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

		Schedule.main(args);
		//System.setIn(System.in);

		String expected = 
				"There are 1 possible combinations.\r\n" + 
						"There are 1 timetables available.\r\n" + 
						"Timetable 1:\r\n" + 
						"                                       |-------------------------|                                       \r\n" + 
						"                                       |   Visualized timetable  |                                       \r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|Time        |Monday      |Tuesday     |Wednesday   |Thursday    |Friday      |Saturday    |Sunday      |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|0800-0850   |            |            |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|0900-0950   |            |            |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1000-1050   |            |CS2010-C0A  |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1100-1150   |CS3332-T0A  |CS2010-C0A  |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1200-1250   |            |            |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1300-1350   |CS3332-C0A  |CS2010-T0A  |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1400-1450   |CS3332-C0A  |            |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1500-1550   |CS3332-C0A  |            |            |            |CS2332-L0A  |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1600-1650   |            |            |            |            |CS2332-L0A  |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1700-1750   |            |            |            |            |CS2332-C0A  |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1800-1850   |            |            |CS4321-T0A  |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|1900-1950   |            |            |CS4321-T0A  |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|2000-2050   |            |            |CS4321-C0A  |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|2100-2150   |            |            |CS4321-C0A  |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"|2200-2250   |            |            |            |            |            |            |            |\r\n" + 
						"|------------|------------|------------|------------|------------|------------|------------|------------|\r\n" + 
						"List of CRNs: [10003, 10001, 20006, 20008, 30013, 30012, 60002, 50005]";

		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

	}

	/**
	 * Test main method given that no possible combination could be made because of the constraints.
	 */
	@Test
	public void testMainBoundByConstraint() {

		String[] args = {"CS3343_data-NoCombinationBecauseOfConstraints.txt"};
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

		Schedule.main(args);

		String expected = "There are 1 possible combinations.\r\nThere is no possible combination i.e. You should change some of the constraints.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test main (No possible combination).
	 */
	@Test
	public void testMainNoCombinations() {
		String[] args = {"CS3343_data.txt"};
		Schedule.main(args);
		String expected = "There is no possible combination i.e. You should remove at least 1 session.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test main with no lectures given.
	 */
	@Test
	public void testMainNoLectures() {
		String[] args = {"CS3343_data-NoLectures.txt"};
		Schedule.main(args);
		String expected = "The input timetable is invalid - insufficient lecture/tutorial (Course: CS3332)";

		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test main with no tutorial sessions given.
	 */
	@Test
	public void testMainNoTutorials() {
		String[] args = {"CS3343_data-NoTutorials.txt"};
		Schedule.main(args);

		String expected = "The input timetable is invalid - insufficient lecture/tutorial (Course: CS3332)";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test main with no arguments.
	 */
	@Test
	public void testMainNoArguments() {
		String[] args = {};
		Schedule.main(args);
		String expected = "Please enter the data file as an argument.";
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

}
