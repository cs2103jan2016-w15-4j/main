package dooyit.logic.commands;

import dooyit.logic.TaskManager;
import dooyit.parser.Command;

public class DeleteCommand extends Command {

	public DeleteCommand(){
		command = "delete";
	}
	
	public void initDeleteCommand(int id){
		deleteId = id;
	}
	
	@Override
	public void execute(TaskManager taskManager){
		taskManager.deleteTask(deleteId);
	}
}
