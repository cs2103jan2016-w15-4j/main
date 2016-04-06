package dooyit.common.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @@author Wu Wenqi <A0124278A>
 *
 */

public class DeadlineTaskTest {

	DeadlineTask task1, task2, task3;
	DateTime dt1, dt2;

	@Before
	public void setUp() {
		int[] date = { 22, 2, 2016 };
		dt1 = new DateTime(date, 800);
		task1 = new DeadlineTask("Go shopping", dt1);
		dt2 = new DateTime();
		task2 = new DeadlineTask("Find a job", dt2);
		task3 = new DeadlineTask("Find a job", dt2);
	}

	@Test
	public void getDateTimeDeadline() {
		assertTrue(task1.getDateTimeDeadline().equals(dt1));
	}

	@Test
	public void isToday() {
		assertTrue(task2.isSameDate(dt2));
	}

	@Test
	public void isOverDue() {
		assertTrue(task1.isOverDue(dt2));
		assertFalse(task2.isOverDue(dt2));
	}

	@Test
	public void getDateString() {
		assertEquals(task1.getDateString(), "08:00");
	}

	@Test
	public void testToString() {
		assertEquals("Go shopping, Deadline: 22 Feb 2016 Monday 08:00 8.00 am", task1.toString());
	}
}
