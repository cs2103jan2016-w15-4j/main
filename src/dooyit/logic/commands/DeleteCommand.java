package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.TaskManager;
import dooyit.logic.api.LogicController;

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
		assert (logic != null);
		
		String errorMessageBody = "";

		for (Integer deleteId : deleteIds) {
			if (logic.containsTask(deleteId)) {
				Task deletedTask = logic.removeTask(deleteId);
				deletedTasks.add(deletedTask);
			} else {
				errorMessageBody += " " + "[" + deleteId + "]";
			}
		}

		if (errorMessageBody != "") {
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
	}
}
