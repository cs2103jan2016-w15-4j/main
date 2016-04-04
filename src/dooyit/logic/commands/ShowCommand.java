//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class ShowCommand implements Command {

	public enum ShowCommandType {
		TODAY, NEXT7DAY, FLOAT, ALL, COMPLETED, CATEGORY
	}

	String categoryName;
	ShowCommandType showCommandType;
	private boolean hasError = false;

	public ShowCommand(ShowCommandType showCommandType) {
		this.showCommandType = showCommandType;
	}

	public ShowCommand(ShowCommandType showCommandType, String categoryName) {
		this.showCommandType = showCommandType;
		this.categoryName = categoryName;
	}

	public boolean hasError() {
		return hasError;
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);

		LogicAction logicAction = null;

		switch (showCommandType) {
		case TODAY:
			logicAction = new LogicAction(Action.SHOW_TODAY_TASK);
			break;

		case NEXT7DAY:
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
				logic.setSelectedCategory(category);
				logicAction = new LogicAction(Action.SHOW_CATEGORY);
			} else {
				logicAction = new LogicAction(Action.ERROR, "Category not found.");
			}
			break;
		}

		return logicAction;
	}

}
