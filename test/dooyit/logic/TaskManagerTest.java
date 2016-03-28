package dooyit.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

public class TaskManagerTest {

	LogicController logicController;
	TaskManager taskManager;

	@Before
	public void setUp() {
		logicController = new LogicController();
		logicController.disableSave();
		logicController.clearTask();

		taskManager = logicController.getTaskManager();
	}

	@Test
	public void addTaskTest() {
		taskManager.clear();
		
		Task task1 = new FloatingTask("hi");
		taskManager.add(task1);
		assertTrue(taskManager.contains(task1));
	}

	@Test
	public void findAndContainTest() {
		taskManager.clear();
		
		DateTime dateTimeDeadline = new DateTime();
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();

		Task task1 = new FloatingTask("hi");
		Task task2 = new DeadlineTask("hello", dateTimeDeadline);
		Task task3 = new EventTask("car", dateTimeStart, dateTimeEnd);
		Task task4 = new EventTask("house", dateTimeStart, dateTimeEnd);
		
		taskManager.add(task1);
		taskManager.add(task2);
		taskManager.add(task3);

		assertFalse(taskManager.contains(task4));
		assertTrue(taskManager.find(task4) == null);
		
		assertTrue(taskManager.contains(task1.getId()));
		assertTrue(taskManager.contains(task1));
		assertTrue(taskManager.find(task1.getId()).equals(task1));
		assertTrue(taskManager.find(task1).equals(task1));

		assertTrue(taskManager.contains(task2.getId()));
		assertTrue(taskManager.contains(task2));
		assertTrue(taskManager.find(task2.getId()).equals(task2));
		assertTrue(taskManager.find(task2).equals(task2));

		assertTrue(taskManager.contains(task3.getId()));
		assertTrue(taskManager.contains(task3));
		assertTrue(taskManager.find(task3.getId()).equals(task3));
		assertTrue(taskManager.find(task3).equals(task3));
		
		assertFalse(taskManager.contains(task4));
		assertTrue(taskManager.find(task4) == null);
	}

	@Test
	public void removeTaskTest() {
		taskManager.clear();
		boolean removeSuccessful;

		Task task1 = new FloatingTask("hi");
		removeSuccessful = taskManager.remove(task1);
		assertFalse(removeSuccessful);
		
		
		taskManager.add(task1);
		removeSuccessful = taskManager.remove(task1);
		assertTrue(removeSuccessful);
		assertFalse(taskManager.contains(task1));
	}

	@Test
	public void loadTaskTest() {
		taskManager.clear();
		
		DateTime dateTimeDeadline = new DateTime();
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();

		Task task1 = new FloatingTask("hi");
		Task task2 = new DeadlineTask("hello", dateTimeDeadline);
		Task task3 = new EventTask("car", dateTimeStart, dateTimeEnd);

		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);

		taskManager.load(tasks);

		assertTrue(taskManager.contains(task1));
		assertTrue(taskManager.contains(task2));
		assertTrue(taskManager.contains(task3));
	}

	@Test
	public void addFloatingTaskTest() {
		taskManager.clear();
		
		Task task1 = taskManager.addFloatingTask("hello");
		assertTrue(!task1.isCompleted());
		assertTrue(taskManager.contains(task1));
		assertTrue(task1 instanceof FloatingTask);

		Task task2 = taskManager.addFloatingTask("hello", true);
		assertTrue(task2.isCompleted());
		assertTrue(taskManager.contains(task2));
		assertTrue(task2 instanceof FloatingTask);
	}

	@Test
	public void addDeadlineTaskTest() {
		taskManager.clear();
		
		DateTime dateTime = new DateTime();

		Task task1 = taskManager.addDeadlineTask("hello", dateTime);
		assertTrue(!task1.isCompleted());
		assertTrue(taskManager.contains(task1));
		assertTrue(task1 instanceof DeadlineTask);

		Task task2 = taskManager.addDeadlineTask("hello", dateTime, true);
		assertTrue(task2.isCompleted());
		assertTrue(taskManager.contains(task2));
		assertTrue(task2 instanceof DeadlineTask);
	}

	@Test
	public void addEventTaskTest() {
		taskManager.clear();
		
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();

		Task task1 = taskManager.addEventTask("hello", dateTimeStart, dateTimeEnd);
		assertTrue(!task1.isCompleted());
		assertTrue(taskManager.contains(task1));
		assertTrue(task1 instanceof EventTask);

		Task task2 = taskManager.addEventTask("hello", dateTimeStart, dateTimeEnd, true);
		assertTrue(task2.isCompleted());
		assertTrue(taskManager.contains(task2));
		assertTrue(task2 instanceof EventTask);
	}

}
