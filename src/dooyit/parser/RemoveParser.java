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

	public Command getCommand(String input) throws IncorrectInputException {
		setVariables(input);
		command = null;
		
		try {
			parseTaskIds();
		} catch(IncorrectInputException e) {
			setInvalidCommand(e.getMessage());
		}
		
		if(command == null) {
			setCorrectRemoveCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectRemoveCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setSingleTypeRemoveCommand();
			break;

		case MULTIPLE:
			setMultipleTypeRemoveCommand();
			break;

		case INTERVAL:
			setIntervalTypeRemoveCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setIntervalTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		command = null;
	}

	// Eg. delete 5 6 8
	private void setMultipleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		command = null;
	}

	private void setSingleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdForTagging);
		command = null;
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}
	
	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_REMOVE_COMMAND);
	}
}
