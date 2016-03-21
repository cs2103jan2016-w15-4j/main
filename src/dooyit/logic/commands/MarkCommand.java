package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class MarkCommand extends Command {
	private ArrayList<Integer> markIds;

	public MarkCommand() {
		markIds = new ArrayList<Integer>();
	}

	public void initMarkCommand(int deleteId) {
		markIds.add(deleteId);
	}

	public void initMarkCommand(ArrayList<Integer> deleteIds) {
		this.markIds.addAll(deleteIds);
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		assert (taskManager != null);

		String errorMessageBody = "";

		for (int i = 0; i < markIds.size(); i++) {
			if (taskManager.containsTask(markIds.get(i))) {
				taskManager.markTask(markIds.get(i));
			} else {
				errorMessageBody += " " + markIds.get(i);
			}
		}

		if (errorMessageBody != "") {
			throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
		}

	}
}
