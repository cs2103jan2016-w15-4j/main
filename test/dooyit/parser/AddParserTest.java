package dooyit.parser;

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
public class AddParserTest {
	
	AddParser parser;
	DateTimeParser dtParser;
	
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String TASK_NAME = "taskName";
	private static final String EVENT_START = "start";
	private static final String EVENT_END = "end";
	private static final String DEADLINE = "deadline";
	private static final String CATEGORY_NAME = "categoryName";
	private static final String INVALID_DATE_TIME = "Error: Invalid Date Time!";
	private static final String INVALID_ADD_COMMAND = "Error: Invalid add command!";
	
	@Before
	public void setup() {
		parser = new AddParser();
		dtParser = new DateTimeParser();
	}
	
	@Test
	public void Floating_Valid_ExpectedTrue() {
		String gardens = "gardens";// by the bay";
		parser.getCommand(gardens);
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		Assert.assertEquals(gardens, name);
	}
	
	@Test
	public void Deadline_ValidTmr_ExpectedTrue() {
		String homeworkTmr = "assignment by tmr 5pm";
		parser.getCommand(homeworkTmr);
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		Assert.assertEquals("assignment", name);
		DateTime deadline = Whitebox.getInternalState(parser, DEADLINE);
		DateTime dt = dtParser.parse("tmr 5pm");
		Assert.assertTrue(dt.equals(deadline));
	}
	
	@Test
	public void DeadlineTmr_Invalid_InvalidCommand() {
		String proposalTmrInvalid = "proposal by tmr 21312312";
		Command command = parser.getCommand(proposalTmrInvalid);
		
		//Getting error message from InvalidCommand
		String message = Whitebox.getInternalState(command, ERROR_MESSAGE);
		Assert.assertEquals(INVALID_DATE_TIME, message);
	}
	
	@Test
	public void Event_ValidDateTimeLong_ExpectedTrue() {
		String brunch = "brunch from 10/12/2016 10am to 10/12/2016 1pm";
		parser.getCommand(brunch);
		
		//Setting expected results
		String expectedName = "brunch";
		DateTime expectedStart = dtParser.parse("10/12/2016 10am");
		DateTime expectedEnd = dtParser.parse("10/12/2016 1pm");
		
		//Getting private attributes from AddParser
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		DateTime start = Whitebox.getInternalState(parser, EVENT_START);
		DateTime end = Whitebox.getInternalState(parser, EVENT_END);
		
		//Comparison with expected results
		Assert.assertEquals(expectedName, name);
		Assert.assertTrue(expectedStart.equals(start));
		Assert.assertTrue(expectedEnd.equals(end));
	}
	
	@Test
	public void Event_InvalidToDateTime_FloatingTask() {
		String brunchInvalidTo = "brunch from 10/12/2016 10am to 1212332";
		Command command = parser.getCommand(brunchInvalidTo);
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		Assert.assertEquals(brunchInvalidTo, name);
	}
	
	@Test
	public void Event_InvalidFromDateTime_FloatingTask() {
		String brunchInvalidFrom = "brunch from 123123123 to 10/12/2016 12pm";
		Command command = parser.getCommand(brunchInvalidFrom);
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		Assert.assertEquals(brunchInvalidFrom, name);
	}
	
	@Test
	public void Event_EmptyFromDateTime_FloatingTask() {
		String brunch = "brunch from to 10/12/2016 12pm";
		Command command = parser.getCommand(brunch);
		String name = Whitebox.getInternalState(parser, TASK_NAME);
		Assert.assertEquals(brunch, name);
		
	}
	
	@Test
	public void Event_EmptyFromToDateTime_AddFloat() {
		String brunch = "brunch from to";
		Command command = parser.getCommand(brunch);
		
		String name = Whitebox.getInternalState(command, TASK_NAME);
		Assert.assertEquals(brunch, name);
	}
	
	@Test
	public void InvalidTask_EmptyString_InvalidCommand() {
		String empty = "";
		Command command = parser.getCommand(empty);
		String message = Whitebox.getInternalState(command, ERROR_MESSAGE);
		Assert.assertEquals(INVALID_ADD_COMMAND, message);
	}
	
	/*@Test
	public void EventWithCat_Valid_ExpectedTrue(){
		String marathon = "marathon from 10/4/2016 6am to 10/4/2016 9am @ Run";
		Command command = parser.getCommand(marathon);
		String categoryName = Whitebox.getInternalState(command, CATEGORY_NAME);
		Assert.assertEquals("Run", categoryName);
	}*/
}
