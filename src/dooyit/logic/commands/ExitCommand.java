package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.TaskManager;

public class ExitCommand extends Command {

	public ExitCommand(){
		
	}
	
	@Override
	public void execute(TaskManager taskManager) throws IncorrectInputException{
		System.exit(1);
	}
}
