package dooyit.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
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
	
	Task task1;
	Task task2;
	Task task3;
	Task task4;
	Task task5;
	Task task6;

	@Before
	public void setUp() {
		logicController = new LogicController();
		logicController.disableSave();
		logicController.clearTask();

		taskManager = logicController.getTaskManager();
	}
	
	public void setupTasks() {
		taskManager.clear();
		
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		DateTime deadline = new DateTime();
		
		task1 = taskManager.addEventTask("hello", dateTimeStart, dateTimeEnd);
		task2 = taskManager.addFloatingTask("buy milk");
		task3 = taskManager.addDeadlineTask("goodbye", deadline, true);
		task4 = taskManager.addEventTask("driving lesson", dateTimeStart, dateTimeEnd, true);
		task5 = taskManager.addFloatingTask("Get oreos", true);
		task6 = taskManager.addDeadlineTask("hahaha", deadline);
		
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
	
	//@@author A0124586Y
	@Test
	public void GetSize() {
		taskManager.clear();
		assertEquals(0, taskManager.size());
	}
	
	@Test
	public void GetTaskWithCat() {
		taskManager.clear();
		ArrayList<Task> tasks = new ArrayList<Task> ();	
		Category category = new Category("Personal", CustomColor.BLUE);
		
		Task task1 = (Task) new FloatingTask("Buy milk");
		task1.setCategory(category);
		tasks.add(task1);
			
		Task task2 = (Task) new FloatingTask("Go gym");
		task2.setCategory(category);
		tasks.add(task2);
		
		taskManager.add(task1);
		taskManager.add(task2);
		
		ArrayList<Task> expectedTasks = taskManager.getTasksWithCategory(category);
		
		assertTrue(expectedTasks.equals(tasks));	
	}
	
	@Test
	public void GetIncompletedTask() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task6);
		
		assertTrue(tasks.equals(taskManager.getIncompletedTasks()));
	}
	
	@Test
	public void GetCompletedTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task3);
		tasks.add(task4);
		tasks.add(task5);
		
		assertTrue(tasks.equals(taskManager.getCompletedTasks()));
	}
	
	@Test
	public void GetEventTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task1);
		tasks.add(task4);
		
		assertTrue(tasks.equals(taskManager.getEventTasks()));
	}
	
	@Test
	public void GetIncompletedEventTask() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task1);
		
		assertTrue(tasks.equals(taskManager.getIncompleteEventTasks()));
	}
	
	@Test
	public void GetIncompleteEventTasksToday() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		
		DateTime dateTime = new DateTime();
		tasks.add(task1);
		
		assertTrue(tasks.equals(taskManager.getIncompleteEventTasks(dateTime)));
	}
	
	@Test
	public void GetDeadlineTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task3);
		tasks.add(task6);
		
		assertTrue(tasks.equals(taskManager.getDeadlineTasks()));
	}
	
	
	@Test
	public void GetIncompleteDeadlineTask() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task6);
		ArrayList<Task> expectedTasks = taskManager.getIncompleteDeadlineTasks();
		assertTrue(tasks.equals(expectedTasks));
		
		tasks.add(task3);
		assertFalse(tasks.equals(expectedTasks));
	}
	
	@Test
	public void GetIncompleteDeadlineTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task6);
		
		assertTrue(tasks.equals(taskManager.getIncompleteDeadlineTasks()));
	}
	
	@Test
	public void GetIncompleteDeadlineTaskToday() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task6);
		DateTime dateTime = new DateTime();
		ArrayList<Task> expectedTasks = taskManager.getIncompleteDeadlineTasks(dateTime);
		assertTrue(tasks.equals(expectedTasks));
		
		tasks.add(task3);
		assertFalse(tasks.equals(expectedTasks));
	}
	
	@Test
	public void GetFloatingTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task2);
		tasks.add(task5);
		
		assertTrue(tasks.equals(taskManager.getFloatingTasks()));
	}
	
	@Test
	public void GetIncompleteFloatingTasks() {
		setupTasks();
		
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task2);
		
		assertTrue(tasks.equals(taskManager.getIncompleteFloatingTasks()));
	}
	
	@Test
	public void SortTasks() {
		setupTasks();

		taskManager.sortTask(taskManager.getAllTasks());
		ArrayList<Task> tasks = new ArrayList<Task> ();
		tasks.add(task1);
		tasks.add(task3);
		tasks.add(task4);
		tasks.add(task6);
		tasks.add(task2);
		tasks.add(task5);
		
		assertTrue(tasks.equals(taskManager.getAllTasks()));
	}
}
