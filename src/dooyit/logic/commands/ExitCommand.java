package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class ExitCommand extends Command {

	public ExitCommand() {

	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		System.exit(1);
	}
}
