//@@author A0126356E
package dooyit.logic.commands;

import dooyit.logic.api.LogicController;

public interface ReversibleCommand {

	public void undo(LogicController logic);

	public void redo(LogicController logic);
}
