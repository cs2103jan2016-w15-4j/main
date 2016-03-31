package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ExitCommand implements Command {

	public ExitCommand() {

	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		LogicAction logicAction = new LogicAction(Action.EXIT);
		System.exit(1);
		return logicAction;
	}
}
