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

		boolean isSuccuss = logic.undo();

		if (isSuccuss) {
			logicAction = new LogicAction(Action.UNDO, Constants.FEEDBACK_SUCCESS_UNDO);
		} else {
			logicAction = new LogicAction(Action.UNDO, Constants.FEEDBACK_FAIL_UNDO);
		}

		return logicAction;
	}

}
