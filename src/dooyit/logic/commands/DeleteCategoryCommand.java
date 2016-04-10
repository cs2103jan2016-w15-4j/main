//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class DeleteCategoryCommand implements ReversibleCommand {

	private String categoryName;
	private Category removedCategory;
	private ArrayList<Task> removedTask;
	private boolean hasError = false;

	public DeleteCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		logic.loadTasks(removedTask);
		logic.addCategory(removedCategory);
	}

	public void redo(LogicController logic) {
		logic.removeCategory(removedCategory);
		for (Task task : removedTask) {
			logic.removeTask(task);
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		if (logic.containsCategory(categoryName)) {
			logicAction = deleteCategoryAndAllTaskWithTheCategory(logic);
		} else {
			logicAction = cantFindCategory();
		}
		return logicAction;
	}

	public LogicAction cantFindCategory() {
		LogicAction logicAction;
		logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_CATEGORY_NOT_FOUND, categoryName));
		hasError = true;
		return logicAction;
	}

	/**
	 * @param logic
	 * @return
	 */
	public LogicAction deleteCategoryAndAllTaskWithTheCategory(LogicController logic) {
		LogicAction logicAction;
		removedCategory = logic.removeCategory(categoryName);
		removedTask = logic.removeTasksWithCategory(removedCategory);
		logicAction = new LogicAction(Action.DELETE_CATEGORY, String.format(Constants.FEEDBACK_DELETE_CATEGORY, categoryName));
		return logicAction;
	}

}
