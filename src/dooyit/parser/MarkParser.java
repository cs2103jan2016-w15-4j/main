package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MarkParser extends TagParser{
	private Command command;

	public MarkParser() {
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
			setCorrectMarkCommand(getTagType());
		}
		
		return command;
	}

	private void setCorrectMarkCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			command = getSingleTypeMarkCommand();
			break;

		case MULTIPLE:
			command = getMultipleTypeMarkCommand();
			break;

		case INTERVAL:
			command = getIntervalTypeMarkCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getIntervalTypeMarkCommand() {
		return CommandUtils.createMarkCommand(taskIdsForTagging);
	}

	// Eg. mark 2 4 0 9
	private Command getMultipleTypeMarkCommand() {
		return CommandUtils.createMarkCommand(taskIdsForTagging);
	}

	private Command getSingleTypeMarkCommand() {
		return CommandUtils.createMarkCommand(taskIdForTagging);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Mark Command!");
	}
}
