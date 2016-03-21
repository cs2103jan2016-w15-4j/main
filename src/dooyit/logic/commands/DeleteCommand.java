package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class DeleteCommand extends Command {

	private ArrayList<Integer> deleteIds;

	public DeleteCommand(int deleteId) {
		this.deleteIds = new ArrayList<Integer>();
		this.deleteIds.add(deleteId);
	}
	
	public DeleteCommand(ArrayList<Integer> deleteIds) {
		this.deleteIds = new ArrayList<Integer>();
		this.deleteIds.addAll(deleteIds);
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);
		
		String errorMessageBody = "";

		for (int i = 0; i < deleteIds.size(); i++) {
			if (taskManager.containsTask(deleteIds.get(i))) {
				taskManager.deleteTask(deleteIds.get(i));
			} else {
				errorMessageBody += " " + deleteIds.get(i);
			}
		}

		if (errorMessageBody != "") {
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}
	}
}
