package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class RemoveParser extends TagParser {
	private static Command command = null;

	public RemoveParser() {
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
			command = getSingleTypeRemoveCommand();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getMultipleTypeRemoveCommand();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getIntervalTypeRemoveCommand();
			break;

		case INVALID:
			command = getInvalidCmd();
			break;
		}
		return command;
	}

	private Command getIntervalTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		return null;
	}

	// Eg. delete 5 6 8
	private Command getMultipleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdsForTagging);
		return null;
	}

	private Command getSingleTypeRemoveCommand() {
		//return CommandUtils.createRemoveCommand(taskIdForTagging);
		return null;
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Remove Command!");
	}
}
