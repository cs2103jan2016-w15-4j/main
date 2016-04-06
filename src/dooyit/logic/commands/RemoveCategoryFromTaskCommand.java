//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class RemoveCategoryFromTaskCommand implements Command, ReversibleCommand {

	private String categoryName;
	private int taskId;
	private Task taskWithCategory;
	private Category removedCategory;
	private boolean hasError = false;

	public RemoveCategoryFromTaskCommand(String categoryName, int taskId) {
		this.categoryName = categoryName;
		this.taskId = taskId;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		taskWithCategory.setCategory(removedCategory);
	}

	public void redo(LogicController logic) {
		taskWithCategory.removeCategory();
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		if (!logic.containsTask(taskId)) {
			hasError = true;
			logicAction = new LogicAction(Action.ERROR, "Index: " + taskId + "doesn't exist.");
			return logicAction;
		}

		if (!logic.containsCategory(categoryName)) {
			hasError = true;
			logicAction = new LogicAction(Action.ERROR, "Category: " + categoryName + " doesn't exist.");
			return logicAction;
		}

		taskWithCategory = logic.findTask(taskId);
		removedCategory = taskWithCategory.getCategory();
		taskWithCategory.setCategory(null);
		logicAction = new LogicAction(Action.REMOVE_CAT_FROM_TASK, "Category succesfully removed.");

		return logicAction;
	}

}
