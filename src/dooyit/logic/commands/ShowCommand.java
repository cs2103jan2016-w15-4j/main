package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.TaskManager;

public class ShowCommand extends Command {
	
	ShowCommandType commandShowType;
	String categoryName;
	
	public ShowCommand(ShowCommandType commandShowType){
		this.commandShowType = commandShowType;
	}
	
	public ShowCommand(ShowCommandType commandShowType, String categoryName){
		this.commandShowType = commandShowType;
		this.categoryName = categoryName;
	}
	
	@Override
	public void execute(TaskManager taskManager) throws IncorrectInputException{
		
		

	}

}
