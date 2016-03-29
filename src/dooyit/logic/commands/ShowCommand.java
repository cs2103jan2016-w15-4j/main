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
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);

		logic.setActiveView(uiMainViewtype);

		LogicAction logicAction = null;

		switch (uiMainViewtype) {
		case TODAY:
			logicAction = new LogicAction(Action.SHOW_TODAY_TASK);
			break;

		case EXTENDED:
			logicAction = new LogicAction(Action.SHOW_NEXT7DAY_TASK);
			break;
			
		case FLOAT:
			logicAction = new LogicAction(Action.SHOW_FLOATING_TASK);
			break;
			
		case ALL:
			logicAction = new LogicAction(Action.SHOW_ALL_TASK);
			break;
			
		case COMPLETED:
			logicAction = new LogicAction(Action.SHOW_COMPLETED);
			break;
			
		case CATEGORY:
			if (logic.containsCategory(categoryName)) {
				Category category = logic.findCategory(categoryName);
				logic.setActiveView(uiMainViewtype);
				logic.setActiveViewCategory(category);
			}

			logicAction = new LogicAction(Action.SHOW_CATEGORY);
			break;
		}

		return logicAction;
	}

}
