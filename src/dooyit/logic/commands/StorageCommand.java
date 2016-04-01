package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class StorageCommand implements Command, ReversibleCommand {

	private String path;
	private String previousPath;

	public StorageCommand(String path) {
		this.path = path;
	}

	public void undo(LogicController logic){
		boolean fileExist = logic.setFileDestinationPath(previousPath);
		
		if(fileExist){
			logic.loadFromStorage();
		}
	}
	
	public void redo(LogicController logic){
		boolean fileExist = logic.setFileDestinationPath(path);
		
		if(fileExist){
			logic.loadFromStorage();
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		try {
			previousPath = logic.getFilePath();
			boolean fileExist = logic.setFileDestinationPath(path);
			if(fileExist){
				logic.loadFromStorage();
			}
		} catch (IncorrectInputException e) {
			logicAction = new LogicAction(Action.ERROR);
			throw new IncorrectInputException("Invalid path: " + path);
		}
		
		logicAction = new LogicAction(Action.SET_STORAGE_PATH);
		return logicAction;
	}

}
