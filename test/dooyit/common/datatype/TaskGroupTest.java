package dooyit.common.datatype;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TaskGroupTest {
	
	TaskGroup taskGroup;
	TaskGroup taskGroup2;
	ArrayList<Task> taskGroupTasks;
	ArrayList<Task> tasks;
	DeadlineTask task1, task2, task3;
	DateTime dt;
	
	@Before
	public void setUp(){
		int[] date = {22, 2, 2016};
		dt = new DateTime(date, 1800);
		task1 = new DeadlineTask("Dinner at Tiffany's", dt);
		task2 = new DeadlineTask("Shopping at Ion", dt);
		task3 = new DeadlineTask("Movie date", dt);
		tasks = new ArrayList<Task>();
		tasks.add(task2);
		tasks.add(task3);
		taskGroup = new TaskGroup("Today", dt);
		taskGroup2 = new TaskGroup("Floating");
	}

	@Test
	public void addTask() {
		taskGroup.addTask(task1);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 1);
		assertTrue(taskGroupTasks.get(0).equals(task1));
	}

	@Test
	public void addTasks() {
		taskGroup.addTasks(tasks);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 2);
		assertTrue(taskGroupTasks.get(0).equals(task2));
		assertTrue(taskGroupTasks.get(1).equals(task3));
	}

	@Test
	public void getDateTime() {
		assertTrue(taskGroup.getDateTime().equals(dt));
	}

	@Test
	public void getTitle() {
		assertEquals(taskGroup.getTitle(), "Today, 22 Feb");
	}
	
	@Test
	public void setTitle() {
		taskGroup.setTitle("This day");
		assertEquals(taskGroup.getTitle(), "This day, 22 Feb");
	}

	@Test
	public void getTasks() {
		taskGroup.addTasks(tasks);
		taskGroupTasks = taskGroup.getTasks();
		assertEquals(taskGroupTasks.size(), 2);
		assertTrue(taskGroupTasks.get(0).equals(task2));
		assertTrue(taskGroupTasks.get(1).equals(task3));
	}
	
	@Test
	public void hasDateTime() {
		assertTrue(taskGroup.hasDateTime());
		assertFalse(taskGroup2.hasDateTime());
	}
}
