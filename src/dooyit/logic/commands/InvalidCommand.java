package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

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
