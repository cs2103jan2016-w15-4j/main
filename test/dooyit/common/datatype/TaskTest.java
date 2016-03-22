package dooyit.common.datatype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		
		assertTrue(taskF1.getTaskType() == Task.TaskType.FLOATING);
		
		assertTrue(taskD1.getTaskType() == Task.TaskType.DEADLINE);
	}
	
	
	
}
