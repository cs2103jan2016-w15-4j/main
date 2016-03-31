package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MoveParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_MOVE_COMMAND = "Error: Invalid move Command!";
	private String categoryName;
	private String taskIds;
	private Command command;
	
	public MoveParser() {
		super();
	}
	
	public Command getCommand(String input) throws IncorrectInputException {
		parse(input);
		setVariables(taskIds);
		
		try {
			parseTaskIds();
		} catch(IncorrectInputException e) {
			setInvalidCommand(e.getMessage()); 
		}
		
		if(command == null) {
			setCorrectMoveCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectMoveCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setSingleTypeMoveCommand();
			break;

		case MULTIPLE:
			setMultipleTypeMoveCommand();
			break;

		case INTERVAL:
			setIntervalTypeMoveCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setIntervalTypeMoveCommand() {
		command = CommandUtils.createSetCategoryCommand(taskIdsForTagging, categoryName);
	}

	private void setMultipleTypeMoveCommand() {
		command = CommandUtils.createSetCategoryCommand(taskIdsForTagging, categoryName);
	}

	private void setSingleTypeMoveCommand() {
		command = CommandUtils.createSetCategoryCommand(taskIdForTagging, categoryName);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MOVE_COMMAND);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	} 

	private void parse(String input) {
		command = null;
		int indexOfCategoryName = input.lastIndexOf(" ");
		categoryName = input.substring(indexOfCategoryName).trim();
		taskIds = input.replace(categoryName, "").trim();
	}
}
