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

public class MoveToCategory implements ReversibleCommand {

	private String categoryName;
	private ArrayList<Integer> taskIds;
	private ArrayList<Task> tasksWithCategory;
	private Category settedCategory;
	private Category prevCategory;
	private boolean isNewCatCreated;
	private boolean hasError = false;

	public MoveToCategory(int taskId, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.add(taskId);
		this.categoryName = categoryName;
	}

	public MoveToCategory(ArrayList<Integer> taskIds, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.addAll(taskIds);
		this.categoryName = categoryName;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		for (Task task : tasksWithCategory) {
			task.setCategory(prevCategory);
		}

		if (isNewCatCreated) {
			logic.removeCategory(settedCategory);
		}
	}

	public void redo(LogicController logic) {
		for (Task task : tasksWithCategory) {
			task.setCategory(settedCategory);
		}

		if (isNewCatCreated) {
			logic.addCategory(settedCategory);
		}
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction = null;
		tasksWithCategory = new ArrayList<Task>();

		String movedTaskIdMsg = Constants.EMPTY_STRING;
		String errorMsgBody = Constants.EMPTY_STRING;

		for (int taskId : taskIds) {
			if (logic.containsTask(taskId)) {
				if (logic.containsCategory(categoryName)) {
					moveTaskToExistingCategory(logic, taskId);
				} else {
					moveTaskToNewCategory(logic, taskId);
				}
				movedTaskIdMsg += Constants.SPACE + taskId;
			} else {
				errorMsgBody += Constants.SPACE + taskId;
			}
		}

		logicAction = createLogicAction(logicAction, movedTaskIdMsg, errorMsgBody);

		return logicAction;
	}

	/**
	 * @param logic
	 * @param taskId
	 */
	public void moveTaskToNewCategory(LogicController logic, int taskId) {
		isNewCatCreated = true;
		settedCategory = logic.addCategory(categoryName);
		Task task = logic.findTask(taskId);
		prevCategory = task.getCategory();
		task.setCategory(settedCategory);
		tasksWithCategory.add(task);
	}

	/**
	 * @param logic
	 * @param taskId
	 */
	public void moveTaskToExistingCategory(LogicController logic, int taskId) {
		isNewCatCreated = false;
		settedCategory = logic.findCategory(categoryName);
		Task task = logic.findTask(taskId);
		prevCategory = task.getCategory();
		task.setCategory(settedCategory);
		tasksWithCategory.add(task);
	}

	/**
	 * @param logicAction
	 * @param movedTaskIdMsg
	 * @param errorMsgBody
	 * @return
	 */
	public LogicAction createLogicAction(LogicAction logicAction, String movedTaskIdMsg, String errorMsgBody) {
		if (!tasksWithCategory.isEmpty()) {
			if (tasksWithCategory.size() == 1) {
				logicAction = new LogicAction(Action.ADD_N_SET_CATEGORY, String.format(Constants.FEEDBACK_TASK_MOVED, movedTaskIdMsg, settedCategory.getName()));
			} else {
				logicAction = new LogicAction(Action.ADD_N_SET_CATEGORY, String.format(Constants.FEEDBACK_TASKS_MOVED, movedTaskIdMsg, settedCategory.getName()));
			}
		} else {
			hasError = true;
			if (errorMsgBody != Constants.EMPTY_STRING) {
				logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_IDS, errorMsgBody));
			}
		}
		return logicAction;
	}

}
