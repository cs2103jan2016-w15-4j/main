package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class InvalidCommand extends Command {
	String errorMessage;

	public InvalidCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		throw new IncorrectInputException(errorMessage);
	}

}
