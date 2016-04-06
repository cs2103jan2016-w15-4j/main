package dooyit.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

public class SearchCommandTest {
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

		DateTime dateTimeDeadline = new DateTime();
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();

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
	public void search_WithResults() {
		setupTasks();
		
		ArrayList<Task> expectedTasks = new ArrayList<Task>();
		expectedTasks.add(floatingTask1);
		expectedTasks.add(eventTask);
		
		String searchString = "h";
		searchCommand = new SearchCommand(searchString);
		searchCommand.execute(logic);
		
		TaskManager manager = getTaskManager(logic);
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched(searchString);
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertTrue(expectedTasks.equals(tasks));
	}
	
	@Test
	public void search_NoResults() {
		setupTasks();
		
		ArrayList<Task> expectedTasks = new ArrayList<Task>();
		
		String searchString = "haha";
		searchCommand = new SearchCommand(searchString);
		searchCommand.execute(logic);
		
		TaskManager manager = getTaskManager(logic);
		ArrayList<TaskGroup> taskGroup = manager.getTaskGroupSearched(searchString);
		ArrayList<Task> tasks = Whitebox.getInternalState(taskGroup.get(0), "tasks");
		assertTrue(expectedTasks.equals(tasks));
	}
	
	private TaskManager getTaskManager(LogicController logic) {
		return logic.getTaskManager();
	}
	
}
