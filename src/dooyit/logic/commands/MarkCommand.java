package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class MarkCommand implements Command, ReversibleCommand {
	private ArrayList<Integer> markIds;
	private ArrayList<Task> markedTasks;
	private boolean hasError = false;
	
	public MarkCommand(int markId) {
		this.markIds = new ArrayList<Integer>();
		this.markedTasks = new ArrayList<Task>();
		this.markIds.add(markId);
	}

	public MarkCommand(ArrayList<Integer> markIds) {
		this.markIds = new ArrayList<Integer>();
		this.markedTasks = new ArrayList<Task>();
		this.markIds.addAll(markIds);
	}

	public boolean hasError(){
		return hasError;
	}
	
	public void undo(LogicController logic){
		for(Task markedTask : markedTasks){
			logic.unmarkTask(markedTask);
		}
	}
	
	public void redo(LogicController logic){
		for(Task markedTask : markedTasks){
			logic.markTask(markedTask);
		}
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;
		
		String errorMessageBody = "";

		for (int markId : markIds) {
			if (logic.containsTask(markId)) {
				logic.markTask(markId);
				Task markedTask = logic.findTask(markId);
				markedTasks.add(markedTask);
				logicAction = new LogicAction(Action.MARK_TASK);
			} else {
				errorMessageBody += " " + markId;
			}
		}

		if (errorMessageBody != "") {
			logicAction = new LogicAction(Action.MARK_TASK);
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
		
		return logicAction;
	}
}
