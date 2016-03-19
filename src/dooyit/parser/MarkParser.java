package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class MarkParser extends TagParser{
	private static Command command = null;

	public MarkParser() {
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
			command = getSingleTypeMarkCommand();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getMultipleTypeMarkCommand();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getIntervalTypeMarkCommand();
			break;

		default:
			command = getInvalidCmd();
			break;
		}
		return command;
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
