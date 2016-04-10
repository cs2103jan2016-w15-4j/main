//@@author A0126356E
package dooyit.logic.commands;

import dooyit.logic.api.LogicController;

public interface ReversibleCommand extends Command{

	public void undo(LogicController logic);

	public void redo(LogicController logic);
}
