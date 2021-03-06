//@@author A0126356E
package dooyit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

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
	public void addFloatingTask() {
		addCommand = new AddTaskCommand("hello");
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		assertTrue(logic.containsTask(task));
	}

	@Test
	public void addDeadlineTask() {
		DateTime dateTime = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTime);
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		assertTrue(logic.containsTask(task));
	}

	@Test
	public void addEventTask() {
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		assertTrue(logic.containsTask(task));
	}

	@Test
	public void undoAddedFloatingTask() {
		addCommand = new AddTaskCommand("hello");
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		// make sure task is inside taskManager
		assertTrue(logic.containsTask(task));

		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}

	@Test
	public void undoAddedDeadlineTask() {
		DateTime dateTime = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTime);
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		// make sure task is inside taskManager
		assertTrue(logic.containsTask(task));

		addCommand.undo(logic);
		assertFalse(logic.containsTask(task));
	}

	@Test
	public void undoAddedEventTask() {
		DateTime dateTimeStart = new DateTime();
		DateTime dateTimeEnd = new DateTime();
		addCommand = new AddTaskCommand("hello", dateTimeStart, dateTimeEnd);
		addCommand.execute(logic);

		Task task = getMostRecentTask();

		// make sure task is inside taskManager
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
