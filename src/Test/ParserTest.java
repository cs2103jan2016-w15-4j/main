package Test;

import dooyit.parser.*;
import dooyit.logic.commands.*;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Parser.class)
public class ParserTest {
	//private Parser parser = new Parser();
	
	@Test
	public void testAddFloatingTask() {
		String userInput = "29 March 2016";
		DateTimeParser dateTimeParser = PowerMockito.spy(new DateTimeParser());
		dateTimeParser.parse(userInput);
		//PowerMockito.when(dateTimeParser, "parse", userInput;
		
		//Command command = parser.getCommand(userInput);
	}
}
