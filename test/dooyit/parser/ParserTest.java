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
		assertEquals(expectedStart, dateTimeStartInCommand);
		assertEquals(expectedEnd, dateTimeEndInCommand);
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
		assertEquals(expectedDeadline, dateTimeInCommand);
	}
	
	//********************************************
	//************ Tests for EditParser **********
	//********************************************
	//!!!!!!!!!!!!!!! TO DO LATER !!!!!!!!!!!!!!!!
	
	
	//********************************************
	//********* Tests for Invalid Command ********
	//********************************************
	
}
