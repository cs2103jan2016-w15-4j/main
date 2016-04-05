//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Error: Invalid Delete Command!";
	private Command command;

	public DeleteParser() {
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
			setCorrectDeleteCommand(getTagType());
		}
		
		return command;
	}

	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setCorrectDeleteCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setSingleTypeDeleteCommand();
			break;

		case MULTIPLE:
			setMultipleTypeDeleteCommand();
			break;

		case INTERVAL:
			setIntervalTypeDeleteCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setIntervalTypeDeleteCommand() {
		command = CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private void setMultipleTypeDeleteCommand() {
		command = CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private void setSingleTypeDeleteCommand() {
		command = CommandUtils.createDeleteCommand(taskIdForTagging);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_COMMAND);
	}
}
