package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class UnmarkParser extends TagParser{
	private static Command command;

	public UnmarkParser() {
		super();
		
	}

	public Command getCommand(String input) throws IncorrectInputException {
		setVariables(input);
		command = null;
		
		try {
			parseTaskIds();
		} catch(IncorrectInputException e) {
			command = getInvalidCommand(e.getMessage());
		}
		
		if(command == null) {
			setUnmarkCommand(getTagType());
		}
		
		return command;
	}

	private void setUnmarkCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			command = getSingleTypeUnmarkCommand();
			break;

		case MULTIPLE:
			command = getMultipleTypeUnmarkCommand();
			break;

		case INTERVAL:
			command = getIntervalTypeUnmarkCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getIntervalTypeUnmarkCommand() {
		return CommandUtils.createUnMarkCommand(taskIdsForTagging);
	}
	
	// Eg. unmark 2 4 0 9
	private Command getMultipleTypeUnmarkCommand() {
		return CommandUtils.createUnMarkCommand(taskIdsForTagging);
	}

	private Command getSingleTypeUnmarkCommand() {
		return CommandUtils.createUnMarkCommand(taskIdForTagging);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Unmark Command!");
	}
}
