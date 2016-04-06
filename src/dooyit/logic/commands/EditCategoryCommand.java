package dooyit.logic.commands;

import dooyit.common.datatype.Category;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.Constants;
import dooyit.logic.api.Action;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class EditCategoryCommand implements Command, ReversibleCommand {

	private static final String FEEDBACK_CATEGORY_EDITED = "Category has been edited.";
	private String categoryName;
	private String newCategoryName;
	private String originalCategoryName;
	private String newColourName;
	private String originalColourString;
	private boolean hasError;
	Category originalCategory;
	Category editedCategory;

	EditCategoryCommand(String categoryName, String newCategoryName) {
		this.categoryName = categoryName;
		this.newCategoryName = newCategoryName;
	}

	EditCategoryCommand(String categoryName, String newCategoryName, String colourName) {
		this(categoryName, newCategoryName);
		this.newColourName = colourName;
	}

	private boolean hasColorString() {
		return newColourName != null;
	}

	@Override
	public void undo(LogicController logic) {
		assert (logic != null);

		if (hasColorString()) {
			logic.editCategoryColour(originalCategory, originalColourString);
		} else {
			logic.editCategoryName(originalCategory, originalCategoryName);
		}

	}

	@Override
	public void redo(LogicController logic) {
		assert (logic != null);
		if (hasColorString()) {
			logic.editCategoryColour(originalCategory, newColourName);
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

		if (!logic.containsCategory(categoryName)) {
			logicAction = new LogicAction(Action.ERROR,
					String.format(Constants.FEEDBACK_CATEGORY_NOT_FOUND, categoryName));
			hasError = true;
			return logicAction;
		}

		originalCategory = logic.findCategory(categoryName);
		originalCategoryName = originalCategory.getName();
		if (hasColorString()) {
			if (logic.containsCustomColour(newColourName)) {
				logic.editCategoryColour(originalCategory, newColourName);
				originalColourString = originalCategory.getCustomColourName();
				logicAction = new LogicAction(Action.EDIT_CATEGORY, FEEDBACK_CATEGORY_EDITED);
			} else {
				logicAction = new LogicAction(Action.EDIT_CATEGORY,
						String.format(Constants.FEEDBACK_INVALID_COLOUR, newColourName));
			}
		} else {
			logic.editCategoryName(originalCategory, newCategoryName);
			logicAction = new LogicAction(Action.EDIT_CATEGORY, FEEDBACK_CATEGORY_EDITED);
		}

		return logicAction;
	}

}
