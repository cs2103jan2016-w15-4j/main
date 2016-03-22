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

	LogicController logicController;
	TaskManager taskManager;
	DeleteCommand deleteCommand;

	Task task1;
	Task task2;
	Task task3;
	Task task4;
	Task task5;

	@Before
	public void setUp() {
		logicController = new LogicController();
		logicController.disableSave();
		logicController.clearTask();

		taskManager = logicController.getTaskManager();

	}

	public void setUpTask() {
		logicController.clearTask();

		task1 = new FloatingTask("hello");
		task2 = new FloatingTask("go");
		task3 = new DeadlineTask("car", new DateTime());
		task4 = new FloatingTask("house");
		task5 = new FloatingTask("water");
		
		taskManager.add(task1);
		taskManager.add(task2);
		taskManager.add(task3);
		taskManager.add(task4);
		taskManager.add(task5);
	}

	@Test
	public void execute() {
		setUpTask();

		// make sure task1 is inside task manager
		assertTrue(taskManager.contains(task1));

		// delete task1
		deleteCommand = new DeleteCommand(task1.getId());
		deleteCommand.execute(logicController);
		// try to find task1
		assertFalse(taskManager.contains(task1));

		// tasks left in taskManager: task2, task3, task4, task5
		// make sure task2 task3, task 4, task 5 is still inside task manager after deletion
		assertTrue(taskManager.contains(task2));
		assertTrue(taskManager.contains(task3));
		assertTrue(taskManager.contains(task4));
		assertTrue(taskManager.contains(task5));

		// delete tasks in batch using 1 command
		ArrayList<Integer> deleteIds = new ArrayList<Integer>();
		deleteIds.add(task2.getId());
		deleteIds.add(task3.getId());
		deleteIds.add(task4.getId());

		// delete task1
		deleteCommand = new DeleteCommand(deleteIds);
		deleteCommand.execute(logicController);

		// try to find task2, task3, task4
		assertFalse(taskManager.contains(task2));
		assertFalse(taskManager.contains(task3));
		assertFalse(taskManager.contains(task4));

		// make sure task5 is still inside taskManager
		assertTrue(taskManager.contains(task5));
	}

	// boundary case for negative partition
	@Test(expected = IncorrectInputException.class)
	public void executeExceptionHandlingNegative() throws IncorrectInputException {
		setUpTask();
		int invalidTaskId;

		// delete taskId -1
		invalidTaskId = -1;
		// try to find invalid task
		assertFalse(taskManager.contains(invalidTaskId));

		deleteCommand = new DeleteCommand(invalidTaskId);
		deleteCommand.execute(logicController);
	}

	// boundary case for positive partition
	@Test(expected = IncorrectInputException.class)
	public void executeExceptionHandlingPositive() throws IncorrectInputException {
		setUpTask();
		int invalidTaskId;

		// delete taskId 100
		invalidTaskId = 100;
		// try to find invalid task
		assertFalse(taskManager.contains(invalidTaskId));

		deleteCommand = new DeleteCommand(invalidTaskId);
		deleteCommand.execute(logicController);
	}

	
	@Test
	public void undo() {
		setUpTask();
		
		// make sure task1 is inside taskManager
		assertTrue(taskManager.contains(task1));
		
		deleteCommand = new DeleteCommand(task1.getId());
		deleteCommand.execute(logicController);
		
		// make sure task1 is deleted
		assertFalse(taskManager.contains(task1));
		
		deleteCommand.undo(logicController);
		// make sure task1 is inside taskManager
		assertTrue(taskManager.contains(task1));
	}
	
}
