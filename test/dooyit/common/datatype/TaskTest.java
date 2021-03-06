// @@author A0124278A
package dooyit.common.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Wu Wenqi
 *
 */

public class TaskTest {

	Task taskF1;
	Task taskF2;
	Task taskF3;

	Task taskD1;
	Task taskD2;
	Task taskD3;

	@Before
	public void setUp() {
		taskF1 = new FloatingTask("hello");
		taskF2 = new FloatingTask("hello");
		taskF3 = new FloatingTask("home");

		DateTime date = new DateTime();

		taskD1 = new DeadlineTask("hello", date);
		taskD2 = new DeadlineTask("hello", date);
		taskD3 = new DeadlineTask("home", new DateTime());
	}

	@Test
	public void getName() {
		assertEquals(taskF1.getName(), "hello");
		assertEquals(taskD3.getName(), "home");
	}

	@Test
	public void changeName() {
		taskF1.changeName("goodbye");
		taskD1.changeName("goodbye");
		assertEquals(taskF1.getName(), "goodbye");
		assertEquals(taskD1.getName(), "goodbye");
	}

	@Test
	public void isCompleted() {
		assertFalse(taskF1.isCompleted());
		assertFalse(taskD1.isCompleted());
	}

	@Test
	public void mark() {
		taskF1.mark();
		taskD1.mark();
		assertTrue(taskF1.isCompleted());
		assertTrue(taskD1.isCompleted());
	}

	@Test
	public void unMark() {
		taskF1.mark();
		taskF1.unMark();
		assertFalse(taskF1.isCompleted());
	}

	@Test
	public void hasCategory() {
		assertFalse(taskF1.hasCategory());
		assertFalse(taskD1.hasCategory());
	}

	@Test
	public void setCategory() {
		Category cat = new Category("School");
		taskF1.setCategory(cat);
		taskD1.setCategory(cat);
		assertTrue(taskF1.hasCategory());
		assertTrue(taskD1.hasCategory());
	}

	@Test
	public void getCategory() {
		Category cat = new Category("School");
		taskF1.setCategory(cat);
		taskD1.setCategory(cat);
		assertTrue(taskF1.getCategory().equals(cat));
		assertTrue(taskF1.getCategory().equals(cat));
	}

	@Test
	public void resetId() {
		taskF1.resetDisplayId();
		taskD1.resetDisplayId();
		assertEquals(taskF1.getDisplayId(), -1);
		assertEquals(taskD1.getDisplayId(), -1);
	}

	@Test
	public void getId() {

	}

	@Test
	public void setId() {
		taskF1.setDisplayId(3);
		taskD1.setDisplayId(5);
		assertEquals(taskF1.getDisplayId(), 3);
		assertEquals(taskD1.getDisplayId(), 5);
	}

	@Test
	public void getUniqueId() {

	}

	@Test
	public void setUniqueId() {
		taskF1.setUniqueId(3);
		taskD1.setUniqueId(5);
		assertEquals(taskF1.getUniqueId(), 3);
		assertEquals(taskD1.getUniqueId(), 5);
	}

	@Test
	public void getTaskType() {
		assertTrue(taskF1.getTaskType() == Task.TaskType.FLOATING);
		assertTrue(taskD1.getTaskType() == Task.TaskType.DEADLINE);
	}
}
