//@@author A0124586Y
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.Task.TaskType;
import dooyit.logic.api.LogicController;
import dooyit.parser.AddParser;
import dooyit.parser.ParserController;

public class IntegrationTest {

	LogicController logic;

	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}

	@Test
	public void addFloatingTask_ValidInput_FloatingTask() {
		logic.clearTasks();
		logic.processInput("add a");

		// Checking Logic
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.FLOATING, task.getTaskType());

		// Checking Parser
		ParserController parser = Whitebox.getInternalState(logic, "parserController");
		AddParser addParser = Whitebox.getInternalState(parser, "addParser");

		String userInput = Whitebox.getInternalState(addParser, "userInput");
		assertEquals("a", userInput);

		String taskName = Whitebox.getInternalState(addParser, "taskName");
		assertEquals("a", taskName);

	}

	@Test
	public void addDeadlineTask_ValidDateTime_DeadlineTask() {
		logic.clearTasks();
		logic.processInput("add buy milk by 30/3/2016");

		// Checking Logic
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.DEADLINE, task.getTaskType());

		// Checking Parser
		ParserController parser = Whitebox.getInternalState(logic, "parserController");
		AddParser addParser = Whitebox.getInternalState(parser, "addParser");

		String userInput = Whitebox.getInternalState(addParser, "userInput");
		assertEquals("buy milk by 30/3/2016", userInput);

		String taskName = Whitebox.getInternalState(addParser, "taskName");
		assertEquals("buy milk", taskName);

		DateTime dt = Whitebox.getInternalState(addParser, "deadline");
		DateTime expectedDateTime = new DateTime(new int[] { 30, 3, 2016 });
		assertTrue(dt.equals(expectedDateTime));
	}

	@Test
	public void addEventTask_ValidDateTime_EventTask() {
		logic.clearTasks();
		logic.processInput("add buy milk from 30/3/2016 to 31/3/2016");

		// Checking Logic
		Task task = logic.getTaskManager().getMostRecentTask();
		assertTrue(logic.containsTask(task));
		assertEquals(TaskType.EVENT, task.getTaskType());

		// Checking Parser
		ParserController parser = Whitebox.getInternalState(logic, "parserController");
		AddParser addParser = Whitebox.getInternalState(parser, "addParser");

		String userInput = Whitebox.getInternalState(addParser, "userInput");
		assertEquals("buy milk from 30/3/2016 to 31/3/2016", userInput);

		String taskName = Whitebox.getInternalState(addParser, "taskName");
		assertEquals("buy milk", taskName);

		DateTime start = Whitebox.getInternalState(addParser, "start");
		DateTime expectedStart = new DateTime(new int[] { 30, 3, 2016 }, 0);
		assertTrue(start.equals(expectedStart));

		DateTime end = Whitebox.getInternalState(addParser, "end");
		DateTime expectedEnd = new DateTime(new int[] { 31, 3, 2016 }, 2359);
		assertTrue(end.equals(expectedEnd));
	}

	@Test
	public void addEventTask_InvalidDateTime_FloatingTask() {
		logic.clearTasks();
		logic.processInput("add buy milk from 30/3/2016");

		// Checking Logic
		Task task = logic.getTaskManager().getMostRecentTask();
		assertEquals(TaskType.FLOATING, task.getTaskType());

		// Checking Parser
		ParserController parser = Whitebox.getInternalState(logic, "parserController");
		AddParser addParser = Whitebox.getInternalState(parser, "addParser");

		String userInput = Whitebox.getInternalState(addParser, "userInput");
		assertEquals("buy milk from 30/3/2016", userInput);

		String taskName = Whitebox.getInternalState(addParser, "taskName");
		assertEquals("buy milk from 30/3/2016", taskName);
	}

}
