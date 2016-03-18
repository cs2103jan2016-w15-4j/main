package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.core.CategoryManager;
import dooyit.logic.core.Logic;
import dooyit.logic.core.TaskManager;

public class SetCategoryCommand extends Command {

	private int taskId;
	private String categoryName;
	
	public SetCategoryCommand(int taskId, String categoryName){
		this.taskId = taskId;
		this.categoryName = categoryName;
	}
	
	
	@Override
	public void execute(Logic logic) throws IncorrectInputException {
		TaskManager taskManager = logic.getTaskManager();
		CategoryManager categoryManager = logic.getCategoryManager();
		
		if(taskManager.containsTask(taskId)){
			
		}
	}

}
