package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.LogicController;
import dooyit.logic.core.TaskManager;

public class DeleteCommand extends Command {

	private int deleteId;
	private ArrayList<Integer> deleteIds;

	public DeleteCommand() {
		deleteIds = new ArrayList<Integer>();
	}

	public void initDeleteCommand(int deleteId) {
		deleteIds.add(deleteId);
	}

	public void initDeleteCommand(ArrayList<Integer> deleteIds) {
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
		
		
//		switch (deleteCommandType) {
//
//		case SINGLE:
//			if (taskManager.containsTask(deleteId)) {
//				taskManager.deleteTask(deleteId);
//			} else {
//				throw new IncorrectInputException("Task ID " + deleteId + " doesn't exists");
//			}
//			break;
//
//		case MULTIPLE:
//			String errorMessageBody = "";
//
//			for (int i = 0; i < deleteIds.size(); i++) {
//				if (taskManager.containsTask(deleteIds.get(i))) {
//					taskManager.deleteTask(deleteIds.get(i));
//				} else {
//					errorMessageBody += " " + deleteIds.get(i);
//				}
//			}
//
//			if (errorMessageBody != "") {
//				throw new IncorrectInputException("Index" + errorMessageBody + " doesn't exists");
//			}
//
//			break;
//		}
		
		
		

	}
}
