package dooyit.logic.commands;

import dooyit.logic.api.LogicController;

public abstract class ReversibleCommand extends Command {
	
	public abstract void undo(LogicController logic);
}