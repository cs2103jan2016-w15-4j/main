package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;

public class UndoCommand extends Command {

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		logic.undoLatestCommand();
	}

}
