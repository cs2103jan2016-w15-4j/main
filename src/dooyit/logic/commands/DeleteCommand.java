package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class DeleteCommand extends ReversibleCommand {

	private ArrayList<Integer> deleteIds;
	private ArrayList<Task> deletedTasks;

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
	
	@Override
	public void undo(LogicController logic){
		TaskManager taskManager = logic.getTaskManager();
		
		for(Task deletedTask : deletedTasks){
			taskManager.add(deletedTask);
		}
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);
		
		String errorMessageBody = "";

		for (Integer deleteId : deleteIds) {
			if (taskManager.contains(deleteId)) {
				Task deletedTask = taskManager.remove(deleteId);
				deletedTasks.add(deletedTask);
			} else {
				errorMessageBody += " " + deleteIds;
			}
		}

		if (errorMessageBody != "") {
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
	}
}
