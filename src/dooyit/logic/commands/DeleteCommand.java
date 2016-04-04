package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class DeleteCommand implements Command, ReversibleCommand {

	private ArrayList<Integer> deleteIds;
	private ArrayList<Task> deletedTasks;
	private boolean hasError = false;
	
	public DeleteCommand(int deleteId) {
		this.deleteIds = new ArrayList<Integer>();
		this.deletedTasks = new ArrayList<Task>();
		this.deleteIds.add(deleteId);
	}
	
	public DeleteCommand(ArrayList<Integer> deleteIds) {
		this.deleteIds = new ArrayList<Integer>();
		this.deletedTasks = new ArrayList<Task>();
		this.deleteIds.addAll(deleteIds);
	}
	
	public boolean hasError(){
		return hasError;
	}
	
	public void undo(LogicController logic){
		
		for(Task deletedTask : deletedTasks){
			logic.addTask(deletedTask);
		}
	}

	public void redo(LogicController logic){
		for(Task deletedTask : deletedTasks){
			logic.removeTask(deletedTask);
		}
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;
		
		String errorMessageBody = "";

		for (Integer deleteId : deleteIds) {
			if (logic.containsTask(deleteId)) {
				Task deletedTask = logic.removeTask(deleteId);
				deletedTasks.add(deletedTask);
				
				logicAction = new LogicAction(Action.DELETE_TASK);
			} else {
				errorMessageBody += " " + "[" + deleteId + "]";
			}
		}

		if (errorMessageBody != "") {
			logicAction = new LogicAction(Action.DELETE_TASK);
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
		
		return logicAction;
	}
}
