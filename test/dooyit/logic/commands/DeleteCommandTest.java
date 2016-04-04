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
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

public class DeleteCommandTest {

	LogicController logic;
	DeleteTaskCommand deleteCommand;

	Task floatingTask1;
	Task floatingTask2;
	Task deadlineTask;
	Task eventTask;
	Task floatingTask3;

	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();
	}

	public void setUpTask() {
		logic.clearTask();

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
	public void executeSingleDeleteOfFloatingTask() {
		setUpTask();

		// make sure task1 is inside task manager
		assertTrue(logic.containsTask(floatingTask1));

		// delete task1
		deleteCommand = new DeleteTaskCommand(floatingTask1.getId());
		deleteCommand.execute(logic);
		// try to find task1
		assertFalse(logic.containsTask(floatingTask1));

		// tasks left in taskManager: task2, task3, task4, task5
		// make sure task2 task3, task 4, task 5 is still inside task manager
		// after deletion
		assertTrue(logic.containsTask(floatingTask2));
		assertTrue(logic.containsTask(floatingTask3));
		assertTrue(logic.containsTask(deadlineTask));
		assertTrue(logic.containsTask(eventTask));
	}

	@Test
	public void executeSingleDeleteOfDeadlineTask() {
		setUpTask();

		// make sure task1 is inside task manager
		assertTrue(logic.containsTask(deadlineTask));

		// delete task1
		deleteCommand = new DeleteTaskCommand(deadlineTask.getId());
		deleteCommand.execute(logic);
		// try to find task1
		assertFalse(logic.containsTask(deadlineTask));

		// tasks left in taskManager: task2, task3, task4, task5
		// make sure task2 task3, task 4, task 5 is still inside task manager
		// after deletion
		assertTrue(logic.containsTask(floatingTask1));
		assertTrue(logic.containsTask(floatingTask2));
		assertTrue(logic.containsTask(floatingTask3));
		assertTrue(logic.containsTask(eventTask));
	}

	@Test
	public void executeSingleDeleteOfEventTask() {
		setUpTask();

		// make sure task1 is inside task manager
		assertTrue(logic.containsTask(eventTask));

		// delete task1
		deleteCommand = new DeleteTaskCommand(eventTask.getId());
		deleteCommand.execute(logic);
		// try to find task1
		assertFalse(logic.containsTask(eventTask));

		// tasks left in taskManager: task2, task3, task4, task5
		// make sure task2 task3, task 4, task 5 is still inside task manager
		// after deletion
		assertTrue(logic.containsTask(floatingTask1));
		assertTrue(logic.containsTask(floatingTask2));
		assertTrue(logic.containsTask(floatingTask3));
		assertTrue(logic.containsTask(deadlineTask));
	}

	@Test
	public void executeBatchDelete() {
		setUpTask();

		// delete tasks in batch using 1 command
		ArrayList<Integer> deleteIds = new ArrayList<Integer>();
		deleteIds.add(floatingTask2.getId());
		deleteIds.add(deadlineTask.getId());
		deleteIds.add(eventTask.getId());

		// delete task1
		deleteCommand = new DeleteTaskCommand(deleteIds);
		deleteCommand.execute(logic);

		// try to find task2, task3, task4
		assertFalse(logic.containsTask(floatingTask2));
		assertFalse(logic.containsTask(deadlineTask));
		assertFalse(logic.containsTask(eventTask));

		// make sure task5 is still inside taskManager
		assertTrue(logic.containsTask(floatingTask1));
		assertTrue(logic.containsTask(floatingTask3));
	}

	// boundary case for negative partition
	@Test(expected = IncorrectInputException.class)
	public void executeExceptionHandlingNegative() throws IncorrectInputException {
		setUpTask();
		int invalidTaskId;

		// delete taskId -1
		invalidTaskId = -1;
		// try to find invalid task
		assertFalse(logic.containsTask(invalidTaskId));

		deleteCommand = new DeleteTaskCommand(invalidTaskId);
		deleteCommand.execute(logic);
	}

	// boundary case for positive partition
	@Test(expected = IncorrectInputException.class)
	public void executeExceptionHandlingPositive() throws IncorrectInputException {
		setUpTask();
		int invalidTaskId;

		// delete taskId 100
		invalidTaskId = 100;
		// try to find invalid task
		assertFalse(logic.containsTask(invalidTaskId));

		deleteCommand = new DeleteTaskCommand(invalidTaskId);
		deleteCommand.execute(logic);
	}

	@Test
	public void undoDeletedFloatingTask() {
		setUpTask();

		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(floatingTask1));

		deleteCommand = new DeleteTaskCommand(floatingTask1.getId());
		deleteCommand.execute(logic);

		// make sure task1 is deleted
		assertFalse(logic.containsTask(floatingTask1));

		deleteCommand.undo(logic);
		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(floatingTask1));
	}

	@Test
	public void undoDeletedDeadlineTask() {
		setUpTask();

		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(deadlineTask));

		deleteCommand = new DeleteTaskCommand(deadlineTask.getId());
		deleteCommand.execute(logic);

		// make sure task1 is deleted
		assertFalse(logic.containsTask(deadlineTask));

		deleteCommand.undo(logic);
		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(deadlineTask));
	}

	@Test
	public void undoDeletedEventTask() {
		setUpTask();

		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(eventTask));

		deleteCommand = new DeleteTaskCommand(eventTask.getId());
		deleteCommand.execute(logic);

		// make sure task1 is deleted
		assertFalse(logic.containsTask(eventTask));

		deleteCommand.undo(logic);
		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(eventTask));
	}

	@Test
	public void undoBatchDelete() {
		setUpTask();

		// delete tasks in batch using 1 command
		ArrayList<Integer> deleteIds = new ArrayList<Integer>();
		deleteIds.add(floatingTask2.getId());
		deleteIds.add(deadlineTask.getId());
		deleteIds.add(eventTask.getId());

		// delete task1
		deleteCommand = new DeleteTaskCommand(deleteIds);
		deleteCommand.execute(logic);

		// try to find task2, task3, task4
		assertFalse(logic.containsTask(floatingTask2));
		assertFalse(logic.containsTask(deadlineTask));
		assertFalse(logic.containsTask(eventTask));

		deleteCommand.undo(logic);
		// try to find task2, task3, task4
		assertTrue(logic.containsTask(floatingTask2));
		assertTrue(logic.containsTask(deadlineTask));
		assertTrue(logic.containsTask(eventTask));
	}

}
