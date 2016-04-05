package dooyit.logic.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.exception.IncorrectInputException;
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
	public void addCategory(){
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230");
		addCatCommand.execute(logic);
		
		assertTrue(logic.containsCategory("CS3230"));
	}
	
	@Test(expected = IncorrectInputException.class)
	public void addExistingCategory(){
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230");
		addCatCommand.execute(logic);
		
		assertTrue(logic.containsCategory("CS3230"));
		
		addCatCommand = new AddCategoryCommand("CS3230");
		addCatCommand.execute(logic);
	}
	
	@Test
	public void addCategoryWithColour(){
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230", "blue");
		addCatCommand.execute(logic);
		
		assertTrue(logic.containsCategory("CS3230"));
		Category category = logic.findCategory("CS3230");
		assertTrue(category.getCustomColour() == CustomColor.BLUE);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void addCategoryWithInvalidColour(){
		logic.clearCategory();
		addCatCommand = new AddCategoryCommand("CS3230", "darkorange");
		addCatCommand.execute(logic);
	}
}
