package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class MarkCommand extends ReversibleCommand {
	private ArrayList<Integer> markIds;
	private ArrayList<Task> markedTasks;
	
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

	@Override
	public void undo(LogicController logic){
		TaskManager taskManager = logic.getTaskManager();
		
		for(Task markedTask : markedTasks){
			taskManager.unMarkTask(markedTask);
		}
	}
	
	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);
		LogicAction logicAction = null;
		
		String errorMessageBody = "";

		for (int markId : markIds) {
			if (taskManager.contains(markId)) {
				taskManager.markTask(markId);
				Task markedTask = taskManager.find(markId);
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
