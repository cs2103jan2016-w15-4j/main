package dooyit.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import dooyit.common.datatype.DateTime;

@PrepareForTest(AddParser.class)
public class AddParserTest {
	
	AddParser parser;
	
	DateTimeParser dtParser;
	
	@Before
	public void setup() {
		parser = new AddParser();
		dtParser = new DateTimeParser();
	}
	
	@Test
	public void add_Floating_Valid() {
		String gardens = "gardens";// by the bay";
		parser.getCommand(gardens);
		String name = Whitebox.getInternalState(parser, "taskName");
		Assert.assertEquals(gardens, name);
	}
	
	@Test
	public void add_DeadlineTmr_Valid() {
		String homeworkTmr = "assignment by tmr 5pm";
		parser.getCommand(homeworkTmr);
		String name = Whitebox.getInternalState(parser, "taskName");
		Assert.assertEquals("assignment", name);
		DateTime deadline = Whitebox.getInternalState(parser, "deadline");
		DateTime dt = dtParser.parse("tmr 5pm");
		Assert.assertTrue(dt.equals(deadline));
	}
	
	@Test
	public void add_Event_Valid() {
		String brunch = "brunch from 10/12/2016 10am to 10/12/2016 1pm";
		parser.getCommand(brunch);
		String name = Whitebox.getInternalState(parser, "taskName");
		Assert.assertEquals("brunch", name);
		DateTime expectedStart = dtParser.parse("10/12/2016 10am");
		DateTime start = Whitebox.getInternalState(parser, "start");
		Assert.assertTrue(expectedStart.equals(start));
		DateTime expectedEnd = dtParser.parse("10/12/2016 1pm");
		DateTime end = Whitebox.getInternalState(parser, "end");
		Assert.assertTrue(expectedEnd.equals(end));
	}
}
