package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class DeleteParser extends TagParser {
	private static Command command = null;

	public DeleteParser() {
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
			command = getSingleTypeDeleteCmd();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getMultipleTypeDeleteCmd();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				command = getInvalidCommand(e.getMessage());
				break;
			}
			command = getIntervalTypeDeleteCmd();
			break;

		case INVALID:
			command = getInvalidCmd();
			break;
		}
		return command;
	}

	private Command getIntervalTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	// Eg. delete 5 6 8
	private Command getMultipleTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private Command getSingleTypeDeleteCmd() {
		return CommandUtils.createDeleteCommand(taskIdForTagging);
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Delete Command!");
	}
}
