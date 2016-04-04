package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UnMarkCommand implements Command, ReversibleCommand{

	private ArrayList<Integer> unmarkIds;
	private ArrayList<Task> unmarkedTasks;
	private boolean hasError = false;

	public UnMarkCommand(int unMarkId) {
		this.unmarkIds = new ArrayList<Integer>();
		this.unmarkedTasks = new ArrayList<Task>();
		this.unmarkIds.add(unMarkId);
	}

	public UnMarkCommand(ArrayList<Integer> unMarkIds) {
		this.unmarkIds = new ArrayList<Integer>();
		this.unmarkedTasks = new ArrayList<Task>();
		this.unmarkIds.addAll(unMarkIds);
	}
	
	public boolean hasError(){
		return hasError;
	}
	
	public void undo(LogicController logic){
		
		for(Task unmarkedTask : unmarkedTasks){
			logic.markTask(unmarkedTask);
		}
	}

	public void redo(LogicController logic){
		for(Task unmarkedTask : unmarkedTasks){
			logic.unmarkTask(unmarkedTask);
		}
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;
		
		String errorMessageBody = "";

		for (int unmarkId : unmarkIds) {
			if (logic.containsTask(unmarkId)) {
				logic.unmarkTask(unmarkId);
				Task unmarkedTask = logic.findTask(unmarkId);
				unmarkedTasks.add(unmarkedTask);
				logicAction = new LogicAction(Action.UNMARK_TASK);
			} else {
				errorMessageBody += " " + unmarkId;
			}
		}

		if (errorMessageBody != "") {
			logicAction = new LogicAction(Action.UNMARK_TASK);
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
		
		return logicAction;
	}
}
