package dooyit.logic.commands;

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

public class AddCommandTest {

	LogicController logic;
	AddTaskCommand addCommand;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}
	
	@Test
	public void addFloatingTask(){
		addCommand = new AddTaskCommand("hello");
		addCommand.execute(logic);
		
		//Task task = new FloatingTask("hello");
		Task task = getMostRecentTask();
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void addDeadlineTask(){
		DateTime dateTime = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTime);
		addCommand.execute(logic);
		
		//Task task = new DeadlineTask("hello", dateTime);
		Task task = getMostRecentTask();
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void addEventTask(){
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logic);
		
		//Task task = new EventTask("hello", dateTimeStart, dateTimeEnd);
		Task task = getMostRecentTask();
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void undoAddedFloatingTask(){
		addCommand = new AddTaskCommand("hello");
		addCommand.execute(logic);
		
		//Task task = new FloatingTask("hello");
		Task task = getMostRecentTask();
		
		//make sure task is inside taskManager
		assertTrue(logic.containsTask(task));
		
		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}
	
	@Test
	public void undoAddedDeadlineTask(){
		DateTime dateTime = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTime);
		addCommand.execute(logic);
		
		
		//Task task = new DeadlineTask("hello", dateTime);
		Task task = getMostRecentTask();
		
		//make sure task is inside taskManager
		assertTrue(logic.containsTask(task));
		
		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}
	
	@Test
	public void undoAddedEventTask(){
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logic);
		
		Task task = getMostRecentTask();
		
		//Task task = new EventTask("hello", dateTimeStart, dateTimeEnd);
		
		//make sure task is inside taskManager
		assertTrue(logic.containsTask(task));
		
		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}
	
	private Task getMostRecentTask() {
		TaskManager manager = logic.getTaskManager();
		Task task = manager.getMostRecentTask();
		
		return task;
	}
}
