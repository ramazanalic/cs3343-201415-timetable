package testSchedule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//import testSchedule.TestSchedule;
//import testSchedule.TestTimeslot;

/**
 * The Class AllTests.
 */
@RunWith(Suite.class)
@SuiteClasses({ TestTimeslot.class,
        TestTimetable.class,
        TestUtilities.class,
        TestWeekday.class,
        TestIO.class,
        TestConstraints.class,
        OtherIntegrationTests.class,
        TestSchedule.class
})

public class AllTests {
}
