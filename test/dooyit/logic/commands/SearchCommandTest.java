package dooyit.logic.commands;

import static org.junit.Assert.assertEquals;
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
	public void search_Month_NoResults() {
		
	}
}
