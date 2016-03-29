package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UndoCommand extends Command {

	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		logic.undoLatestCommand();
		
		logicAction = new LogicAction(Action.UNDO);
		return logicAction;
	}

}
