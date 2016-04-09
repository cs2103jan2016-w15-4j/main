//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class DeleteTaskCommand implements Command, ReversibleCommand {

	private ArrayList<Integer> deleteIds;
	private ArrayList<Task> deletedTasks;
	private boolean hasError = false;

	public DeleteTaskCommand(int deleteId) {
		this.deleteIds = new ArrayList<Integer>();
		this.deletedTasks = new ArrayList<Task>();
		this.deleteIds.add(deleteId);
	}

	public DeleteTaskCommand(ArrayList<Integer> deleteIds) {
		this.deleteIds = new ArrayList<Integer>();
		this.deletedTasks = new ArrayList<Task>();
		this.deleteIds.addAll(deleteIds);
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		for (Task deletedTask : deletedTasks) {
			logic.addTask(deletedTask);
		}
	}

	public void redo(LogicController logic) {
		for (Task deletedTask : deletedTasks) {
			logic.removeTask(deletedTask);
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;

		String errorMsgBody = Constants.EMPTY_STRING;
		String deletedTaskMsg = Constants.EMPTY_STRING;

		for (Integer deleteId : deleteIds) {
			if (logic.containsTask(deleteId)) {
				Task deletedTask = logic.removeTask(deleteId);
				deletedTasks.add(deletedTask);
				deletedTaskMsg += Constants.SPACE + deleteId;
			} else {
				errorMsgBody += Constants.SPACE + deleteId;
			}
		}

		if (!deletedTasks.isEmpty()) {
			if (deletedTasks.size() == 1) {
				logicAction = new LogicAction(Action.DELETE_TASK, String.format(Constants.FEEDBACK_TASK_DELETED, deletedTaskMsg));
			} else {
				logicAction = new LogicAction(Action.DELETE_TASK, String.format(Constants.FEEDBACK_TASKS_DELETED, deletedTaskMsg));
			}

		} else {
			hasError = true;
			if (errorMsgBody != Constants.EMPTY_STRING) {
				logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_IDS, errorMsgBody));
			}
		}

		return logicAction;
	}
}
