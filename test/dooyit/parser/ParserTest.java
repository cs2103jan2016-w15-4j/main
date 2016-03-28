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
	Parser parser;
	
	@Before
	public void setup() {
		parser = new Parser();
	}
	
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
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidSingleTaskIdAlphabet() {
		String input = "delete a";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidSingleTaskIdColon() {
		String input = "delete :";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidSingleTaskIdQuestionMark() {
		String input = "delete ?";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidSingleTaskIdEmptyString() {
		String input = "delete ";
		parser.getCommand(input);
	}
	
	@Test
	public void deleteMultipleTaskIds() {
		String input = "delete 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidMultipleTaskIdsAlphabet() {
		String input = "delete 2 a 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidMultipleTaskIdsColon() {
		String input = "delete 2 : 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidMultipleTaskIdsBackslash() {
		String input = "delete 2" + " \\ 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidMultipleTaskIdsQuestionMark() {
		String input = "delete 2 ? 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test
	public void deleteIntervalOfTaskIds() {
		String input = "delete 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "delete a-7";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "delete 2-c";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "delete a-z";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsStartColon() {
		String input = "delete :-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsEndColon() {
		String input = "delete 1-:";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "delete \\-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void deleteInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "delete 1-\\";
		parser.getCommand(input);
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
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidSingleTaskIdAlphabet() {
		String input = "mark a";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidSingleTaskIdColon() {
		String input = "mark :";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidSingleTaskIdQuestionMark() {
		String input = "mark ?";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidSingleTaskIdEmptyString() {
		String input = "mark ";
		parser.getCommand(input);
	}
	
	@Test
	public void markeMultipleTaskIds() {
		String input = "mark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidMultipleTaskIdsAlphabet() {
		String input = "mark 2 a 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidMultipleTaskIdsColon() {
		String input = "mark 2 : 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidMultipleTaskIdsBackslash() {
		String input = "mark 2" + " \\ 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidMultipleTaskIdsQuestionMark() {
		String input = "mark 2 ? 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test
	public void markIntervalOfTaskIds() {
		String input = "mark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "mark a-7";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "mark 2-c";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "mark a-z";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsStartColon() {
		String input = "mark :-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsEndColon() {
		String input = "mark 1-:";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "mark \\-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void markInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "mark 1-\\";
		parser.getCommand(input);
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
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidSingleTaskIdAlphabet() {
		String input = "unmark a";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidSingleTaskIdColon() {
		String input = "unmark :";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidSingleTaskIdQuestionMark() {
		String input = "unmark ?";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidSingleTaskIdEmptyString() {
		String input = "unmark ";
		parser.getCommand(input);
	}
	
	@Test
	public void unmarkeMultipleTaskIds() {
		String input = "unmark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidMultipleTaskIdsAlphabet() {
		String input = "unmark 2 a 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidMultipleTaskIdsColon() {
		String input = "unmark 2 : 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidMultipleTaskIdsBackslash() {
		String input = "unmark 2" + " \\ 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidMultipleTaskIdsQuestionMark() {
		String input = "unmark 2 ? 5 7 20 12";
		parser.getCommand(input);
	}
	
	@Test
	public void unmarkIntervalOfTaskIds() {
		String input = "unmark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "unmark a-7";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "unmark 2-c";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "unmark a-z";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsStartColon() {
		String input = "unmark :-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsEndColon() {
		String input = "unmark 1-:";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "unmark \\-3";
		parser.getCommand(input);
	}
	
	@Test(expected = IncorrectInputException.class)
	public void unmarkInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "unmark 1-\\";
		parser.getCommand(input);
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
	//!!!!!!!!!!!!!!! TO DO LATER !!!!!!!!!!!!!!!!
	
	
	//********************************************
	//****** Tests for EditCategoryParser ********
	//********************************************
	//!!!!!!!!!!!!!!! TO DO LATER !!!!!!!!!!!!!!!!
	
	
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
	//!!!!!!!!!!!!!!! TO DO LATER !!!!!!!!!!!!!!!!
	
	
	//********************************************
	//********* Tests for Invalid Command ********
	//********************************************
	
}
