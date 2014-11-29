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
 * The Class TestIO.
 */
public class TestIO extends TestCase {
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
	 * Test printSchedule method in IO.
	 */
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

		String expected =
				"                                       |-------------------------|                                       \n" + 
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


	/**
	 * Test printCRNs method in IO.
	 */
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

	/**
	 * Test read required constraints method in IO.
	 */
	@Test
	public void testReadRequiredConstraints() {
		ArrayList<String> crns = IO.readRequiredConstraints("TheseCRNsMustBeIncluded.txt");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("50005");
		expected.add("60002");

		assertEquals(expected, crns);
	}

	// Test case 32: Test readRequiredConstraints
	/**
	 * Test read required constraints method in IO with invalid data.
	 */
	@Test
	public void testReadRequiredConstraintsFail() {
		ArrayList<String> crns = IO.readRequiredConstraints(".txt");
		ArrayList<String> expected = new ArrayList<String>();

		assertEquals(expected, crns);
		assertEquals("You should input valid data in \"TheseCRNsMustBeIncluded.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test read building constraints method in IO.
	 */
	@Test
	public void testReadBuildingConstraints() {

		ArrayList<String> bldgs = IO.readBuildingConstraints("NoClassInTheseBuildings.txt");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("CMC");
		expected.add("AC3");

		assertEquals(expected, bldgs);
	}

	/**
	 * Test read building constraints method in IO with invalid data.
	 */
	@Test
	public void testReadBuildingConstraintsFail() {

		ArrayList<String> bldgs = IO.readBuildingConstraints("");
		ArrayList<String> expected = new ArrayList<String>();

		assertEquals(expected, bldgs);
		assertEquals("You should input valid data in \"NoClassInTheseBuildings.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test read time gap constraint method in IO.
	 */
	@Test
	public void testReadTimeGapConstraint() {

		double gapTime = IO.readTimeGapConstraint("MaxTimeBetween2Sessions.txt");
		assertEquals(2.0, gapTime);
	}

	/**
	 * Test read time gap constraint method in IO with invalid data.
	 */
	@Test
	public void testReadTimeGapConstraintFail() {

		double gapTime = IO.readTimeGapConstraint("MaxTimeBetween2Sessions-Error.txt");
		assertEquals(-1.0, gapTime);
		assertEquals("You should input a valid number to indicate the maximum time between 2 sessions in \"MaxTimeBetween2Sessions.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test read time gap constraint method in IO with invalid file name.
	 */
	@Test
	public void testReadTimeGapConstraintError() {

		double gapTime = IO.readTimeGapConstraint("NotExist.txt");
		assertEquals(-1.0, gapTime);
		assertEquals("You should input a valid number to indicate the maximum time between 2 sessions in \"MaxTimeBetween2Sessions.txt\".", outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test read time gap constraint with an empty file.
	 */
	@Test
	public void testReadTimeGapConstraintEmpty() {

		double gapTime = IO.readTimeGapConstraint("Empty.txt");
		assertEquals(-1.0, gapTime);
	}

	/**
	 * Test read building constraints with an empty file.
	 */
	@Test
	public void testReadBuildingConstraintsEmpty() {
		ArrayList<String> bldgs = IO.readBuildingConstraints("Empty.txt");
		assertEquals(new ArrayList<String>(), bldgs);
	}

	/**
	 * Test read required constraints with an empty file.
	 */
	@Test
	public void testReadRequiredConstraintsEmpty() {
		ArrayList<String> crns = IO.readRequiredConstraints("Empty.txt");
		assertEquals(new ArrayList<String>(), crns);
	}


	/**
	 * Test read time constraints with dayoff value.
	 */
	@Test
	public void testReadTimeConstraintsDayOff() {

		HashMap<Integer, ArrayList<Double>> timeExcluded = IO.readTimeConstraints("TimeConstraints-Dayoff.txt");

		ArrayList<Double> expected = new ArrayList<Double>();
		expected.add(8.0);
		expected.add(9.0);
		expected.add(10.0);
		expected.add(11.0);
		expected.add(12.0);
		expected.add(13.0);
		expected.add(14.0);
		expected.add(15.0);
		expected.add(16.0);
		expected.add(17.0);
		expected.add(18.0);
		expected.add(19.0);
		expected.add(20.0);
		expected.add(21.0);
		expected.add(22.0);

		assertEquals(timeExcluded.get(4), expected);
	}

	/**
	 * Test read time constraints with before value.
	 */
	@Test
	public void testReadTimeConstraintsBefore() {

		HashMap<Integer, ArrayList<Double>> timeExcluded = IO.readTimeConstraints("TimeConstraints-Before.txt");
		HashMap<Integer, ArrayList<Double>> expected = new HashMap<Integer, ArrayList<Double>>();
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(8.0);
		temp.add(9.0);
		expected.put(0, temp);
		expected.put(2, temp);
		expected.put(3, temp);
		expected.put(4, temp);
		expected.put(5, temp);
		expected.put(6, temp);
		ArrayList<Double> temp2 = new ArrayList<Double>();
		temp2.add(8.0);
		temp2.add(9.0);
		temp2.add(10.0);
		temp2.add(11.0);
		expected.put(1, temp2);

		assertEquals(timeExcluded, expected);
	}

	/**
	 * Test read time constraints with after value.
	 */
	@Test
	public void testReadTimeConstraintsAfter() {

		HashMap<Integer, ArrayList<Double>> timeExcluded = IO.readTimeConstraints("TimeConstraints-After.txt");
		HashMap<Integer, ArrayList<Double>> expected = new HashMap<Integer, ArrayList<Double>>();
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(18.0);
		temp.add(19.0);
		temp.add(20.0);
		temp.add(21.0);
		temp.add(22.0);
		expected.put(0, temp);
		expected.put(1, temp);
		expected.put(2, temp);
		expected.put(3, temp);
		expected.put(4, temp);
		expected.put(6, temp);
		ArrayList<Double> temp2 = new ArrayList<Double>();
		temp2.add(16.0);
		temp2.add(17.0);
		temp2.add(18.0);
		temp2.add(19.0);
		temp2.add(20.0);
		temp2.add(21.0);
		temp2.add(22.0);
		expected.put(5, temp2);

		assertEquals(timeExcluded, expected);
	}

	/**
	 * Test read time constraints with between value.
	 */
	@Test
	public void testReadTimeConstraintsBetween() {

		HashMap<Integer, ArrayList<Double>> timeExcluded = IO.readTimeConstraints("TimeConstraints-Between.txt");
		HashMap<Integer, ArrayList<Double>> expected = new HashMap<Integer, ArrayList<Double>>();
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(12.0);
		expected.put(0, temp);
		expected.put(1, temp);
		expected.put(2, temp);
		expected.put(3, temp);
		expected.put(5, temp);
		expected.put(6, temp);
		ArrayList<Double> temp2 = new ArrayList<Double>();
		temp2.add(12.0);
		temp2.add(15.0);
		expected.put(4, temp2);

		assertEquals(timeExcluded, expected);
	}

	/**
	 * Test read time constraints with an error keyword.
	 */
	@Test
	public void testReadTimeConstraintsError() {

		HashMap<Integer, ArrayList<Double>> timeExcluded = IO.readTimeConstraints("TimeConstraints-Error.txt");

		String expected = "You should input valid data in \"TimeConstraints.txt\".";

		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

}
