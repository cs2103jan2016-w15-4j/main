package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class InvalidCommand implements Command {
	String errorMessage;

	public InvalidCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		LogicAction logicAction= new LogicAction(Action.ERROR);
		throw new IncorrectInputException(errorMessage);
		
		//return logicAction;
	}

}
