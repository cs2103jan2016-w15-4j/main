package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ClearCommand implements Command, ReversibleCommand {

	ArrayList<Task> clearedTasks;
	
	public void undo(LogicController logic) {
		logic.loadTasks(clearedTasks);
	}
	
	public void redo(LogicController logic){
		clearedTasks = logic.clearTask();
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		clearedTasks = logic.clearTask();
		
		logicAction = new LogicAction(Action.CLEAR_TASK, "All tasks are CLEARED!");
		return logicAction;
	}

}
