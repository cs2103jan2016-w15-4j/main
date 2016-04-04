package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UndoCommand implements Command {

	private boolean hasError = false;
	
	public UndoCommand(){
		
	}
	
	public boolean hasError(){
		return hasError;
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		logic.undo();
		
		logicAction = new LogicAction(Action.UNDO);
		return logicAction;
	}

}
