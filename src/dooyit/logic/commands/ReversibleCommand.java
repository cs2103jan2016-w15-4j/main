package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;

public abstract class ReversibleCommand extends Command {
	
	public abstract void undo();
}
