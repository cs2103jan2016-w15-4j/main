//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class MoveToCategory implements Command, ReversibleCommand {

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
		
		for (int taskId : taskIds) {
			if (logic.containsTask(taskId)) {
				if (logic.containsCategory(categoryName)) {
					isNewCatCreated = false;
					settedCategory = logic.findCategory(categoryName);
					Task task = logic.findTask(taskId);
					prevCategory = task.getCategory();
					task.setCategory(settedCategory);
					tasksWithCategory.add(task);
					logicAction = new LogicAction(Action.SET_CATEGORY);
				} else {
					isNewCatCreated = true;
					settedCategory = logic.addCategory(categoryName);
					Task task = logic.findTask(taskId);
					prevCategory = task.getCategory();
					task.setCategory(settedCategory);
					tasksWithCategory.add(task);
					logicAction = new LogicAction(Action.ADD_N_SET_CATEGORY);
					//throw new IncorrectInputException("Category: " + categoryName + " is created.");
				}
			} else {
				logicAction = new LogicAction(Action.ERROR);
				hasError = true;
				//throw new IncorrectInputException("TaskID: " + taskId + " doesn't exist.");
			}
		}

		return logicAction;
	}

}
