package dooyit.logic.commands;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.TaskManager;

public class DeleteCommand extends Command {

	public DeleteCommand(){

	}
	
	public void initDeleteCommand(int id){
		deleteId = id;
	}
	
	@Override
	public void execute(TaskManager taskManager){
		
		if(taskManager.deleteTask(deleteId) == null){
			
			throw new IncorrectInputException("Index " + deleteId + " doesn't exists");
			
		}
	}
}
