package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

public class UnmoveParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_UNMOVE_COMMAND = "Invalid Unmove Command!";
	private Command command;

	public UnmoveParser() {
		super();
	}

	public Command getCommand(String input) throws IncorrectInputException {
		setAttributesForTagging(input);
		command = null;
		
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			setInvalidCommand(e.getMessage());
		}
		
		if (command == null) {
			setUnmarkCommand(getTagType());
		}
		
		return command;
	}

	private void setUnmarkCommand(TagType tagType) {
		switch (tagType) {
		case VALID :
			setMultipleTypeUnmoveCommand();
			break;

		default :
			setInvalidCommand();
			break;
		}
	}
	
	private void setMultipleTypeUnmoveCommand() {
		int firstId = taskIdsForTagging.get(0);
		command = CommandUtils.createUnMoveCategoryCommand(firstId);
	}
	
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_UNMOVE_COMMAND);
	}
}