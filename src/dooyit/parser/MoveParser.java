package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MoveParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_MOVE_COMMAND = "Error: Invalid move Command!";
	private static final int INDEX_NAME = 0;
	private String catName;
	private String taskIds;
	private String userInput; 
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

		/*case MULTIPLE:
			setMultipleTypeMoveCommand();
			break;

		case INTERVAL:
			setIntervalTypeMoveCommand();
			break;*/

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setIntervalTypeMoveCommand() {
		//comamnd = CommandUtils.createMoveCommand(catName, taskIdsForTagging);
		command = null;
	}

	// Eg. delete 5 6 8
	private void setMultipleTypeMoveCommand() {
		//command = CommandUtils.createMoveCommand(catName, taskIdsForTagging);
		command = null;
	}

	private void setSingleTypeMoveCommand() {
		command = CommandUtils.createSetCategoryCommand(taskIdForTagging, catName);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MOVE_COMMAND);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void parse(String input) {
		command = null;
		userInput = input;
		String[] inputArr = userInput.split("\\s+");
		catName = inputArr[INDEX_NAME];
		int indexOfTaskIds = userInput.indexOf(catName) + catName.length();
		taskIds = userInput.substring(indexOfTaskIds).trim();
	}
}
