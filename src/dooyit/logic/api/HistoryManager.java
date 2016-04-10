//@@author A0126356E
package dooyit.logic.api;

import java.util.Stack;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.ReversibleCommand;

/**
 * history manager is responsible for adding reversible commands into history
 * for undoing the command as well as redo
 * 
 * @author limtaeu
 *
 */
public class HistoryManager {

	private Stack<ReversibleCommand> undoHistory;
	private Stack<ReversibleCommand> redoHistory;

	public HistoryManager() {
		undoHistory = new Stack<ReversibleCommand>();
		redoHistory = new Stack<ReversibleCommand>();
	}

	/**
	 * add command into history stack, check if command is reversible and
	 * executed successfully without error before adding
	 * 
	 * @param command
	 */
	public void addCommand(Command command) {
		if (command.hasError()) {
			return;
		}

		if (command instanceof ReversibleCommand) {
			undoHistory.push((ReversibleCommand) command);
		}
	}

	/**
	 * undo latest command
	 * 
	 * @param logic
	 * @return true if successful
	 */
	public boolean undoCommand(LogicController logic) {
		ReversibleCommand reversibleCommand;
		if (!undoHistory.isEmpty()) {
			reversibleCommand = undoHistory.pop();
			reversibleCommand.undo(logic);
			redoHistory.push(reversibleCommand);
			return true;
		}
		return false;
	}

	/**
	 * redo latest command
	 * 
	 * @param logic
	 * @return true if successful
	 */
	public boolean redoCommand(LogicController logic) {
		ReversibleCommand reversibleCommand;
		if (!redoHistory.isEmpty()) {
			reversibleCommand = redoHistory.pop();
			reversibleCommand.redo(logic);
			undoHistory.push(reversibleCommand);
			return true;
		}
		return false;
	}
}
