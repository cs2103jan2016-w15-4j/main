package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.CategoryManager;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;

public class SetCategoryCommand extends Command {

	private String categoryName;
	private ArrayList<Integer> taskIds;

	public SetCategoryCommand(int taskId, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.add(taskId);
		this.categoryName = categoryName;
	}

	// public SetCategoryCommand(ArrayList<Integer> taskIds, String
	// categoryName) {
	// this.taskIds = new ArrayList<Integer>();
	// this.taskIds.addAll(taskIds);
	// this.categoryName = categoryName;
	// }

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		CategoryManager categoryManager = logic.getCategoryManager();

		for (Integer taskId : taskIds) {
			if (taskManager.contains(taskId)) {
				if (categoryManager.contains(categoryName)) {
					Category category = categoryManager.findCategory(categoryName);
					Task task = taskManager.find(taskId);
					task.setCategory(category);
				} else {
					Category category = categoryManager.addCategory(categoryName);
					Task task = taskManager.find(taskId);
					task.setCategory(category);
					throw new IncorrectInputException("Category: " + categoryName + " is created.");
				}
			} else {
				throw new IncorrectInputException("TaskID: " + taskId + " doesn't exist.");
			}
		}
	}

}
