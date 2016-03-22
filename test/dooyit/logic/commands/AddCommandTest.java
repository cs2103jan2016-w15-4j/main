package dooyit.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class AddCommandTest {

	LogicController logicController;
	TaskManager taskManager;
	AddCommand addCommand;
	
	@Before
	public void setUp() {
		logicController = new LogicController();
		logicController.disableSave();
		logicController.clearTask();

		taskManager = logicController.getTaskManager();
	}
	
	
	@Test
	public void testAddFloatingTask(){
		addCommand = new AddCommand("hello");
		addCommand.execute(logicController);
		
		Task task = new FloatingTask("hello");
		
		assertTrue(taskManager.contains(task));
	}
	
	@Test
	public void testAddDeadlineTask(){
		DateTime dateTime = new DateTime();
		addCommand = new AddCommand("hello", dateTime);
		addCommand.execute(logicController);
		
		Task task = new DeadlineTask("hello", dateTime);
		
		assertTrue(taskManager.contains(task));
	}
	
	@Test
	public void testAddEventTask(){
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logicController);
		
		Task task = new EventTask("hello", dateTimeStart, dateTimeEnd);
		
		assertTrue(taskManager.contains(task));
	}
	
	@Test
	public void undoAddedTask(){
		addCommand = new AddCommand("hello");
		addCommand.execute(logicController);
		
		Task task = new FloatingTask("hello");
		
		//make sure task is inside taskManager
		assertTrue(taskManager.contains(task));
		
		addCommand.undo(logicController);
		assertFalse(taskManager.contains(task));
	}
}
