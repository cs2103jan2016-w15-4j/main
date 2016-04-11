//@@author A0124586Y
package dooyit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColour;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class DeleteCategoryCommandTest {
	private static final String ACTION = "action";

	LogicController logic;
	DeleteCategoryCommand deleteCatCommand;

	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}

	@Test
	public void execute_ContainsCategory_ExpectedPass() {
		logic.clearCategory();
		Category personal = new Category("Personal", CustomColour.RED);
		Category assignments = new Category("Assignments", CustomColour.GREEN);
		logic.addCategory(personal);
		logic.addCategory(assignments);
		deleteCatCommand = new DeleteCategoryCommand("Personal");
		deleteCatCommand.execute(logic);
		assertFalse(logic.containsCategory("Personal"));
	}

	@Test
	public void execute_MissingCategory_IncorrectInputException() {
		logic.clearCategory();
		deleteCatCommand = new DeleteCategoryCommand("Personal");
		LogicAction logicAction = deleteCatCommand.execute(logic);
		Action action = Whitebox.getInternalState(logicAction, ACTION);
		assertEquals(Action.ERROR, action);
	}

}
