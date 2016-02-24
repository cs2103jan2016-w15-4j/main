package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Logic;
import dooyit.logic.TaskManager;

public class DeleteCommand extends Command {
	public enum DeleteCommandType {
		SINGLE, MULTIPLE
	};

	private int deleteId;
	private ArrayList<Integer> deleteIds;
	private DeleteCommandType deleteCommandType;

	public DeleteCommand() {

	}

	public void initDeleteCommand(int deleteId) {
		deleteCommandType = DeleteCommandType.SINGLE;
		this.deleteId = deleteId;
	}

	public void initDeleteCommand(ArrayList<Integer> deleteIds) {
		deleteCommandType = DeleteCommandType.MULTIPLE;
		this.deleteIds = deleteIds;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		
		switch (deleteCommandType) {

			case SINGLE:
				if (taskManager.deleteTask(deleteId) == null) {
					throw new IncorrectInputException("Index " + deleteId + " doesn't exists");
				}
				break;
				
			case MULTIPLE:
				String errorMessageBody = null;
				
				for(int i=0; i<deleteIds.size(); i++){
					if (taskManager.deleteTask(deleteIds.get(i)) == null) {
						errorMessageBody += " " + deleteIds.get(i);
					}
					
				}
				
				if(errorMessageBody != null){
					throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
				}
				
				break;
			}

	}
}
