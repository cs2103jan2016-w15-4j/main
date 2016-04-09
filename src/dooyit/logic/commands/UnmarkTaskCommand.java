//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UnmarkTaskCommand implements Command, ReversibleCommand {

	private ArrayList<Integer> unmarkIds;
	private ArrayList<Task> unmarkedTasks;
	private boolean hasError = false;

	public UnmarkTaskCommand(int unMarkId) {
		this.unmarkIds = new ArrayList<Integer>();
		this.unmarkedTasks = new ArrayList<Task>();
		this.unmarkIds.add(unMarkId);
	}

	public UnmarkTaskCommand(ArrayList<Integer> unMarkIds) {
		this.unmarkIds = new ArrayList<Integer>();
		this.unmarkedTasks = new ArrayList<Task>();
		this.unmarkIds.addAll(unMarkIds);
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {

		for (Task unmarkedTask : unmarkedTasks) {
			logic.markTask(unmarkedTask);
		}
	}

	public void redo(LogicController logic) {
		for (Task unmarkedTask : unmarkedTasks) {
			logic.unmarkTask(unmarkedTask);
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;

		String unmarkedTaskMsg = Constants.EMPTY_STRING;
		String errorMessageBody = Constants.EMPTY_STRING;

		for (int unmarkId : unmarkIds) {
			if (logic.containsTask(unmarkId)) {
				boolean markSuccess = logic.unmarkTask(unmarkId);
				Task unmarkedTask = logic.findTask(unmarkId);
				unmarkedTasks.add(unmarkedTask);
				
				if(markSuccess){
					unmarkedTaskMsg += Constants.SPACE + unmarkId;
				}
			} else {
				errorMessageBody += Constants.SPACE + unmarkId;
			}
		}

		if (!unmarkedTasks.isEmpty()) {
			if(unmarkedTaskMsg == Constants.EMPTY_STRING){
				logicAction = new LogicAction(Action.UNMARK_TASK, String.format(Constants.FEEDBACK_TASK_NOT_COMPLETED));
			}else if (unmarkedTasks.size() == 1) {
				logicAction = new LogicAction(Action.UNMARK_TASK, String.format(Constants.FEEDBACK_TASK_UNMARKED, unmarkedTaskMsg));
			} else {
				logicAction = new LogicAction(Action.UNMARK_TASK, String.format(Constants.FEEDBACK_TASKS_UNMARKED, unmarkedTaskMsg));
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
