package dooyit.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;

@PrepareForTest(AddParser.class)
public class ParserTest {
	
	Parser parser = new Parser();
	
	//****************************************
	//***** Tests for DeleteParser ***********
	//****************************************
	@Test
	public void deleteSingleTaskId() {
		String input = "delete 2";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void deleteMultipleTaskIds() {
		String input = "delete 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void deleteIntervalOfTaskIds() {
		String input = "delete 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	//****************************************
	//***** Tests for MarkParser ***********
	//****************************************
	@Test
	public void markSingleTaskId() {
		String input = "mark 2";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void markMultipleTaskIds() {
		String input = "mark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void markIntervalOfTaskIds() {
		String input = "mark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	//********************************************
	//********* Tests for UnmarkParser ***********
	//********************************************
	@Test
	public void unmarkSingleTaskId() {
		String input = "unmark 2";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void unmarkMultipleTaskIds() {
		String input = "unmark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void unmarkIntervalOfTaskIds() {
		String input = "unmark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	//******************************************
	//********* Tests for MoveParser ***********
	//******************************************
	@Test
	public void moveSingleTaskId() {
		String input = "move movies 2";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "taskIds");
		String categoryNameInCommand = Whitebox.getInternalState(command, "categoryName");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2));
		String expectedCategoryName = "movies";
		assertEquals(taskIdsInCommand, expectedTaskIds);
		assertEquals(categoryNameInCommand, expectedCategoryName);
	}
	
	//*******************************************
	//**** Tests for AddCategoryParser **********
	//*******************************************
	@Test
	public void addcatName() {
		String input = "addcat CS2103T";
		Command command = parser.getCommand(input);
		String categoryNameInCommand = Whitebox.getInternalState(command, "categoryName");
		String expectedCategoryName = "CS2103T";
		assertEquals(categoryNameInCommand, expectedCategoryName);
	}
	
	@Test
	public void addcatNameWithColour() {
		String input = "addcat CS2103T blue";
		Command command = parser.getCommand(input);
		String categoryNameInCommand = Whitebox.getInternalState(command, "categoryName");
		String categoryColourInCommand = Whitebox.getInternalState(command, "colorString");
		String expectedCategoryName = "CS2103T";
		String expectedCategoryColour = "blue";
		assertEquals(categoryNameInCommand, expectedCategoryName);
		assertEquals(categoryColourInCommand, expectedCategoryColour);
	}
	
	//********************************************
	//**** Tests for DeleteCategoryParser ********
	//********************************************
	
	
	//********************************************
	//****** Tests for EditCategoryParser ********
	//********************************************
	
	
	//********************************************
	//****** Tests for Change Skin Command *******
	//********************************************
	
	
	//********************************************
	//****** Tests for Set Storage Command *******
	//********************************************
	
	//********************************************
	//******** Tests for Search Command **********
	//********************************************
	
	//********************************************
	//********* Tests for ShowParser *************
	//********************************************
	
	//********************************************
	//************ Tests for AddParser ***********
	//********************************************
	
	
	//********************************************
	//************ Tests for EditParser **********
	//********************************************
	
	
	//********************************************
	//********* Tests for Invalid Command ********
	//********************************************
	
}
