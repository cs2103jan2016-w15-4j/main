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
	private String colourName;
	private boolean hasError;
	Category originalCategory;
	Category editedCategory;

	EditCategoryCommand(String categoryName, String newCategoryName) {
		this.categoryName = categoryName;
		this.newCategoryName = newCategoryName;
	}

	EditCategoryCommand(String categoryName, String newCategoryName, String colourName) {
		this(categoryName, newCategoryName);
		this.colourName = colourName;
	}

	private boolean hasColorString() {
		return colourName != null;
	}

	@Override
	public void undo(LogicController logic) {
		assert (logic != null);
		logic.removeCategory(editedCategory);
		logic.addCategory(originalCategory);
	}

	@Override
	public void redo(LogicController logic) {
		assert (logic != null);
		logic.addCategory(editedCategory);
		logic.removeCategory(originalCategory);
	}

	@Override
	public boolean hasError() {
		return hasError;
	}

	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {
		assert (logic != null);
		LogicAction logicAction;
		String invalidColourMsg = String.format(Constants.FEEDBACK_INVALID_COLOUR, colourName);
		
		if (!logic.containsCategory(categoryName)) {
			logicAction = new LogicAction(Action.ERROR, String.format(Constants.FEEDBACK_CATEGORY_NOT_FOUND, categoryName));
			hasError = true;
			return logicAction;
		}

		originalCategory = logic.findCategory(categoryName);
		if (hasColorString()) {
			if(logic.containsCustomColour(colourName)){
				editedCategory = logic.editCategory(originalCategory, newCategoryName, colourName);
				logicAction = new LogicAction(Action.EDIT_CATEGORY, FEEDBACK_CATEGORY_EDITED);
			}else{
				editedCategory = logic.editCategory(originalCategory, newCategoryName);
				logicAction = new LogicAction(Action.EDIT_CATEGORY, FEEDBACK_CATEGORY_EDITED + invalidColourMsg);
			}
		} else {
			editedCategory = logic.editCategory(originalCategory, newCategoryName);
			logicAction = new LogicAction(Action.EDIT_CATEGORY, FEEDBACK_CATEGORY_EDITED);
		}

		return logicAction;
	}

}
