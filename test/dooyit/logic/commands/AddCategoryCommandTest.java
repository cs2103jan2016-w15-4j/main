//@@author A0126356E
package dooyit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColour;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class AddCategoryCommandTest {

	LogicController logic;
	AddCategoryCommand addCatCommand;

	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
	}

	@Test
	public void addCategory() {
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230");
		addCatCommand.execute(logic);

		assertTrue(logic.containsCategory("CS3230"));
	}

	@Test
	public void addExistingCategory() {
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230");
		addCatCommand.execute(logic);

		assertTrue(logic.containsCategory("CS3230"));

		addCatCommand = new AddCategoryCommand("CS3230");
		LogicAction logicAction = addCatCommand.execute(logic);
		Action action = Whitebox.getInternalState(logicAction, "action");
		assertEquals(Action.ERROR, action);
	}

	@Test
	public void addCategoryWithColour() {
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230", "blue");
		addCatCommand.execute(logic);

		assertTrue(logic.containsCategory("CS3230"));
		Category category = logic.findCategory("CS3230");
		assertTrue(category.getCustomColour() == CustomColour.BLUE);
	}

	@Test
	public void addCategoryWithInvalidColour() {
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230", "darkorange");
		LogicAction logicAction = addCatCommand.execute(logic);
		Action action = Whitebox.getInternalState(logicAction, "action");
		assertEquals(Action.ADD_CATEGORY, action);
	}
}
