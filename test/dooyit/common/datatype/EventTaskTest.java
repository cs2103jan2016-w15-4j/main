package dooyit.common.datatype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Task.TaskType;

public class EventTaskTest {
	
	EventTask task1;
	EventTask task2;
	EventTask task3;
	DateTime dt1_start, dt1_end;
	DateTime dt2_start, dt2_end;
	DateTime today;
	
	@Before
	public void setUp(){
		int[] date1 = {22, 2, 2016};
		today = new DateTime(date1, 0);
		dt1_start = new DateTime(date1, 800);
		dt1_end = new DateTime(date1, 1800);
		task1 = new EventTask("Beach day", dt1_start, dt1_end);
		int[] date2 = {21, 2, 2016};
		dt2_start = new DateTime(date2, 800);
		dt2_end = new DateTime(date2, 1800);
		task2 = new EventTask("Hiking", dt2_start, dt2_end);
		task3 = new EventTask("Hiking", dt2_start, dt2_end);
	}
	
	@Test
	public void getDateTimeStart(){
		assertTrue(task1.getDateTimeStart().equals(dt1_start));
	}

	@Test
	public void getDateTimeEnd(){
		assertTrue(task1.getDateTimeEnd().equals(dt1_end));
	}

	@Test
	public void isToday(){
		assertTrue(task1.isToday(today));
	}
	
	@Test
	public void isOverDue(){
		assertTrue(task2.isOverDue(today));
		assertFalse(task1.isOverDue(today));
	}
	
	@Test
	public void getDateString(){
		assertEquals(task1.getDateString(), "08:00 - 18:00");
	}
	
	@Test
	public void testToString(){
		assertEquals(task2.toString(), "Hiking, Event: 21 Feb 2016 Sunday 08:00 8.00 amto21 Feb 2016 Sunday 18:00 6.00 pm");
	}

	@Test
	public void testEquals(){
		assertTrue(task2.equals(task3));
		assertFalse(task1.equals(task2));
	}
}
