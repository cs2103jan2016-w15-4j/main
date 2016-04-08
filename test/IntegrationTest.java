import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.Task.TaskType;
import dooyit.logic.api.LogicController;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.parser.AddParser;
import dooyit.parser.Parser;

public class IntegrationTest {

	LogicController logic;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}
	
	
	@Test
	public void addFloatingTask_ValidInput_FloatingTask(){
		logic.clearTasks();
		logic.processInput("add a");
		
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.FLOATING, task.getTaskType());
		Parser parser = Whitebox.getInternalState(logic, "parser");
		AddParser addParser = Whitebox.getInternalState(parser, "addParser");
		String userInput = Whitebox.getInternalState(addParser, "userInput");
		assertEquals("a", userInput);
		
	}
	
	@Test
	public void addDeadlineTask_ValidDateTime_DeadlineTask(){
		logic.clearTasks();
		logic.processInput("add buy milk by 30/3/2016");	
		
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.DEADLINE, task.getTaskType());
	}
	
	@Test
	public void addEventTask_ValidDateTime_EventTask(){
		logic.clearTasks();
		logic.processInput("add buy milk from 30/3/2016 to 31/3/2016");
		
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.EVENT, task.getTaskType());
	}
	
	@Test
	public void addEventTask_InvalidDateTime_FloatingTask() {
		logic.clearTasks();
		logic.processInput("add buy milk from 30/3/2016");
		
		Task task = logic.getTaskManager().getMostRecentTask();
		assertEquals(TaskType.FLOATING, task.getTaskType());
	}
	
}
