package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class UnmarkParser extends TagParser{
	private static Command command = null;

	public UnmarkParser() {
		super();
		command = null;
	}

	public Command getCommand(String input) throws IncorrectInputException {
		setVariables(input);
		switch (getTagType()) {
		case SINGLE:
			try {
				parseSingleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getSingleTypeUnmarkCmd();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getMultipleTypeUnmarkCmd();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getIntervalTypeUnmarkCmd();
			break;

		case INVALID:
			command = getInvalidCmd();
			break;
		}
		return command;
	}

	private Command getIntervalTypeUnmarkCmd() {
		return null;
		// return CommandUtils.createUnmarkCommand(taskIdsForTagging);
	}
	
	// Eg. unmark 2 4 0 9
	private Command getMultipleTypeUnmarkCmd() {
		return null;
		// return CommandUtils.createUnmarkCommand(taskIdsForTagging);
	}

	private Command getSingleTypeUnmarkCmd() {
		return null;
		// return CommandUtils.createUnmarkCommand(taskIdForTagging);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Unmark Command!");
	}
}
