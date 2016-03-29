package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.common.exception.IncorrectInputException;
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
	}
	
	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		
		if(logic.containsCategory(categoryName)){
			removedCategory = logic.removeCategory(categoryName);
			removedTask = logic.removeTasksWithCategory(removedCategory);
		}else{
			throw new IncorrectInputException("Category: " + categoryName + " doesn't exist.");
		}
		
	}

}
