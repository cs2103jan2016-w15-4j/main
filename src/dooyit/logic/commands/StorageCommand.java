//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class StorageCommand implements Command, ReversibleCommand {

	private String path;
	private String previousPath;
	private boolean hasError = false;

	public StorageCommand(String path) {
		this.path = path;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		boolean fileExist = logic.setFileDestinationPath(previousPath);

		if (fileExist) {
			logic.loadFromStorage();
		}
	}

	public void redo(LogicController logic) {
		boolean fileExist = logic.setFileDestinationPath(path);

		if (fileExist) {
			logic.loadFromStorage();
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		try {
			previousPath = logic.getFilePath();
			boolean fileExist = logic.setFileDestinationPath(path);
			
			logicAction = new LogicAction(Action.SET_STORAGE_PATH, Constants.FEEDBACK_SET_NEW_PATH);
			
			if (fileExist) {
				logic.loadFromStorage();
				logicAction = new LogicAction(Action.SET_STORAGE_PATH, Constants.FEEDBACK_SET_NEW_PATH_WITH_LOAD);
			}
		} catch (IncorrectInputException e) {
			logicAction = new LogicAction(Action.ERROR, Constants.FEEDBACK_INVALID_PATH);
			hasError = true;
		}

		
		return logicAction;
	}

}
