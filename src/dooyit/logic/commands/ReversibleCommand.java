package dooyit.logic.commands;

import dooyit.logic.api.LogicController;

public interface ReversibleCommand {
	
	public void undo(LogicController logic);
	
}
