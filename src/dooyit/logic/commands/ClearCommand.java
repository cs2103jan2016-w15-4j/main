package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class ClearCommand extends ReversibleCommand {

	ArrayList<Task> clearedTasks;
	
	@Override
	public void undo(LogicController logic) {
		logic.loadTasks(clearedTasks);
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		clearedTasks = logic.clearTask();
	}

}
