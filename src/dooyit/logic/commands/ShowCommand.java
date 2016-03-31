package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.CategoryManager;
import dooyit.logic.TaskManager;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;
import dooyit.ui.UIController;
import dooyit.ui.UIMainViewType;

public class ShowCommand implements Command {

	String categoryName;
	UIMainViewType uiMainViewtype;

	public ShowCommand(UIMainViewType uiMainViewtype) {
		this.uiMainViewtype = uiMainViewtype;
	}

	public ShowCommand(UIMainViewType uiMainViewtype, String categoryName) {
		this.uiMainViewtype = uiMainViewtype;
		this.categoryName = categoryName;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);

		LogicAction logicAction = null;

		switch (uiMainViewtype) {
		case TODAY:
			logic.setActiveView(uiMainViewtype);
			logicAction = new LogicAction(Action.SHOW_TODAY_TASK);
			break;

		case EXTENDED:
			logic.setActiveView(uiMainViewtype);
			logicAction = new LogicAction(Action.SHOW_NEXT7DAY_TASK);
			break;
			
		case FLOAT:
			logic.setActiveView(uiMainViewtype);
			logicAction = new LogicAction(Action.SHOW_FLOATING_TASK);
			break;
			
		case ALL:
			logic.setActiveView(uiMainViewtype);
			logicAction = new LogicAction(Action.SHOW_ALL_TASK);
			break;
			
		case COMPLETED:
			logic.setActiveView(uiMainViewtype);
			logicAction = new LogicAction(Action.SHOW_COMPLETED);
			break;
			
		case CATEGORY:
			if (logic.containsCategory(categoryName)) {
				Category category = logic.findCategory(categoryName);
				logic.setSelectedCategory(category);
				logic.setActiveView(uiMainViewtype);
				logic.setActiveViewCategory(category);
				
				logicAction = new LogicAction(Action.SHOW_CATEGORY);
			}
			else{
				logicAction = new LogicAction(Action.ERROR, "Category not found.");
			}
			break;
		}

		return logicAction;
	}

}
