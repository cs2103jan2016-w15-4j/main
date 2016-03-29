package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ClearCommand extends ReversibleCommand {

	ArrayList<Task> clearedTasks;
	
	@Override
	public void undo(LogicController logic) {
		logic.loadTasks(clearedTasks);
	}
	
	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		clearedTasks = logic.clearTask();
		
		logicAction = new LogicAction(Action.CLEAR_TASK, "All tasks are CLEARED!");
		return logicAction;
	}

}
