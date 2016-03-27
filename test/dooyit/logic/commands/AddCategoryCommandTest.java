package dooyit.logic.commands;

import org.junit.Before;

import dooyit.logic.api.LogicController;

public class AddCategoryCommandTest {

	LogicController logic;
	AddCategoryCommand addCommand;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();
	}
	
	
}
