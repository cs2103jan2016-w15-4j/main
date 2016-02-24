package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.exception.IncorrectInputException;
import dooyit.logic.Logic;
import dooyit.logic.TaskManager;

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
		
		switch (markCommandType) {

			case SINGLE:
				if (taskManager.markTask(markId) == null) {
					throw new IncorrectInputException("Index " + markId + " doesn't exists");
				}
				break;
				
			case MULTIPLE:
				String errorMessageBody = null;
				
				for(int i=0; i<markIds.size(); i++){
					if (taskManager.markTask(markIds.get(i)) == null) {
						errorMessageBody += " " + markIds.get(i);
					}
					
				}
				
				if(errorMessageBody != null){
					throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
				}
				
				break;
			}

	}
}
