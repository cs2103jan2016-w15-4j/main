package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UnMarkCommand extends ReversibleCommand {

	private ArrayList<Integer> unmarkIds;
	private ArrayList<Task> unmarkedTasks;

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
	
	@Override
	public void undo(LogicController logic){
		TaskManager taskManager = logic.getTaskManager();
		
		for(Task unmarkedTask : unmarkedTasks){
			taskManager.markTask(unmarkedTask);
		}
	}

	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);
		LogicAction logicAction = null;
		
		String errorMessageBody = "";

		for (int unmarkId : unmarkIds) {
			if (logic.containsTask(unmarkId)) {
				taskManager.unMarkTask(unmarkId);
				Task unmarkedTask = taskManager.find(unmarkId);
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
