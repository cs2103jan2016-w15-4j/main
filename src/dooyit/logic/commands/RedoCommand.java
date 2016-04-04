//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class RedoCommand implements Command {

	private boolean hasError = false;

	public RedoCommand() {

	}

	public boolean hasError() {
		return hasError;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		logic.redo();

		logicAction = new LogicAction(Action.REDO);
		return logicAction;
	}

}
