package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class EditParser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_EDIT_COMMAND = "Error: Invalid edit Command!";
	private static final String MARKER_TIME_START = " from ";
	private static final String MARKER_TIME_END = " to ";
	private static final String MARKER_DEADLINE = " by ";

	private static String userInput;
	private String taskName;
	private int taskId;
	private DateTime start;
	private DateTime end;
	private DateTime deadline;
	Command command;

	enum EDIT_TYPE {
		NAME, DEADLINE, TIME_START_END, TIME_START, TIME_END, NAME_TIME_START_END, NAME_TIME_START, NAME_TIME_END, NAME_DEADLINE, INVALID
	};

	public EditParser() {
		taskId = UNINITIALIZED_INT;
		start = null;
		end = null;
		deadline = null;
	}

	public Command getCommand(String input) { 
		userInput = input.trim();
		taskId = Integer.parseInt(userInput.split("\\s+")[0].trim());
		command = null;
		
		switch (getEditType()) {
		case NAME:
			parseName();
			setEditNameCommand();
			break;

		case DEADLINE:
			parseDeadline();
			setEditDeadlineCommand();
			break;

		case TIME_START_END:
			parseTimeStartEnd();
			setEditStartAndEventCommand();
			break;

		case TIME_START:
			parseTimeStart();
			setEditStartAndEventCommand();
			break;

		case TIME_END:
			parseTimeEnd();
			setEditStartAndEventCommand();
			break;

		case NAME_TIME_START_END:
			parseNameTimeStartEnd();
			setEditNameAndEventCommand();
			break;

		case NAME_TIME_START:
			parseNameTimeStart();
			setEditNameAndEventCommand();
			break;

		case NAME_TIME_END:
			parseNameTimeEnd();
			setEditNameAndEventCommand();
			break;

		case NAME_DEADLINE:
			parseNameDeadline();
			setEditNameAndDeadlineCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
		return command;
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_EDIT_COMMAND);
	}

	private void setEditNameAndDeadlineCommand() {
		command = CommandUtils.createEditCommandNameAndDeadline(taskId, taskName, deadline);
	}

	private void setEditNameAndEventCommand() {
		command = CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
	}

	private void setEditStartAndEventCommand() {
		command = CommandUtils.createEditCommandEvent(taskId, start, end);
	}

	private void setEditDeadlineCommand() {
		command = CommandUtils.createEditCommandDeadline(taskId, deadline);
	}

	private void setEditNameCommand() {
		command = CommandUtils.createEditCommandName(taskId, taskName);
	}

	private void parseNameDeadline() {
		parseNameForDeadlineType();
		parseDeadline();
	}

	private void parseNameForDeadlineType() {
		int indexDeadline = userInput.lastIndexOf(MARKER_DEADLINE);
		int startOfName = userInput.indexOf(" ") + 1;
		taskName = userInput.substring(startOfName, indexDeadline);
	}

	private void parseNameTimeEnd() {
		parseNameForTimeEndType();
		parseTimeEnd();
	}

	private void parseNameForTimeEndType() {
		int indexTo = userInput.lastIndexOf(MARKER_TIME_END);
		int startOfName = userInput.indexOf(" ") + 1;
		taskName = userInput.substring(startOfName, indexTo);
	}

	private void parseNameTimeStart() {
		parseNameForTimeStartType();
		parseTimeStart();
	}

	private void parseNameForTimeStartType() {
		int indexFrom = userInput.lastIndexOf(MARKER_TIME_START);
		int startOfName = userInput.indexOf(" ") + 1;
		taskName = userInput.substring(startOfName, indexFrom);
	}

	private void parseNameTimeStartEnd() {
		parseNameForTimeStartEndType();
		parseTimeStart();
		parseTimeEnd();
	}

	private void parseNameForTimeStartEndType() {
		parseNameForTimeStartType();
	}

	private void parseTimeEnd() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexTo = userInput.lastIndexOf(MARKER_TIME_END);
		end = dateTimeParser.parse(userInput.substring(indexTo).replace(MARKER_TIME_END, "").trim());
	}

	private void parseTimeStart() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_TIME_START);
		int indexTo = userInput.lastIndexOf(MARKER_TIME_END);
		if (indexTo != -1) {
			start = dateTimeParser.parse(userInput.substring(indexFrom, indexTo).replace(MARKER_TIME_START, "").trim());
		} else {
			start = dateTimeParser.parse(userInput.substring(indexFrom).replace(MARKER_TIME_START, "").trim());
		}
	}

	private void parseTimeStartEnd() {
		parseName();
		parseTimeEnd();
		parseTimeStart();
	}

	private void parseDeadline() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = userInput.lastIndexOf(MARKER_DEADLINE);
		deadline = dateTimeParser.parse(userInput.substring(indexDeadline).replace(MARKER_DEADLINE, "").trim());
	}

	private void parseName() {
		int startOfName = userInput.indexOf(" ") + 1;
		taskName = userInput.substring(startOfName);
	}

	private EDIT_TYPE getEditType() {
		if (hasName()) {
			if (!hasStart() && !hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME;
			} else if (hasStart() && hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_START_END;
			} else if (hasStart() && !hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_START;
			} else if (!hasStart() && hasEnd() && !hasDeadline()) {
				return EDIT_TYPE.NAME_TIME_END;
			} else if (!hasStart() && !hasEnd() && hasDeadline()) { // !hasStart()
																	// &&
																	// !hasEnd()
				return EDIT_TYPE.NAME_DEADLINE;
			}

		} else if (hasDeadline() && !hasStart() && !hasEnd()) {
			return EDIT_TYPE.DEADLINE;

		} else if (hasStart() || hasEnd()) {
			if (hasStart() && hasEnd()) {
				return EDIT_TYPE.TIME_START_END;
			} else if (hasStart() && !hasEnd()) {
				return EDIT_TYPE.TIME_START;
			} else { // !hasStart() && hasEnd()
				return EDIT_TYPE.TIME_END;
			}

		} else {
			// Invalid command
		}
		return null;
	}

	private static boolean hasName() {
		String mayBeName = userInput.split("\\s+")[1];
		// This means that the names cannot be "by", "to" or "from"
		return !mayBeName.equals(MARKER_DEADLINE.trim()) && !mayBeName.equals(MARKER_TIME_END.trim())
				&& !mayBeName.equals(MARKER_TIME_START.trim());
	}

	private static boolean hasStart() {
		return userInput.lastIndexOf(MARKER_TIME_START) != -1;
	}

	private static boolean hasEnd() {
		return userInput.lastIndexOf(MARKER_TIME_END) != -1;
	}

	private static boolean hasDeadline() {
		return userInput.lastIndexOf(MARKER_DEADLINE) != -1;
	}

}
