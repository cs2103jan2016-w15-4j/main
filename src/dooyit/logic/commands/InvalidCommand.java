package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class InvalidCommand extends Command {
	String errorMessage;

	public InvalidCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		throw new IncorrectInputException(errorMessage);
	}

}
