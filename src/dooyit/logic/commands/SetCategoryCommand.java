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

public class SetCategoryCommand implements Command, ReversibleCommand {

	private String categoryName;
	private ArrayList<Integer> taskIds;
	private ArrayList<Task> tasksWithCategory;
	private Category category;
	private boolean isNewCatCreated;

	public SetCategoryCommand(int taskId, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.add(taskId);
		this.categoryName = categoryName;
	}
	
	public SetCategoryCommand(ArrayList<Integer> taskIds, String categoryName) {
		this.taskIds = new ArrayList<Integer>();
		this.taskIds.addAll(taskIds);
		this.categoryName = categoryName;
	}
	
	public void undo(LogicController logic){
		for (Task task : tasksWithCategory) {
			task.setCategory(null);
		}
		
		if(isNewCatCreated){
			logic.removeCategory(category);
		}
	}

	public void redo(LogicController logic){
		for (Task task : tasksWithCategory) {
			task.setCategory(category);
		}
		
		if(isNewCatCreated){
			logic.addCategory(category);
		}
	}
	
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction = null;
		tasksWithCategory = new ArrayList<Task>();
		
		for (int taskId : taskIds) {
			if (logic.containsTask(taskId)) {
				if (logic.containsCategory(categoryName)) {
					isNewCatCreated = false;
					category = logic.findCategory(categoryName);
					Task task = logic.findTask(taskId);
					task.setCategory(category);
					tasksWithCategory.add(task);
					logicAction = new LogicAction(Action.SET_CATEGORY);
				} else {
					isNewCatCreated = true;
					category = logic.addCategory(categoryName);
					Task task = logic.findTask(taskId);
					task.setCategory(category);
					tasksWithCategory.add(task);
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
