package dooyit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DateTime.Month;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class SearchCommandTest {
	private static final String EMPTY_STRING = "";
	
	LogicController logic;
	SearchCommand searchCommand;
	
	Task floatingTask1;
	Task floatingTask2;
	Task deadlineTask;
	Task eventTask;
	Task floatingTask3;
	
	@Before
	public void setUp(){
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}
	
	public void setupTasks() {
		logic.clearTasks();
		
		int[] date1 = {25, 4, 2016};
		int[] date2 = {30, 4, 2016};
		DateTime dateTimeDeadline = new DateTime(date1, 1000);
		DateTime dateTimeStart = new DateTime(date2, 1200);
		DateTime dateTimeEnd = new DateTime(date2, 1400);

		floatingTask1 = new FloatingTask("hello");
		floatingTask2 = new FloatingTask("go");
		floatingTask3 = new FloatingTask("water");
		deadlineTask = new DeadlineTask("car", dateTimeDeadline);
		eventTask = new EventTask("house", dateTimeStart, dateTimeEnd);

		logic.addTask(floatingTask1);
		logic.addTask(floatingTask2);
		logic.addTask(deadlineTask);
		logic.addTask(eventTask);
		logic.addTask(floatingTask3);
	}
	
	@Test
	public void search_Name_WithResults() {
		setupTasks();
		
		String searchString = "h";
		searchCommand = new SearchCommand(searchString);
		searchCommand.execute(logic);
		
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> floatingTasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		ArrayList<Task> eventTasks = Whitebox.getInternalState(taskGroup.get(1), "tasks");
		
		//floatingTasks should only contain the task with name "hello"
		assertEquals(1, floatingTasks.size());
		
		//eventTasks should only contain the task with name "house"
		assertEquals(1, eventTasks.size());
		
		//Assert that the references are the same.
		assertEquals(floatingTask1, floatingTasks.get(0));
		assertEquals(eventTask, eventTasks.get(0));
	}
	
	@Test
	public void search_Name_NoResults() {
		setupTasks();
		
		String searchString = "haha";
		searchCommand = new SearchCommand(searchString);
		searchCommand.execute(logic);
		
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertEquals(0, tasks.size());
	}
	
	@Test
	public void search_Month_WithResults() {
		setupTasks();
		
		searchCommand = new SearchCommand("april", Month.APR);
		searchCommand.execute(logic);
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> tasks1 = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		ArrayList<Task> tasks2 = Whitebox.getInternalState(taskGroup.get(1), "tasks");
		
		assertEquals(1, tasks1.size());
		assertEquals(1, tasks2.size());
		
		assertEquals(deadlineTask, tasks1.get(0));
		assertEquals(eventTask, tasks2.get(0));
	}
	
	@Test
	public void search_Month_NoResults() {
		setupTasks();
		
		searchCommand = new SearchCommand("january", Month.JAN);
		searchCommand.execute(logic);
		
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertEquals(0, tasks.size());
	}
	
	@Test
	public void search_Date_WithResults() {
		setupTasks();
		
		int[] date = {25, 4, 2016};
		DateTime dt = new DateTime(date);
		searchCommand = new SearchCommand(dt);
		searchCommand.execute(logic);
		
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertEquals(1, tasks.size());
		assertEquals(deadlineTask, tasks.get(0));
	}
	
	@Test
	public void search_Date_NoResults() {
		setupTasks();
		
		int[] date = {17, 4, 2016};
		DateTime dt = new DateTime(date);
		searchCommand = new SearchCommand(dt);
		searchCommand.execute(logic);
		
		TaskManager manager = logic.getTaskManager();
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched();
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertEquals(0, tasks.size());
	}
}
