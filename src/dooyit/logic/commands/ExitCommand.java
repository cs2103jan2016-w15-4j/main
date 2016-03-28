package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

public class ExitCommand extends Command {

	public ExitCommand() {

	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		System.exit(1);
	}
}
