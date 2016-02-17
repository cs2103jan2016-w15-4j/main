package dooyit.logic.commands;

import dooyit.logic.TaskManager;
import dooyit.parser.Command;

public class InvalidCommand extends Command {
	String errorMessage;
	
	public InvalidCommand(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	@Override
	public void execute(TaskManager taskManager) {
		System.out.println(errorMessage);
	}

}
