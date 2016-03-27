package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class RemoveParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_REMOVE_COMMAND = "Error: Invalid remove Command!";
	private Command command;

	public RemoveParser() {
		super();
	}

	public Command getCommand(String input) {
		setVariables(input);
		command = null;
		
		try {
			parseTaskIds();
		} catch(IncorrectInputException e) {
			command = getInvalidCommand(e.getMessage());
		}
		
		if(command == null) {
			setCorrectRemoveCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectRemoveCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			command = getSingleTypeRemoveCommand();
			break;

		case MULTIPLE:
			command = getMultipleTypeRemoveCommand();
			break;

		case INTERVAL:
			command = getIntervalTypeRemoveCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getIntervalTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		return null;
	}

	// Eg. delete 5 6 8
	private Command getMultipleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		return null;
	}

	private Command getSingleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdForTagging);
		return null;
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_REMOVE_COMMAND);
	}
}
