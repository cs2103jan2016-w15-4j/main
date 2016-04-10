//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MoveParser extends TagParser {
	private static final String ERROR_MESSAGE_NO_CATEGORY_SPECIFIED = "No category specified!";
	private static final String ERROR_MESSAGE_INVALID_MOVE_COMMAND = "Invalid move Command!";
	private String categoryName;
	private String taskIds;
	private Command command;
	private boolean hasCategory;
	
	public MoveParser() {
		super();
	}
	
	public Command getCommand(String input) throws IncorrectInputException {
		parse(input);
		if (!hasCategory) {
			setInvalidCommand(ERROR_MESSAGE_NO_CATEGORY_SPECIFIED); 
		} else {
			setAttributesForTagging(taskIds);
			try {
				parseTaskIds();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage()); 
			}
		}
		
		if (command == null) {
			setCorrectMoveCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectMoveCommand(TagType tagType) {
		switch (tagType) {
		case VALID :
			setMultipleTypeMoveCommand();
			break;

		default :
			setInvalidCommand();
			break;
		}
	}

	private void setMultipleTypeMoveCommand() {
		command = CommandUtils.createMoveToCategoryCommand(taskIdsForTagging, categoryName);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MOVE_COMMAND);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	} 

	private void parse(String input) {
		command = null;
		hasCategory = false;
		String[] splitInput = input.split("\\s+");
		int indexOfCategoryName = splitInput.length - 1;
		categoryName = splitInput[indexOfCategoryName];
		
		if (ParserCommons.isNumber(categoryName) || isIntervalType(categoryName)) {
			hasCategory = false;
		} else {
			hasCategory = true;
			taskIds = input.replace(categoryName, EMPTY_STRING).trim();
		}
	}
}
