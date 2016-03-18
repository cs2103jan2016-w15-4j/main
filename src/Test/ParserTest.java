package Test;

import dooyit.parser.*;
import dooyit.logic.commands.*;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

<<<<<<< HEAD
@RunWith(PowerMockRunner.class);
@PrepareForTest(Parser.class);
=======
import org.junit.Test;

>>>>>>> 1030d5e54d6a9a27297303d324a7ddc2b6c325ba
public class ParserTest {
	private Parser parser = new Parser();
	
	@Test
	public void testAddFloatingTask() {
		String userInput = "add floatingTask";
		Command command = parser.getCommand(userInput);
	}
}
