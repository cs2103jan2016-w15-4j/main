package dooyit.common.datatype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Task.TaskType;

public class TaskTest {

	Task taskF1;
	Task taskF2;
	Task taskF3;
	
	Task taskD1;
	Task taskD2;
	Task taskD3;
	
	@Before
	public void setUp(){
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
	public void hasCategory(){
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
	public void getCategory(){
		Category cat = new Category("School");
		taskF1.setCategory(cat);
		taskD1.setCategory(cat);
		assertTrue(taskF1.getCategory().equals(cat));
		assertTrue(taskF1.getCategory().equals(cat));
	}
	
	@Test
	public void resetId(){
		taskF1.resetId();
		taskD1.resetId();
		assertEquals(taskF1.getId(), -1);
		assertEquals(taskD1.getId(), -1);
	}
	
	@Test
	public void getId() {
		
	}
	
	@Test
	public void setId(){
		taskF1.setId(3);
		taskD1.setId(5);
		assertEquals(taskF1.getId(), 3);
		assertEquals(taskD1.getId(), 5);
	}
	
	@Test
	public void getUniqueId() {
		
	}
	
	@Test
	public void setUniqueId(){
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
	
	@Test
	public void testEquals(){
		
		assertTrue(taskF1.equals(taskF2));
		assertFalse(taskF3.equals(taskF1));
		
		assertTrue(taskD1.equals(taskD2));
		//assertFalse(taskD1.equals(taskD3));
		
		String newName = "car";
		taskF1.changeName(newName);
		assertTrue(taskF1.getName().equals(newName));
	
		taskF1.mark();
		assertTrue(taskF1.isCompleted());
		
		taskF1.unMark();
		assertTrue(!taskF1.isCompleted());
	}
	
	
	
}
