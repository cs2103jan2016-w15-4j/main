package dooyit.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.logic.commands.ShowCommand.ShowCommandType;

public class ShowCommandTest {
	LogicController logic;
	ShowCommand showCommand;
	
	@Before
	public void setup() {
		logic = new LogicController();
		logic.disableSave();
		logic.clearTasks();
		logic.addCategory("Test");
	}
	
	@Test
	public void Show_Today_LogicActionToday() {
		showCommand = new ShowCommand(ShowCommandType.TODAY);
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_TODAY_TASK, logicAction.getAction());
	}
	
	@Test
	public void Show_Next7_LogicActionNext7() {
		showCommand = new ShowCommand(ShowCommandType.NEXT7DAY);
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_NEXT7DAY_TASK, logicAction.getAction());
	}
	
	@Test
	public void Show_CategoryExisting_LogicActionShowCategory() {
		showCommand = new ShowCommand(ShowCommandType.CATEGORY, "Test");
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_CATEGORY, logicAction.getAction());
	}
	
	@Test
	public void Show_CategoryMissing_LogicActionError() {
		showCommand = new ShowCommand(ShowCommandType.CATEGORY, "Missing");
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.ERROR, logicAction.getAction());
	}
	
	@Test
	public void Show_Float_LogicActionShowFloat() {
		showCommand = new ShowCommand(ShowCommandType.FLOAT);
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_FLOATING_TASK, logicAction.getAction());
	}
	
	@Test
	public void Show_Completed_LogicActionShowAll() {
		showCommand = new ShowCommand(ShowCommandType.ALL);
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_ALL_TASK, logicAction.getAction());
	}
	
	@Test
	public void Show_Completed_LogicActionShowCompleted() {
		showCommand = new ShowCommand(ShowCommandType.COMPLETED);
		LogicAction logicAction = showCommand.execute(logic);
		assertEquals(Action.SHOW_COMPLETED, logicAction.getAction());
	}
}
