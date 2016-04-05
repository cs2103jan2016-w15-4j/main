//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MarkParser extends TagParser{
	public static final String ERROR_MESSAGE_INVALID_MARK_COMMAND = "Error: Invalid mark Command!";
	private Command command;

	public MarkParser() {
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
			setCorrectMarkCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectMarkCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setSingleTypeMarkCommand();
			break;

		case MULTIPLE:
			setMultipleTypeMarkCommand();
			break;

		case INTERVAL:
			setIntervalTypeMarkCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setIntervalTypeMarkCommand() {
		command = CommandUtils.createMarkCommand(taskIdsForTagging);
	}

	private void setMultipleTypeMarkCommand() {
		command = CommandUtils.createMarkCommand(taskIdsForTagging);
	}

	private void setSingleTypeMarkCommand() {
		command = CommandUtils.createMarkCommand(taskIdForTagging);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MARK_COMMAND);
	}
}
