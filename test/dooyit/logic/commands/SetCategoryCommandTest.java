package dooyit.logic.commands;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.commands.SetCategoryCommand;

public class SetCategoryCommandTest {
	LogicController logic;
	SetCategoryCommand setCatCommand;
	
	@Before
	public void setup() {
		logic = new LogicController();
		logic.disableSave();
	}
	
	public void clear() {
		logic.clearTasks();
		logic.clearCategory();
	}
	
	@Test (expected = IncorrectInputException.class)
	public void SetCategory_MissingCategory_CategoryCreated() {
		clear();
		int[] date = {20, 4, 2016};
		DateTime deadline = new DateTime(date, 1000);
		logic.addDeadlineTask("Project Proposal", deadline);
		ArrayList<Task> tasks = logic.getTaskManager().getAllTasks();
		int taskId = tasks.get(0).getUniqueId();
		setCatCommand = new SetCategoryCommand(taskId, "Deadlines");
		setCatCommand.execute(logic);
		assertTrue(logic.containsCategory("Deadlines"));
	}
	
	@Test (expected = IncorrectInputException.class)
	public void SetCategory_InvalidId_IncorrectInputException() {
		clear();
		int[] date = {20, 4, 2016};
		DateTime deadline = new DateTime(date, 1000);
		logic.addDeadlineTask("Project Proposal", deadline);
		
		setCatCommand = new SetCategoryCommand(100, "Deadlines");
		setCatCommand.execute(logic);
		assertTrue(logic.containsCategory("Deadlines"));
	}
	
	@Test
	public void SetCategory_ExistingCategory_CategoryAssigned() {
		clear();
		int[] date = {20, 4, 2016};
		DateTime deadline = new DateTime(date, 1000);
		logic.addDeadlineTask("Project Proposal", deadline);
		
		//Add the category first
		Category category = new Category("Deadlines", CustomColor.BLUE);
		logic.addCategory(category);
		assertTrue(logic.containsCategory("Deadlines"));
		
		//Get the Id of the task we added to reference for editting
		ArrayList<Task> tasks = logic.getTaskManager().getAllTasks();
		Task task = tasks.get(0);
		int taskId = task.getUniqueId();
		setCatCommand = new SetCategoryCommand(taskId, "Deadlines");
		
		setCatCommand.execute(logic);
		assertEquals(task.getCategory(), category);
	}
}
