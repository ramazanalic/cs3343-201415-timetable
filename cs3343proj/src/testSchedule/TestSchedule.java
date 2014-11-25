package testSchedule;

import java.util.ArrayList;

import org.junit.*;

import schedule.Schedule;
import schedule.Timeslot;
import schedule.Weekday;
import junit.framework.TestCase;

public class TestSchedule extends TestCase{

	private ArrayList<Timeslot> timeslots;

	/**
	 * Sets up the test fixture.
	 *
	 * Called before every test case method.
	 */
	@Before
	public void setUp() { timeslots = new ArrayList<Timeslot>(); }

	/**
	 * Tears down the test fixture.
	 *
	 * Called after every test case method.
	 */
	@After
	public void tearDown() {}

	// Test case 1: There exists a session of CS2205 on Friday
	@Test
	public void testCS2205Fri() {
		Schedule.readTimeslots(timeslots, "CS3343_data.txt");
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
		Schedule.readTimeslots(timeslots, "CS3343_data.txt");
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
		
		boolean result = a.overlap(b);
		assertEquals(result, true);
		result = a.overlap(c);
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
		Schedule.readTimeslots(timeslots, "CS3343_data.txt");
		Weekday expected = Weekday.Mon;
		ArrayList<Timeslot> t = Schedule.extractTimeslotsByDay(timeslots, expected);
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
		Schedule.readTimeslots(timeslots, "CS3343_data.txt");
		String expected = "AC2";
		ArrayList<Timeslot> t = Schedule.extractTimeslotsByCode(timeslots, expected);
		boolean result = true;
		for (Timeslot i : t)
			if (!i.getBuilding().equals(expected))
				result = false;
		assertEquals(result, true);
	}
	
	// Test case 10: Test extract by code
	@Test
	public void testExtractByCodeFalse() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Tue.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);
		String expected = "AC2";
		boolean result = true;
		for (Timeslot i : timeslots)
			if (!i.getBuilding().equals(expected))
				result = false;
		assertEquals(result, false);
	}
}
