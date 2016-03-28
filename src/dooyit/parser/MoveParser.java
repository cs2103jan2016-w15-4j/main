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
	
	public Command getCommand(String input) {
		parse(input);
		setVariables(taskIds);
		
		try {
			parseTaskIds();
		} catch(IncorrectInputException e) {
			command = getInvalidCommand(e.getMessage());
		}
		
		if(command == null) {
			setCorrectMoveCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectMoveCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			command = getSingleTypeMoveCommand();
			break;

		case MULTIPLE:
			command = getMultipleTypeMoveCommand();
			break;

		case INTERVAL:
			command = getIntervalTypeMoveCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getIntervalTypeMoveCommand() {
		//return CommandUtils.createMoveCommand(catName, taskIdsForTagging);
		return null;
	}

	// Eg. delete 5 6 8
	private Command getMultipleTypeMoveCommand() {
		//return CommandUtils.createMoveCommand(catName, taskIdsForTagging);
		return null;
	}

	private Command getSingleTypeMoveCommand() {
		System.out.println("reached here in get single type move command");
		return CommandUtils.createSetCategoryCommand(taskIdForTagging, catName);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MOVE_COMMAND);
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
