package dooyit.logic.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class DeleteCommandTest {

	LogicController logic;
	DeleteCommand deleteCommand;

	Task task1;
	Task task2;
	Task task3;
	Task task4;
	Task task5;

	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();
	}

	public void setUpTask() {
		logic.clearTask();

		task1 = new FloatingTask("hello");
		task2 = new FloatingTask("go");
		task3 = new DeadlineTask("car", new DateTime());
		task4 = new FloatingTask("house");
		task5 = new FloatingTask("water");
		
		logic.addTask(task1);
		logic.addTask(task2);
		logic.addTask(task3);
		logic.addTask(task4);
		logic.addTask(task5);
	}

	@Test
	public void execute() {
		setUpTask();

		// make sure task1 is inside task manager
		assertTrue(logic.containsTask(task1));

		// delete task1
		deleteCommand = new DeleteCommand(task1.getId());
		deleteCommand.execute(logic);
		// try to find task1
		assertFalse(logic.containsTask(task1));

		// tasks left in taskManager: task2, task3, task4, task5
		// make sure task2 task3, task 4, task 5 is still inside task manager after deletion
		assertTrue(logic.containsTask(task2));
		assertTrue(logic.containsTask(task3));
		assertTrue(logic.containsTask(task4));
		assertTrue(logic.containsTask(task5));

		// delete tasks in batch using 1 command
		ArrayList<Integer> deleteIds = new ArrayList<Integer>();
		deleteIds.add(task2.getId());
		deleteIds.add(task3.getId());
		deleteIds.add(task4.getId());

		// delete task1
		deleteCommand = new DeleteCommand(deleteIds);
		deleteCommand.execute(logic);

		// try to find task2, task3, task4
		assertFalse(logic.containsTask(task2));
		assertFalse(logic.containsTask(task3));
		assertFalse(logic.containsTask(task4));

		// make sure task5 is still inside taskManager
		assertTrue(logic.containsTask(task5));
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

		deleteCommand = new DeleteCommand(invalidTaskId);
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

		deleteCommand = new DeleteCommand(invalidTaskId);
		deleteCommand.execute(logic);
	}

	@Test
	public void undo() {
		setUpTask();
		
		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(task1));
		
		deleteCommand = new DeleteCommand(task1.getId());
		deleteCommand.execute(logic);
		
		// make sure task1 is deleted
		assertFalse(logic.containsTask(task1));
		
		deleteCommand.undo(logic);
		// make sure task1 is inside taskManager
		assertTrue(logic.containsTask(task1));
	}
	
}
