package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.parser.TagParser.TAG_TYPE;

public class FloatParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_FLOAT_COMMAND = "Invalid float Command!";
	private Command command;

	public FloatParser() {
		super();
	}

	public Command getCommand(String input) throws IncorrectInputException {
		setVariables(input);
		command = null;
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			setInvalidCommand(e.getMessage());
		}
		
		if (command == null) {
			setCorrectFloatCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectFloatCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case VALID :
			setMultipleTypeFloatCommand();
			break;

		default :
			setInvalidCommand();
			break;
		}
	}
	
	private void setMultipleTypeFloatCommand() {
		//command = CommandUtils.createFloatCommand(taskIdsForTagging);
		command = CommandUtils.createEditCommandToFloat(taskIdsForTagging.get(0));
	}
	
	private void setInvalidCommand(String message) {
		//command = CommandUtils.createInvalidCommand(message);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_FLOAT_COMMAND);
	}
}
