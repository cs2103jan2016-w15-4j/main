//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ClearTaskCommand implements Command, ReversibleCommand {

	ArrayList<Task> clearedTasks;
	private boolean hasError = false;

	public ClearTaskCommand() {

	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		logic.loadTasks(clearedTasks);
	}

	public void redo(LogicController logic) {
		clearedTasks = logic.clearTask();
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		clearedTasks = logic.clearTask();

		logicAction = new LogicAction(Action.CLEAR_TASK, Constants.FEEDBACK_TASK_CLEARED);
		return logicAction;
	}

}
