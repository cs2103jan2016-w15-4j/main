package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class InvalidCommand implements Command {
	String errorMessage;
	private boolean hasError = false;

	public InvalidCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean hasError(){
		return hasError;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		LogicAction logicAction= new LogicAction(Action.ERROR, errorMessage);
		hasError = true;
		return logicAction;
	}

}
