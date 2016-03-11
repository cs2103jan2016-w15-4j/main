package dooyit.logic.commands;

import java.io.IOException;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.storage.Storage;

public class StorageCommand extends Command {

	private String path;
	
	public StorageCommand(String path){
		this.path = path;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		Storage storage = logic.getStorage();
		
		try{
			storage.setFileDestination(path);
		}
		catch(IOException e){
			throw new IncorrectInputException("Invalid path: " + path);
		}
	}

}
