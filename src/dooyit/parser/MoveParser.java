package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MoveParser extends TagParser {
	private static final int INDEX_NAME = 0;
	private static String catName;
	private static String taskIds;
	private static String userInput;
	private static Command command;
	
	public MoveParser() {
		super();
	}
	
	public Command getCommand(String input) throws IncorrectInputException {
		parse(input);
		setVariables(taskIds);
		switch (getTagType()) {
		case SINGLE:
			try {
				parseSingleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getSingleTypeMoveCommand();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getMultipleTypeMoveCommand();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getIntervalTypeMoveCommand();
			break;

		case INVALID:
			command = getInvalidCmd();
			break;
		}
		return command;
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
		return CommandUtils.createSetCategoryCommand(taskIdForTagging, catName);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Delete Command!");
	}

	private void parse(String input) {
		command = null;
		userInput = input;
		String[] inputArr = userInput.split("//s+");
		catName = inputArr[INDEX_NAME];
		int indexTaskIds = userInput.indexOf(catName) + catName.length();
		taskIds = userInput.substring(indexTaskIds);
	}
}
