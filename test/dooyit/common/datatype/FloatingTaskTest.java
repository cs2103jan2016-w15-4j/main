// @@author A0124278A
package dooyit.common.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Wu Wenqi
 *
 */

public class FloatingTaskTest {

	FloatingTask task1;
	FloatingTask task2;
	FloatingTask task3;
	DateTime today;

	@Before
	public void setUp() {
		today = new DateTime();
		task1 = new FloatingTask("Get a scooter");
		task2 = new FloatingTask("Get a scooter");
		task3 = new FloatingTask("Go skydiving");
	}

	@Test
	public void isToday() {
		assertFalse(task1.isSameDate(today));
	}

	@Test
	public void isOverDue() {
		assertFalse(task1.isOverDue(today));
	}

	@Test
	public void getDateString() {
		assertEquals(task1.getDateString(), "");
	}

	/*@Test
	public void testToString() {
		assertEquals(task1.toString(), "Get a scooter");
	}*/

}
