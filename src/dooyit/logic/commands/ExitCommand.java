package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class ExitCommand extends Command {

	public ExitCommand() {

	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		System.exit(1);
	}
}
