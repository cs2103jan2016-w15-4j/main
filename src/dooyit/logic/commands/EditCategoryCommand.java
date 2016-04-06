package dooyit.logic.commands;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.api.LogicAction;
import dooyit.logic.api.LogicController;

public class EditCategoryCommand implements Command, ReversibleCommand {

	String categoryName;
	String colourName;

	EditCategoryCommand(String categoryName) {
		this.categoryName = categoryName;
	}

	EditCategoryCommand(String categoryName, String colourName) {
		
	}
	
	@Override
	public void undo(LogicController logic) {
		
	}

	@Override
	public void redo(LogicController logic) {

	}

	@Override
	public boolean hasError() {

		return false;
	}

	@Override
	public LogicAction execute(LogicController logic) throws IncorrectInputException {

		return null;
	}

}
