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
 * The Class TestUtilities.
 */
public class TestUtilities extends TestCase {
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
	 * Test extract by code.
	 */
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
		String code = "CS3443";
		ArrayList<Timeslot> t = Utilities.extractTimeslotsByCode(timeslots, code);
		ArrayList<Timeslot> expected = new ArrayList<Timeslot>();
		expected.add(e);
		expected.add(f);
		assertEquals(expected, t);
	}


	/**
	 * Test extract timeslots by type (both lecture and tutorial).
	 */
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



	/**
	 * Test all courses method in Utilities.
	 */
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

	/**
	 * Test permutate method in Utilities.
	 */
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

	/**
	 * Test permutate arraylist method in Utilities.
	 */
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

	/**
	 * Test generate permutations method in Utilities.
	 */
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


	/**
	 * Test validate input method with duplicate crn.
	 */
	@Test
	public void testValidateInputDuplicateCrn() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40001","CS3332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - duplicate CRN (CRN #40001)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

	}

	/**
	 * Test validate input method with invalid session.
	 */
	@Test
	public void testValidateInputSession() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","KA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - session (CRN #40002: KA1)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

	}

	/**
	 * Test validate input method with invalid building.
	 */
	@Test
	public void testValidateInputBuilding() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC4", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - building code (CRN #40002: AC4)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

	}

	/**
	 * Test validate input method with invalid course start time.
	 */
	@Test
	public void testValidateInputCourseStartTime() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 7, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - course start time (CRN #40001: 7)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
		
		String prev = outContent.toString().replaceAll("\r\n", "").replaceAll("\n", "");
		
		timeslots = new ArrayList<Timeslot>();

		a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 23, 24, Weekday.Mon.getDay());
		b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		result = Utilities.validateInput(timeslots, buildingList);

		expected = "The input timetable is invalid - course start time (CRN #40001: 23)";
		
		assertEquals(result, false);
		assertEquals(prev+expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

		timeslots = new ArrayList<Timeslot>();

		a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 9, 15, Weekday.Mon.getDay());
		b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		result = Utilities.validateInput(timeslots, buildingList);

		assertEquals(result, true);
		

	}

	/**
	 * Test validate input method with invalid course finish time.
	 */
	@Test
	public void testValidateInputCourseFinishTime() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 10, 27, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - course finish time (CRN #40001: 27)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

		String prev = outContent.toString().replaceAll("\r\n", "").replaceAll("\n", "");
		
		timeslots = new ArrayList<Timeslot>();

		a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 9, 5, Weekday.Mon.getDay());
		b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		result = Utilities.validateInput(timeslots, buildingList);

		expected = "The input timetable is invalid - course finish time (CRN #40001: 5)";
		assertEquals(result, false);
		assertEquals(prev+expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));

		timeslots = new ArrayList<Timeslot>();

		a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 9, 10, Weekday.Mon.getDay());
		b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		result = Utilities.validateInput(timeslots, buildingList);

		assertEquals(result, true);
	}

	/**
	 * Test validate input method with invalid course time range.
	 */
	@Test
	public void testValidateInputCourseTimeRange() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 10, 8, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, Weekday.Tue.getDay());

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - course time range (CRN #40001: 10 > 8)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}

	/**
	 * Test validate input method with invalid day.
	 */
	@Test
	public void testValidateInputDay() {

		timeslots = new ArrayList<Timeslot>();
		ArrayList<String> buildingList = new ArrayList<String>();

		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 10, 13, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, 10);

		timeslots.add(a);
		timeslots.add(b);

		boolean result = Utilities.validateInput(timeslots, buildingList);

		String expected = "The input timetable is invalid - day (CRN #40002)";

		assertEquals(result, false);
		assertEquals(expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
		
		String prev = outContent.toString().replaceAll("\r\n", "").replaceAll("\n", "");
		
		timeslots = new ArrayList<Timeslot>();
		a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 10, 13, Weekday.Mon.getDay());
		b = new Timeslot("40002","CS3332","LA1", "AC3", "5503", 13, 16, -1);
		
		timeslots.add(a);
		timeslots.add(b);

		result = Utilities.validateInput(timeslots, buildingList);

		expected = "The input timetable is invalid - day (CRN #40002)";

		assertEquals(result, false);
		assertEquals(prev + expected.replaceAll("\r\n", ""), outContent.toString().replaceAll("\r\n", "").replaceAll("\n", ""));
	}


}
