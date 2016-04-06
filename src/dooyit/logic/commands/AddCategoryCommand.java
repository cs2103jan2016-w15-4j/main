//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class AddCategoryCommand implements Command, ReversibleCommand {

	private String categoryName;
	private String colourString;
	Category addedCategory;
	private boolean hasError = false;

	public AddCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
	}

	public AddCategoryCommand(String categoryName, String colorString) {
		this.categoryName = categoryName;
		this.colourString = colorString;
	}

	private boolean hasColorString() {
		return colourString != null;
	}

	public boolean hasError() {
		return hasError;
	}

	public void undo(LogicController logic) {
		logic.removeCategory(addedCategory);
	}

	public void redo(LogicController logic) {
		logic.addCategory(addedCategory);
	}

	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert(logic != null);
		LogicAction logicAction;

		String feedbackMsgColor = Constants.EMPTY_STRING;

		if (logic.containsCategory(categoryName)) {
			logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_FAIL_CATEGORY_EXISTS, categoryName));
			hasError = true;
			return logicAction;
		}

		if (hasColorString()) {
			if (logic.containsCustomColour(colourString)) {
				addedCategory = logic.addCategory(categoryName, colourString);
			} else {
				addedCategory = logic.addCategory(categoryName);
				feedbackMsgColor += String.format(Constants.FEEDBACK_INVALID_COLOUR, colourString);
			}
		} else {
			addedCategory = logic.addCategory(categoryName);
		}

		logicAction = new LogicAction(Action.ADD_CATEGORY, String.format(Constants.FEEDBACK_CATEGORY_ADDED, categoryName, feedbackMsgColor));

		return logicAction;
	}

}
