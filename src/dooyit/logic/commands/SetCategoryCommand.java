package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.CategoryManager;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class SetCategoryCommand implements Command {

	private String categoryName;
	private ArrayList<Integer> taskIds;

	public SetCategoryCommand(int taskId, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.add(taskId);
		this.categoryName = categoryName;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction = null;
		
		for (Integer taskId : taskIds) {
			if (logic.containsTask(taskId)) {
				if (logic.containsCategory(categoryName)) {
					Category category = logic.findCategory(categoryName);
					Task task = logic.findTask(taskId);
					task.setCategory(category);
					logicAction = new LogicAction(Action.SET_CATEGORY);
				} else {
					Category category = logic.addCategory(categoryName);
					Task task = logic.findTask(taskId);
					task.setCategory(category);
					logicAction = new LogicAction(Action.ADD_CATEGORY);
					throw new IncorrectInputException("Category: " + categoryName + " is created.");
				}
			} else {
				logicAction = new LogicAction(Action.ERROR);
				throw new IncorrectInputException("TaskID: " + taskId + " doesn't exist.");
			}
		}
		
		return logicAction;
	}

}
