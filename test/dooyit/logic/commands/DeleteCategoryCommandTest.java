//@@author A0124586Y
package dooyit.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;

public class DeleteCategoryCommandTest {
	LogicController logic;
	DeleteCategoryCommand deleteCatCommand;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();
	}
	
	@Test
	public void execute_ContainsCategory_ExpectedPass() {
		logic.clearCategory();
		Category personal = new Category("Personal", CustomColor.RED);
		Category assignments = new Category("Assignments", CustomColor.GREEN);
		logic.addCategory(personal);
		logic.addCategory(assignments);
		deleteCatCommand = new DeleteCategoryCommand("Personal");
		deleteCatCommand.execute(logic);
		assertFalse(logic.containsCategory("Personal"));
	}
	
	@Test (expected = IncorrectInputException.class)
	public void execute_MissingCategory_IncorrectInputException() {
		logic.clearCategory();
		deleteCatCommand = new DeleteCategoryCommand("Personal");
		deleteCatCommand.execute(logic);
	}
	
	
}
