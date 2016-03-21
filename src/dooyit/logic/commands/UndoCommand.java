package dooyit.logic.commands;

import java.util.Stack;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;

public class UndoCommand extends Command {

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		Stack<ReversibleCommand> history = logic.getHistory();
		
		ReversibleCommand reversibleCommand;
		
		if(!history.isEmpty()){
			reversibleCommand = history.pop();
			reversibleCommand.undo(logic);
		}
	}

}
