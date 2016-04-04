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
	private String colorString;
	Category addedCategory;
	private boolean hasError = false;

	public AddCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
	}

	public AddCategoryCommand(String categoryName, String colorString) {
		this.categoryName = categoryName;
		this.colorString = colorString;
	}

	private boolean hasColorString() {
		return colorString != null;
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
		LogicAction logicAction;

		String feedbackMsgColor = "";

		if (!logic.containsCategory(categoryName)) {
			if (hasColorString()) {
				try {
					addedCategory = logic.addCategory(categoryName, colorString);
				} catch (IncorrectInputException e) {
					feedbackMsgColor += e.getMessage();
				}
			} else {
				addedCategory = logic.addCategory(categoryName);
			}

			logicAction = new LogicAction(Action.ADD_CATEGORY, String.format(Constants.FEEDBACK_CATEGORY_ADDED, categoryName, feedbackMsgColor));
		} else {
			logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_FAIL_CATEGORY_EXISTS, categoryName));
			hasError = true;
		}

		return logicAction;
	}

}
