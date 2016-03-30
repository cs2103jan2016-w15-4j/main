import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.logic.api.LogicController;

public class IntegrationTest {

	LogicController logic;
	
	@Before
	public void setUp() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTask();
	}
	
	
	@Test
	public void addFloatingTask(){
		logic.clearTask();
		logic.processInput("add a");
		
		FloatingTask task = new FloatingTask("a");
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void addDeadlineTask(){
		logic.clearTask();
		logic.processInput("add buy milk by 30/3/2016");
		
		int date[] = {30, 3, 2016};
		DateTime dateTimeDeadline = new DateTime(date);
		
		DeadlineTask task = new DeadlineTask("buy milk", dateTimeDeadline);
		assertTrue(logic.containsTask(task));
	}
	
	@Test
	public void addEventTask(){
		logic.clearTask();
		logic.processInput("add buy milk from 30/3/2016 to 31/3/2016");
		
		int dateStart[] = {30, 3, 2016};
		int dateEnd[] = {31, 3, 2016};
		DateTime dateTimeStart = new DateTime(dateStart);
		DateTime dateTimeEnd = new DateTime(dateEnd);
		
		EventTask task = new EventTask("buy milk", dateTimeStart, dateTimeEnd);
		assertTrue(logic.containsTask(task));
	}
	
	
	
}
