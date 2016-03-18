package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class SetCategoryCommand extends Command {

	private int taskId;
	private String categoryName;
	private CustomColor color;
	
	public SetCategoryCommand(int taskId, String categoryName) {
		this.taskId = taskId;
		this.categoryName = categoryName;
	}

	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		CategoryManager categoryManager = logic.getCategoryManager();

		if (taskManager.containsTask(taskId)) {
			if (categoryManager.containsCategory(categoryName)) {
				Category category = categoryManager.findCategory(categoryName);
				Task task = taskManager.findTask(taskId);
				task.setCategory(category);
			} else {
				Category category = categoryManager.addCategory(categoryName);
				Task task = taskManager.findTask(taskId);
				task.setCategory(category);
				throw new IncorrectInputException("Category: " + categoryName + " is created.");
			}
		} else {
			throw new IncorrectInputException("TaskID: " + taskId + " doesn't exist.");
		}
	}

}
