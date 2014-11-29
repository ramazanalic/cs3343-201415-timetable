package testSchedule;

import java.io.*;
import java.util.ArrayList;
import org.junit.*;

import schedule.Timeslot;
import schedule.Timetable;
import schedule.Weekday;
import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class TestSchedule.
 */
public class TestTimetable extends TestCase{

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
	 * Test toString method of Timetable.
	 */
	@Test
	public void testToStringTimetable() {
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

}
