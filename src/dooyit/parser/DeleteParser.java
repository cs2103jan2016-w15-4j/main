package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Invalid Delete Command!";
	private Command command;

	public DeleteParser() {
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
			setCorrectDeleteCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectDeleteCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			command = getSingleTypeDeleteCommand();
			break;

		case MULTIPLE:
			command = getMultipleTypeDeleteCommand();
			break;

		case INTERVAL:
			command = getIntervalTypeDeleteCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getIntervalTypeDeleteCommand() {
		return CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private Command getMultipleTypeDeleteCommand() {
		return CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private Command getSingleTypeDeleteCommand() {
		return CommandUtils.createDeleteCommand(taskIdForTagging);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_COMMAND);
	}
}
