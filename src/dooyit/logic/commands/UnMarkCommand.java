package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class UnMarkCommand extends Command {

private ArrayList<Integer> unMarkIds;
	
	public UnMarkCommand(int unMarkId) {
		this.unMarkIds = new ArrayList<Integer>();
		this.unMarkIds.add(unMarkId);
	}

	public UnMarkCommand(ArrayList<Integer> unMarkIds) {
		this.unMarkIds = new ArrayList<Integer>();
		this.unMarkIds.addAll(unMarkIds);
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);

		String errorMessageBody = "";

		for (int i = 0; i < unMarkIds.size(); i++) {
			if (taskManager.containsTask(unMarkIds.get(i))) {
				taskManager.unMarkTask(unMarkIds.get(i));
			} else {
				errorMessageBody += " " + unMarkIds.get(i);
			}
		}

		if (errorMessageBody != "") {
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}

	}
}
