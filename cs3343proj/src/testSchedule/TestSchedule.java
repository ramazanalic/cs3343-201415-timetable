package testSchedule;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

import schedule.BuildingConstraint;
import schedule.RequiredConstraint;
import schedule.Schedule;
import schedule.TimeConstraint;
import schedule.TimeGapConstraint;
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

	// Test case 11: Test extract by code
	@Test
	public void testRequiredConstraint() {
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

		ArrayList<String> listOfCrns = new ArrayList<String>();
		listOfCrns.add("40001");
		listOfCrns.add("40005");
		RequiredConstraint rc = new RequiredConstraint(timeslots, listOfCrns);
		assertEquals(rc.isFulfilled(), true);

		listOfCrns.clear();
		listOfCrns.add("40001");
		listOfCrns.add("40007");
		rc = new RequiredConstraint(timeslots, listOfCrns);
		assertEquals(rc.isFulfilled(), false);
	}

	// Test case 12: Test time gap constraint
	@Test
	public void testTimeGapConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

		TimeGapConstraint rc = new TimeGapConstraint(timeslots, 3);
		assertEquals(rc.isFulfilled(), true);

		Timeslot f = new Timeslot("40006","CS3301","L01", "AC1", "LT-2", 15, 16, Weekday.Wed.getDay());
		timeslots.add(f);

		rc = new TimeGapConstraint(timeslots, 3);
		assertEquals(rc.isFulfilled(), false);
	}

	//Test case 13: Test required + time gap constraints
	@Test
	public void testRequiredAndTimeGapConstraints() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 14, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 13, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		Timeslot d = new Timeslot("40004","CS3201","CA1", "AC3", "6208", 10, 12, Weekday.Tue.getDay());
		Timeslot e = new Timeslot("40005","CS3443","CB1", "AC1", "LT-2", 12, 16, Weekday.Tue.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

		ArrayList<String> listOfCrns = new ArrayList<String>();
		listOfCrns.add("40001");
		listOfCrns.add("40005");
		RequiredConstraint rc = new RequiredConstraint(timeslots, listOfCrns);
		TimeGapConstraint rc1 = new TimeGapConstraint(timeslots, 3);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), true);

		listOfCrns.clear();
		listOfCrns.add("40001");
		listOfCrns.add("40007");
		rc = new RequiredConstraint(timeslots, listOfCrns);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), false);

		listOfCrns.clear();
		listOfCrns.add("40003");
		listOfCrns.add("40004");
		rc = new RequiredConstraint(timeslots, listOfCrns);
		Timeslot f = new Timeslot("40006","CS3301","L01", "AC1", "LT-2", 15, 16, Weekday.Wed.getDay());
		timeslots.add(f);
		rc1 = new TimeGapConstraint(timeslots, 3);
		assertEquals(rc.isFulfilled() && rc1.isFulfilled(), false);
	}

	//Test case 14: Test time constraint
	@Test
	public void testTimeConstraint() {
		Timeslot a = new Timeslot("40001","CS3332","C01", "AC1", "LT-1", 13, 16, Weekday.Mon.getDay());
		Timeslot b = new Timeslot("40002","CS2332","LA1", "AC2", "5503", 14, 16, Weekday.Tue.getDay());
		Timeslot c = new Timeslot("40003","CS3301","LA1", "AC1", "LT-3", 9, 11.5, Weekday.Wed.getDay());
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);

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

		TimeConstraint rc = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot d = new Timeslot("40004","CS3305","LA1", "AC1", "LT-3", 16, 18, Weekday.Mon.getDay());
		timeslots.add(d);
		rc = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot e = new Timeslot("40005","CS3305","LA1", "AC1", "LT-3", 12, 14, Weekday.Tue.getDay());
		timeslots.add(e);
		rc = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot f = new Timeslot("40006","CS3305","LA1", "AC1", "LT-3", 12, 14, Weekday.Mon.getDay());
		timeslots.add(f);
		rc = new TimeConstraint(timeslots, daytimeExcluded);
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
		timeslots.remove(f);
		rc = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc.isFulfilled(), true);

		Timeslot g = new Timeslot("40007","CS3301","LA1", "AC1", "LT-3", 9, 11, Weekday.Tue.getDay());
		timeslots.add(g);
		rc = new TimeConstraint(timeslots, daytimeExcluded);
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
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

		HashMap<Integer,ArrayList<Double>> daytimeExcluded = new HashMap<Integer,ArrayList<Double>>();

		// Mon: Exclude before 12, after 18
		ArrayList<Double> mon = new ArrayList<Double>();
		mon.addAll(Schedule.beforeTime(12));
		mon.addAll(Schedule.afterTime(18));
		daytimeExcluded.put(1, mon);

		//Tue: Exclude before 9, after 18
		ArrayList<Double> tue = new ArrayList<Double>();
		tue.addAll(Schedule.beforeTime(9));
		tue.addAll(Schedule.afterTime(18));
		daytimeExcluded.put(2, tue);

		TimeConstraint rc1 = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), true);

		//Wed: Exclude between 12 to 14
		ArrayList<Double> wed = new ArrayList<Double>();
		wed.addAll(Schedule.betweenTime(12, 14));
		daytimeExcluded.put(3, wed);

		rc1 = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), true);

		
		
		//Wed: Exclude before 13
		wed = new ArrayList<Double>();
		wed.addAll(Schedule.beforeTime(13));
		daytimeExcluded.put(3, wed);

		rc1 = new TimeConstraint(timeslots, daytimeExcluded);
		assertEquals(rc1.isFulfilled(), false);
				
		wed = new ArrayList<Double>();
		wed.addAll(Schedule.beforeTime(9));
		wed.addAll(Schedule.beforeTime(8));
		wed.addAll(Schedule.beforeTime(7));
		daytimeExcluded.put(3, wed);
		
		rc1 = new TimeConstraint(timeslots, daytimeExcluded);
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
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

		ArrayList<String> listOfBuildings = new ArrayList<String>();
		listOfBuildings.add("CMC");
		
		BuildingConstraint rc = new BuildingConstraint(timeslots, listOfBuildings);
		assertEquals(rc.isFulfilled(), true);
		
		listOfBuildings.add("AC3");
		rc = new BuildingConstraint(timeslots, listOfBuildings);
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
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

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
		timeslots.add(a);
		timeslots.add(b);
		timeslots.add(c);
		timeslots.add(d);
		timeslots.add(e);

		ArrayList<Timeslot> extracted = Schedule.extractTimeslotsByType(timeslots, "Lecture");
		ArrayList<Timeslot> lectures = new ArrayList<Timeslot>();
		lectures.add(a);
		lectures.add(d);
		lectures.add(e);
		assertEquals(extracted.equals(lectures), true);
		
		extracted = Schedule.extractTimeslotsByType(timeslots, "Tutorial");
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
	
}
