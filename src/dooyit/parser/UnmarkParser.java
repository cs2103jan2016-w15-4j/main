package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class UnmarkParser extends TagParser{
	private static Command cmd = null;

	public UnmarkParser(String input) {
		super(input);
		cmd = null;
	}

	public Command getCommand() throws IncorrectInputException {
		switch (getTagType()) {
		case SINGLE:
			try {
				parseSingleType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getSingleTypeUnmarkCmd();
			break;

		case MULTIPLE:
			try {
				parseMultipleType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getMultipleTypeUnmarkCmd();
			break;

		case INTERVAL:
			try {
				parseIntervalType();
			} catch (IncorrectInputException e) {
				cmd = getInvalidCommand(e.getMessage());
				break;
			}
			cmd = getIntervalTypeUnmarkCmd();
			break;

		case INVALID:
			cmd = getInvalidCmd();
			break;
		}
		return cmd;
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
