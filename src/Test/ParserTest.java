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
		String userInput = "add floatingTask";
		//Command command = parser.getCommand(userInput);
	}
}