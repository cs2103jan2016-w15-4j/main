package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class MarkCommand extends Command {
	public enum MarkCommandType {
		SINGLE, MULTIPLE
	};

	private int markId;
	private ArrayList<Integer> markIds;
	private MarkCommandType markCommandType;

	public MarkCommand() {

	}

	public void initMarkCommand(int deleteId) {
		markCommandType = MarkCommandType.SINGLE;
		this.markId = deleteId;
	}

	public void initMarkCommand(ArrayList<Integer> deleteIds) {
		markCommandType = MarkCommandType.MULTIPLE;
		this.markIds = deleteIds;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert(taskManager != null);
		
		switch (markCommandType) {

			case SINGLE:
				if(taskManager.containsTask(markId)){
					boolean successfullyMarked = taskManager.markTask(markId);
					
					if(!successfullyMarked){
						throw new IncorrectInputException("Task " + markId + "-" + taskManager.findTask(markId).getName() +" is already marked.");
					}
				}
				else{
					throw new IncorrectInputException("Index " + markId + " doesn't exists");
				}
				break;
				
			case MULTIPLE:
				String errorMessageBody = "";
				
				for(int i=0; i<markIds.size(); i++){
					if(taskManager.containsTask(markIds.get(i))){
						taskManager.markTask(markIds.get(i));
					}
					else{
						errorMessageBody += " " + markIds.get(i);
					}
				}
				
				if(errorMessageBody != ""){
					throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
				}
				
				break;
			}

	}
}
