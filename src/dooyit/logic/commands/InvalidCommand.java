package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Logic;
import dooyit.logic.TaskManager;

public class InvalidCommand extends Command {
	String errorMessage;
	
	public InvalidCommand(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException{
		throw new IncorrectInputException(errorMessage);
	}

}
