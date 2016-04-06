//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class UnmarkParser extends TagParser{
	private static final String ERROR_MESSAGE_INVALID_UNMARK_COMMAND = "Invalid Unmark Command!";
	private Command command;

	public UnmarkParser() {
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
			setUnmarkCommand(getTagType());
		}
		
		return command;
	}

	private void setUnmarkCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setSingleTypeUnmarkCommand();
			break;

		case MULTIPLE:
			setMultipleTypeUnmarkCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}
	
	// Eg. unmark 2 4 0 9
	private void setMultipleTypeUnmarkCommand() {
		command = CommandUtils.createUnMarkCommand(taskIdsForTagging);
	}

	private void setSingleTypeUnmarkCommand() {
		command = CommandUtils.createUnMarkCommand(taskIdForTagging);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_UNMARK_COMMAND);
	}
}
