package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.CategoryManager;
import dooyit.logic.api.LogicController;
import dooyit.logic.api.TaskManager;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;

public class ShowCommand extends Command {

	String categoryName;
	UIMainViewType uiMainViewtype;

	public ShowCommand(UIMainViewType uiMainViewtype) {
		this.uiMainViewtype = uiMainViewtype;
	}

	public ShowCommand(UIMainViewType uiMainViewtype, String categoryName) {
		this.uiMainViewtype = uiMainViewtype;
		this.categoryName = categoryName;
	}

	@Override
	public void execute(LogicController logic) throws IncorrectInputException {
		UIController uiController = logic.getUIController();
		CategoryManager categoryManager = logic.getCategoryManager();
		TaskManager taskManager = logic.getTaskManager();
		
//		if(uiController == null)
//			return;
		
		if(uiMainViewtype == UIMainViewType.CATEGORY){
			if(categoryManager.contains(categoryName)){
				Category category = categoryManager.findCategory(categoryName);
				uiController.setActiveViewType(uiMainViewtype);
				uiController.refreshMainView(taskManager.getTaskGroupsCompleted(), category);
			}
		}

		uiController.setActiveViewType(uiMainViewtype);
	}

}
