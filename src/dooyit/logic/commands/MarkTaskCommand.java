//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class MarkTaskCommand implements ReversibleCommand {
	private ArrayList<Integer> markIds;
	private ArrayList<Task> markedTasks;
	private boolean hasError = false;

	public MarkTaskCommand(int markId) {
		this.markIds = new ArrayList<Integer>();
		this.markedTasks = new ArrayList<Task>();
		this.markIds.add(markId);
	}

	public MarkTaskCommand(ArrayList<Integer> markIds) {
		this.markIds = new ArrayList<Integer>();
		this.markedTasks = new ArrayList<Task>();
		this.markIds.addAll(markIds);
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		for (Task markedTask : markedTasks) {
			logic.unmarkTask(markedTask);
		}
	}

	public void redo(LogicController logic) {
		for (Task markedTask : markedTasks) {
			logic.markTask(markedTask);
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;

		String markedTaskMsg = Constants.EMPTY_STRING;
		String errorMessageBody = Constants.EMPTY_STRING;

		for (int markId : markIds) {
			if (logic.containsTask(markId)) {
				markTaskWithTaskId(logic, markId);
				markedTaskMsg += Constants.SPACE + markId;
			} else {
				errorMessageBody += Constants.SPACE + markId;
			}
		}

		logicAction = createLogicAction(logicAction, markedTaskMsg, errorMessageBody);

		return logicAction;
	}

	public void markTaskWithTaskId(LogicController logic, int markId) {
		logic.markTask(markId);
		Task markedTask = logic.findTask(markId);
		markedTasks.add(markedTask);
	}

	public LogicAction createLogicAction(LogicAction logicAction, String markedTaskMsg, String errorMessageBody) {
		if (!markedTasks.isEmpty()) {
			if (markedTasks.size() == 1) {
				logicAction = new LogicAction(Action.MARK_TASK, String.format(Constants.FEEDBACK_TASK_MARKED, markedTaskMsg));
			} else {
				logicAction = new LogicAction(Action.MARK_TASK, String.format(Constants.FEEDBACK_TASKS_MARKED, markedTaskMsg));
			}

		} else {
			hasError = true;
			if (errorMessageBody != Constants.EMPTY_STRING) {
				logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_IDS, errorMessageBody));
			}
		}
		return logicAction;
	}
}
