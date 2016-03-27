package dooyit.logic.commands;

import java.io.IOException;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;
import dooyit.storage.StorageController;

public class StorageCommand extends ReversibleCommand {

	private String path;
	private String previousPath;

	public StorageCommand(String path) {
		this.path = path;
	}

	@Override
	public void undo(LogicController logic){
		logic.setFileDestinationPath(previousPath);
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		
		try {
			previousPath = logic.getFilePath();
			logic.setFileDestinationPath(path);
		} catch (IncorrectInputException e) {
			throw new IncorrectInputException("Invalid path: " + path);
		}
	}

}
