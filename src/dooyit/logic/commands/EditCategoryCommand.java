//@@author A0126356E
package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class EditCategoryCommand implements ReversibleCommand {

	private String categoryName;
	private String newCategoryName;
	private String originalCategoryName;
	private String newColourName;
	private String originalColourString;
	private boolean hasError;
	private Category originalCategory;

	public EditCategoryCommand(String categoryName, String newCategoryName) {
		this.categoryName = categoryName;
		this.newCategoryName = newCategoryName;
	}

	public EditCategoryCommand(String categoryName, String newCategoryName, String colourName) {
		this(categoryName, newCategoryName);
		this.newColourName = colourName;
	}

	private boolean editCategoryWithColour() {
		return newColourName != null;
	}

	@Override
	public void undo(LogicController logic) {
		assert (logic != null);

		if (editCategoryWithColour()) {
			logic.editCategoryColour(originalCategory, originalColourString);
			logic.editCategoryName(originalCategory, originalCategoryName);
		} else {
			logic.editCategoryName(originalCategory, originalCategoryName);
		}
	}

	@Override
	public void redo(LogicController logic) {
		assert (logic != null);
		if (editCategoryWithColour()) {
			logic.editCategoryColour(originalCategory, newColourName);
			logic.editCategoryName(originalCategory, newCategoryName);
		} else {
			logic.editCategoryName(originalCategory, newCategoryName);
		}
	}

	@Override
	public boolean hasError() {
		return hasError;
	}

	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;

		// error if cannot find category
		if (!logic.containsCategory(categoryName)) {
			logicAction = cantFindCategory();
			return logicAction;
		}

		saveOriginalCategory(logic);

		if (editCategoryWithColour()) {
			if (logic.containsCustomColour(newColourName)) {
				logicAction = editCategoryNameAndColour(logic);
			} else {
				logicAction = invalidColour();
			}
		} else {
			logicAction = editCategoryName(logic);
			
		}

		return logicAction;
	}

	public LogicAction invalidColour() {
		return new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_INVALID_COLOUR, newColourName));
	}

	public LogicAction cantFindCategory() {
		hasError = true;
		return new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_CATEGORY_NOT_FOUND, categoryName));
	}

	public LogicAction editCategoryNameAndColour(LogicController logic) {
		LogicAction logicAction;
		originalColourString = originalCategory.getCustomColourName();
		logic.editCategoryColour(originalCategory, newColourName);
		editCategoryName(logic);
		logicAction = new LogicAction(Action.EDIT_CATEGORY, Constants.FEEDBACK_CATEGORY_EDITED);
		return logicAction;
	}

	public void saveOriginalCategory(LogicController logic) {
		originalCategory = logic.findCategory(categoryName);
		originalCategoryName = originalCategory.getName();
	}

	public LogicAction editCategoryName(LogicController logic) {
		logic.editCategoryName(originalCategory, newCategoryName);
		LogicAction logicAction = new LogicAction(Action.EDIT_CATEGORY, Constants.FEEDBACK_CATEGORY_EDITED);
		return logicAction;
	}

}
