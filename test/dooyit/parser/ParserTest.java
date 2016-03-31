package dooyit.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import dooyit.ui.UIMainViewType;

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
		assertEquals(expectedTaskIds, taskIdsInCommand);
	}
	
	@Test
	public void deleteInvalidSingleTaskIdAlphabet() {
		String input = "delete a";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidSingleTaskIdColon() {
		String input = "delete :";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidSingleTaskIdQuestionMark() {
		String input = "delete ?";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidSingleTaskIdEmptyString() {
		String input = "delete ";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

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
	public void deleteInvalidMultipleTaskIdsAlphabet() {
		String input = "delete 2 a 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidMultipleTaskIdsColon() {
		String input = "delete 2 : 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidMultipleTaskIdsBackslash() {
		String input = "delete 2" + " \\ 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidMultipleTaskIdsQuestionMark() {
		String input = "delete 2 ? 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteIntervalOfTaskIds() {
		String input = "delete 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "deleteIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "delete a-7";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "delete 2-c";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "delete a-z";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsStartColon() {
		String input = "delete :-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsEndColon() {
		String input = "delete 1-:";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "delete \\-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void deleteInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "delete 1-\\";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

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
	public void markInvalidSingleTaskIdAlphabet() {
		String input = "mark a";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidSingleTaskIdColon() {
		String input = "mark :";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidSingleTaskIdQuestionMark() {
		String input = "mark ?";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidSingleTaskIdEmptyString() {
		String input = "mark ";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markeMultipleTaskIds() {
		String input = "mark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void markInvalidMultipleTaskIdsAlphabet() {
		String input = "mark 2 a 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidMultipleTaskIdsColon() {
		String input = "mark 2 : 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidMultipleTaskIdsBackslash() {
		String input = "mark 2" + " \\ 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidMultipleTaskIdsQuestionMark() {
		String input = "mark 2 ? 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markIntervalOfTaskIds() {
		String input = "mark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "markIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "mark a-7";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "mark 2-c";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "mark a-z";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsStartColon() {
		String input = "mark :-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsEndColon() {
		String input = "mark 1-:";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "mark \\-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void markInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "mark 1-\\";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
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
		assertEquals(expectedTaskIds, taskIdsInCommand);
	}
	
	@Test
	public void unmarkInvalidSingleTaskIdAlphabet() {
		String input = "unmark a";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidSingleTaskIdColon() {
		String input = "unmark :";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidSingleTaskIdQuestionMark() {
		String input = "unmark ?";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidSingleTaskIdEmptyString() {
		String input = "unmark ";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkeMultipleTaskIds() {
		String input = "unmark 2 3 5 7 20 12";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 7, 20, 12));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void unmarkInvalidMultipleTaskIdsAlphabet() {
		String input = "unmark 2 a 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidMultipleTaskIdsColon() {
		String input = "unmark 2 : 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidMultipleTaskIdsBackslash() {
		String input = "unmark 2" + " \\ 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidMultipleTaskIdsQuestionMark() {
		String input = "unmark 2 ? 5 7 20 12";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkIntervalOfTaskIds() {
		String input = "unmark 2-7";
		Command command = parser.getCommand(input);
		ArrayList<Integer> taskIdsInCommand = Whitebox.getInternalState(command, "unmarkIds");
		ArrayList<Integer> expectedTaskIds = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7));
		assertEquals(taskIdsInCommand, expectedTaskIds);
	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsStartAlphabet() {
		String input = "unmark a-7";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsEndAlphabet() {
		String input = "unmark 2-c";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);

	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsStartAndEndAlphabet() {
		String input = "unmark a-z";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsStartColon() {
		String input = "unmark :-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsEndColon() {
		String input = "unmark 1-:";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsStartBackslash() {
		String input = "unmark \\-3";
		Command command = parser.getCommand(input);
		String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
	}
	
	@Test
	public void unmarkInvalidIntervalOfTaskIdsEndBackslash() {
		String input = "unmark 1-\\";
		Command command = parser.getCommand(input);
				String commandErrorMessage = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = TagParser.ERROR_MESSAGE_INVALID_TASK_ID;
		assertEquals(expectedErrorMessage, commandErrorMessage);
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
		assertEquals(expectedTaskIds, taskIdsInCommand);
		assertEquals(expectedCategoryName, categoryNameInCommand);
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
		assertEquals(expectedCategoryName, categoryNameInCommand);
	}
	
	@Test
	public void addcatNameWithColour() {
		String input = "addcat CS2103T blue";
		Command command = parser.getCommand(input);
		String categoryNameInCommand = Whitebox.getInternalState(command, "categoryName");
		String categoryColourInCommand = Whitebox.getInternalState(command, "colorString");
		String expectedCategoryName = "CS2103T";
		String expectedCategoryColour = "blue";
		assertEquals(expectedCategoryName, categoryNameInCommand);
		assertEquals(expectedCategoryColour, categoryColourInCommand);
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
	@Test
	public void changeSkinToDark() {
		String input = "skin dark";
		Command command = parser.getCommand(input);
		
		String theme = Whitebox.getInternalState(command, "themeString");
		String expectedTheme = "dark";
		assertEquals(expectedTheme, theme);
	}
	
	@Test
	public void changeSkinToAqua() {
		String input = "skin aqua";
		Command command = parser.getCommand(input);
		
		String theme = Whitebox.getInternalState(command, "themeString");
		String expectedTheme = "aqua";
		assertEquals(expectedTheme, theme);
	}
	
	@Test
	public void changeSkinToDefault() {
		String input = "skin light";
		Command command = parser.getCommand(input);
		
		String theme = Whitebox.getInternalState(command, "themeString");
		String expectedTheme = "light";
		assertEquals(expectedTheme, theme);
	}
	//********************************************
	//****** Tests for Set Storage Command *******
	//********************************************
	
	//********************************************
	//******** Tests for Search Command **********
	//********************************************
	@Test
	public void search() {
		String input = "search hello";
		Command command = parser.getCommand(input);
		
		String keyword = Whitebox.getInternalState(command, "searchString");
		String expectedKeyword = "hello";
		assertEquals(expectedKeyword, keyword);
	}
	
	//********************************************
	//********* Tests for ShowParser *************
	//********************************************
	@Test
	public void showToday() {
		String input = "show today";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.TODAY;
		assertEquals(expectedView, view);
	}
	
	@Test
	public void showNextSeven() {
		String input = "show next7";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.EXTENDED;
		assertEquals(expectedView, view);
	}
	
	@Test
	public void showAll() {
		String input = "show all";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.ALL;
		assertEquals(expectedView, view);
	}
	
	@Test
	public void showFloat() {
		String input = "show float";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.FLOAT;
		assertEquals(expectedView, view);
	}
	
	@Test
	public void showCompleted() {
		String input = "show completed";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.COMPLETED;
		assertEquals(expectedView, view);
	}
	
	@Test
	public void showCategory() {
		String input = "show helloWorld";
		Command command = parser.getCommand(input);
		
		UIMainViewType view = Whitebox.getInternalState(command, "uiMainViewtype");
		UIMainViewType expectedView = UIMainViewType.CATEGORY;
		String categoryName = Whitebox.getInternalState(command, "categoryName");
		String expectedCategoryName = "helloWorld";
		assertEquals(expectedView, view);
		assertEquals(expectedCategoryName, categoryName);
	}
	
	//********************************************
	//************ Tests for AddParser ***********
	//********************************************
	@Test
	public void addFloatingTask() {
		String input = "add read book";
		Command command = parser.getCommand(input);
		
		String taskName = Whitebox.getInternalState(command, "taskName");
		String expectedTaskName = "read book";
		assertEquals(expectedTaskName, taskName);
	}
	
	@Test
	public void addFloatingTaskWithDeadlineMarker() {
		String input = "add gardens by the bay";
		Command command = parser.getCommand(input);
		
		String taskName = Whitebox.getInternalState(command, "taskName");
		String expectedTaskName = "gardens by the bay";
		assertEquals(expectedTaskName, taskName);
	}
	
	@Test
	public void addFloatingTaskWithEventMarkers() {
		String input = "add read book Harry Potter from chapter 1 to chapter 2";
		Command command = parser.getCommand(input);
		
		String taskName = Whitebox.getInternalState(command, "taskName");
		String expectedTaskName = "read book Harry Potter from chapter 1 to chapter 2";
		assertEquals(expectedTaskName, taskName);
	}
	
	@Test
	public void addEvent() {
		String input = "add read book Harry Potter from 12/2/2016 5pm to 14 feb 2016 7pm";
		Command command = parser.getCommand(input);
		
		String taskName = Whitebox.getInternalState(command, "taskName");
		String expectedTaskName = "read book Harry Potter";
		
		DateTimeParser dateTimeParser = new DateTimeParser();
		DateTime expectedStart = dateTimeParser.parse("12/2/2016 5pm");
		DateTime dateTimeStartInCommand = Whitebox.getInternalState(command, "dateTimeStart");
		DateTime expectedEnd = dateTimeParser.parse("14 feb 2016 7pm");
		DateTime dateTimeEndInCommand = Whitebox.getInternalState(command, "dateTimeEnd");
		
		assertEquals(expectedTaskName, taskName);
		assertTrue(expectedStart.equals(dateTimeStartInCommand));
		assertTrue(expectedEnd.equals(dateTimeEndInCommand));
	}
	
	@Test
	public void addDeadlineTask() {
		String input = "add read book Harry Potter by 19/4/2017 3.30pm";
		Command command = parser.getCommand(input);
		String taskName = Whitebox.getInternalState(command, "taskName");
		String expectedTaskName = "read book Harry Potter";
		
		DateTimeParser dateTimeParser = new DateTimeParser();
		DateTime expectedDeadline = dateTimeParser.parse("19/4/2017 3.30pm");
		DateTime dateTimeInCommand = Whitebox.getInternalState(command, "dateTimeDeadline");
		
		assertEquals(expectedTaskName, taskName);
		assertTrue(expectedDeadline.equals(dateTimeInCommand));
	}
	
	
	@Test
	public void Deadline_ValidTmr_ExpectedTrue() {
		String homeworkTmr = "add assignment by 30/3 5pm";
		Command command = parser.getCommand(homeworkTmr);
		String name = Whitebox.getInternalState(command, "taskName");
		assertEquals("assignment", name);
		
		DateTime deadline = Whitebox.getInternalState(command, "deadline");
		DateTimeParser dtParser = new DateTimeParser();
		DateTime expectedDeadline = dtParser.parse("30/3 5pm");
		Assert.assertTrue(expectedDeadline.equals(deadline));
	}
	
	@Test
	public void DeadlineTmr_Invalid_InvalidCommand() {
		String proposalTmrInvalid = "proposal by tmr 21312312";
		Command command = parser.getCommand(proposalTmrInvalid);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	@Test
	public void Event_ValidDateTimeLong_ExpectedTrue() {
		String brunch = "brunch from 10/12/2016 10am to 10/12/2016 1pm";
		Command command = parser.getCommand(brunch);
		
		//Setting expected results
		String expectedName = "brunch";
		DateTimeParser dtParser = new DateTimeParser();
		DateTime expectedStart = dtParser.parse("10/12/2016 10am");
		DateTime expectedEnd = dtParser.parse("10/12/2016 1pm");
		
		//Getting private attributes from AddParser
		String name = Whitebox.getInternalState(command, "taskName");
		DateTime start = Whitebox.getInternalState(command,"eventStart");
		DateTime end = Whitebox.getInternalState(command, "eventEnd");
		
		//Comparison with expected results
		Assert.assertEquals(expectedName, name);
		Assert.assertTrue(expectedStart.equals(start));
		Assert.assertTrue(expectedEnd.equals(end));
	}
	
	@Test
	public void Event_InvalidToDateTime_FloatingTask() {
		String brunchInvalidTo = "brunch from 10/12/2016 10am to 1212332";
		Command command = parser.getCommand(brunchInvalidTo);
		
		//String name = Whitebox.getInternalState(parser, TASK_NAME);
		//Assert.assertEquals(brunchInvalidTo, name);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	@Test
	public void Event_InvalidFromDateTime_FloatingTask() {
		String brunchInvalidFrom = "brunch from 123123123 to 10/12/2016 12pm";
		Command command = parser.getCommand(brunchInvalidFrom);
		//String name = Whitebox.getInternalState(parser, TASK_NAME);
		//Assert.assertEquals(brunchInvalidFrom, name);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	@Test
	public void Event_EmptyFromDateTime_FloatingTask() {
		String brunch = "brunch from to 10/12/2016 12pm";
		Command command = parser.getCommand(brunch);
		//String name = Whitebox.getInternalState(parser, TASK_NAME);
		//Assert.assertEquals(brunch, name);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	@Test
	public void Event_EmptyToDateTime_FloatingTask() {
		String brunch = "brunch from 10/12/2016 12pm to ";
		Command command = parser.getCommand(brunch);
		//String name = Whitebox.getInternalState(parser, TASK_NAME);
		//Assert.assertEquals(brunch, name);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	
	@Test
	public void Event_EmptyFromToDateTime_AddFloat() {
		String brunch = "brunch from to";
		Command command = parser.getCommand(brunch);
		
		//String name = Whitebox.getInternalState(command, TASK_NAME);
		//Assert.assertEquals(brunch, name);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = DateTimeParser.ERROR_MESSAGE_INVALID_DATE_TIME;
		assertEquals(expectedErrorMessage, message);
	}
	
	//********************************************
	//************ Tests for EditParser **********
	//********************************************
	//!!!!!!!!!!!!!!! TO DO LATER !!!!!!!!!!!!!!!!
	
	
	//********************************************
	//********* Tests for Invalid Command ********
	//********************************************
	
	@Test
	public void EmptyString_InvalidCommand() {
		String empty = "";
		Command command = parser.getCommand(empty);
		
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = Parser.ERROR_MESSAGE_INVALID_COMMAND + empty;
		Assert.assertEquals(expectedErrorMessage, message);
	}
	
	@Test
	public void parseGibberishInvalidCommand() {
		String gibberish = "dhasjkfhsbdk";
		Command command = parser.getCommand(gibberish);
		
		String message = Whitebox.getInternalState(command, "errorMessage");
		String expectedErrorMessage = Parser.ERROR_MESSAGE_INVALID_COMMAND + gibberish;
		Assert.assertEquals(expectedErrorMessage, message);
	}
}
