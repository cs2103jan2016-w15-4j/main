package Test;

import dooyit.parser.*;
import dooyit.logic.commands.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
	private Parser parser = new Parser();
	
	@Test
	public void testAddFloatingTask() {
		String userInput = "add floatingTask";
		Command command = parser.getCommand(userInput);
	}
}
