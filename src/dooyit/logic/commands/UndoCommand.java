//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UndoCommand implements Command {

	private boolean hasError = false;

	public UndoCommand() {

	}

	public boolean hasError() {
		return hasError;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		boolean isSuccess = logic.undo();

		if (isSuccess) {
			logicAction = successFeedback();
		} else {
			logicAction = failFeedBack();
		}

		return logicAction;
	}

	public LogicAction failFeedBack() {
		return new LogicAction(Action.UNDO, Constants.FEEDBACK_FAIL_UNDO);
	}

	public LogicAction successFeedback() {
		return new LogicAction(Action.UNDO, Constants.FEEDBACK_SUCCESS_UNDO);
	}

}
