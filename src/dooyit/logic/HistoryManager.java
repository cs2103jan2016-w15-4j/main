//@@author A0126356E
package dooyit.logic;

import java.util.Stack;

import dooyit.logic.api.LogicController;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.ReversibleCommand;

public class HistoryManager {

	private Stack<ReversibleCommand> undoHistory;
	private Stack<ReversibleCommand> redoHistory;

	public HistoryManager() {
		undoHistory = new Stack<ReversibleCommand>();
		redoHistory = new Stack<ReversibleCommand>();
	}

	public void addCommand(Command command) {
		if (command.hasError()) {
			return;
		}

		if (command instanceof ReversibleCommand) {
			undoHistory.push((ReversibleCommand) command);
		}
	}

	public void undoCommand(LogicController logic) {
		ReversibleCommand reversibleCommand;
		if (!undoHistory.isEmpty()) {
			reversibleCommand = undoHistory.pop();
			reversibleCommand.undo(logic);
			redoHistory.push(reversibleCommand);
		}
	}

	public void redoCommand(LogicController logic) {
		ReversibleCommand reversibleCommand;
		if (!redoHistory.isEmpty()) {
			reversibleCommand = redoHistory.pop();
			reversibleCommand.redo(logic);
			undoHistory.push(reversibleCommand);
		}
	}
}
