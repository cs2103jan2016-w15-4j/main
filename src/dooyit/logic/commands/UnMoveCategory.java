//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class UnMoveCategory implements ReversibleCommand {

	private int taskId;
	private Task taskWithCategory;
	private Category removedCategory;
	private boolean hasError = false;

	public UnMoveCategory(int taskId) {
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
			logicAction = new LogicAction(Action.ERROR, Constants.FEEDBACK_INVALID_ID);
			return logicAction;
		}
		
		taskWithCategory = logic.findTask(taskId);
		if(!taskWithCategory.hasCategory()){
			hasError = true;
			logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_CATEGORY_DOESNT_EXIST, taskId));
			return logicAction;
		}
		
		logicAction = removeCategoryFromTask();

		return logicAction;
	}

	/**
	 * @return
	 */
	public LogicAction removeCategoryFromTask() {
		LogicAction logicAction;
		removedCategory = taskWithCategory.getCategory();
		taskWithCategory.setCategory(null);
		logicAction = new LogicAction(Action.REMOVE_CAT_FROM_TASK, String.format(Constants.FEEDBACK_TASK_UNMOVED, taskId));
		return logicAction;
	}

}
