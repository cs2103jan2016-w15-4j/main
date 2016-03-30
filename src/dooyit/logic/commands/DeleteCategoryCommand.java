package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class DeleteCategoryCommand extends ReversibleCommand {

	private String categoryName;
	Category removedCategory;
	ArrayList<Task> removedTask;
	
	public DeleteCategoryCommand(String categoryName){
		this.categoryName = categoryName;
	}
	
	@Override
	public void undo(LogicController logic) {
		logic.loadTasks(removedTask);
		logic.addCategory(removedCategory);
	}
	
	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;
		
		if(logic.containsCategory(categoryName)){
			removedCategory = logic.removeCategory(categoryName);
			removedTask = logic.removeTasksWithCategory(removedCategory);
			logicAction = new LogicAction(Action.DELETE_CATEGORY);
		}else{
			logicAction = new LogicAction(Action.ERROR);
			throw new IncorrectInputException("Category: " + categoryName + " doesn't exist.");
		}
		return logicAction;
	}

}
