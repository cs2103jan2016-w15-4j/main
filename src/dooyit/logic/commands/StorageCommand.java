package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Logic;

public class StorageCommand extends Command {

	private String path;
	
	public StorageCommand(String path){
		this.path = path;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException {

	}

}
