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

	LogicController logic;
	//TaskManager taskManager;
	AddCommand addCommand;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();

		//taskManager = logic.getTaskManager();
	}
	
	@Test
	public void testAddFloatingTask(){
		addCommand = new AddCommand("hello");
		addCommand.execute(logic);
		
		Task task = new FloatingTask("hello");
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void testAddDeadlineTask(){
		DateTime dateTime = new DateTime();
		addCommand = new AddCommand("hello", dateTime);
		addCommand.execute(logic);
		
		Task task = new DeadlineTask("hello", dateTime);
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void testAddEventTask(){
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logic);
		
		Task task = new EventTask("hello", dateTimeStart, dateTimeEnd);
		
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void undoAddedTask(){
		addCommand = new AddCommand("hello");
		addCommand.execute(logic);
		
		Task task = new FloatingTask("hello");
		
		//make sure task is inside taskManager
		assertTrue(logic.containsTask(task));
		
		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}
}
