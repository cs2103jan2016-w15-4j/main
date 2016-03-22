package dooyit.logic.commands;

import java.io.IOException;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicController;
import dooyit.storage.StorageController;

public class StorageCommand extends Command {

	private String path;

	public StorageCommand(String path) {
		this.path = path;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		StorageController storage = logic.getStorage();

		try {
			storage.setFileDestination(path);
		} catch (IOException e) {
			throw new IncorrectInputException("Invalid path: " + path);
		}
	}

}
