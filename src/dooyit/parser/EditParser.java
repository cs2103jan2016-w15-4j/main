package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
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
			try {
				parseDeadline();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditDeadlineCommand();
			break;

		case TIME_START_END:
			try {
				parseTimeStartEnd(); 
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditEventCommand();
			break;

		case TIME_START:
			try {
				parseTimeStart();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditEventCommand();
			break;

		case TIME_END:
			try {
				parseTimeEnd();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditEventCommand();
			break;

		case NAME_TIME_START_END:
			try {
				parseNameTimeStartEnd();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndEventCommand();
			break;

		case NAME_TIME_START:
			try {
				parseNameTimeStart();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndEventCommand();
			break;

		case NAME_TIME_END:
			try {
				parseNameTimeEnd();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndEventCommand();
			break;

		case NAME_DEADLINE:
			try {
				parseNameDeadline();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndDeadlineCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
		return command;
	}

	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
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

	private void setEditEventCommand() {
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
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE);
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName, indexDeadline);
	}

	private void parseNameTimeEnd() throws IncorrectInputException {
		parseNameForTimeEndType();
		try {
			parseTimeEnd();
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private void parseNameForTimeEndType() {
		int indexTo = getIndexOfMarker(MARKER_TIME_END);
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName, indexTo);
	}

	private void parseNameTimeStart() throws IncorrectInputException {
		parseNameForTimeStartType();
		try {
			parseTimeStart();
		} catch (IncorrectInputException e) {
			throw e;
		} 
	}

	private void parseNameForTimeStartType() {
		int indexFrom = getIndexOfMarker(MARKER_TIME_START);
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName, indexFrom);
	}

	private int getIndexOfName() {
		return userInput.indexOf(" ") + 1;
	}

	private void parseNameTimeStartEnd() throws IncorrectInputException {
		parseNameForTimeStartEndType();
		try {
			parseTimeStart();
			parseTimeEnd();
		} catch (IncorrectInputException e) {
			throw e;
		}	
	}

	private void parseNameForTimeStartEndType() {
		parseNameForTimeStartType();
	}

	private void parseTimeEnd() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexTo = getIndexOfMarker(MARKER_TIME_END);
		String timeString = getTimeString(indexTo, MARKER_TIME_END);
		try {
			end = dateTimeParser.parse(timeString);
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private void parseTimeStart() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = getIndexOfMarker(MARKER_TIME_START);
		int indexTo = getIndexOfMarker(MARKER_TIME_END);
		try {
			if (!isUninitialized(indexTo)) {
				String timeString = getTimeString(indexFrom, indexTo, MARKER_TIME_START);
				start = dateTimeParser.parse(timeString);
			} else { 
				String timeString = getTimeString(indexFrom, MARKER_TIME_START);
				start = dateTimeParser.parse(timeString);
			}
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private int getIndexOfMarker(String marker) {
		return userInput.lastIndexOf(marker);
	}

	private String getTimeString(int indexFrom, int indexTo, String marker) {
		return userInput.substring(indexFrom, indexTo).replace(marker, EMPTY_STRING).trim();
	}

	private void parseTimeStartEnd() throws IncorrectInputException {
		parseName();
		try {
			parseTimeEnd();
			parseTimeStart();
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private void parseDeadline() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE);
		String deadlineString = getTimeString(indexDeadline, MARKER_DEADLINE);
		try {
			deadline = dateTimeParser.parse(deadlineString);
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private String getTimeString(int index, String marker) {
		return userInput.substring(index).replace(marker, EMPTY_STRING).trim();
	}

	private void parseName() {
		int startOfName = getIndexOfName();
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
			} else if (!hasStart() && !hasEnd() && hasDeadline()) { 
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

	private boolean hasName() {
		String mayBeName = userInput.split("\\s+")[1];
		// This means that the names cannot be "by", "to" or "from"
		return !mayBeName.equals(MARKER_DEADLINE.trim()) && !mayBeName.equals(MARKER_TIME_END.trim())
				&& !mayBeName.equals(MARKER_TIME_START.trim());
	}

	private boolean hasStart() {
		return !isUninitialized(getIndexOfMarker(MARKER_TIME_START));
	}

	private boolean hasEnd() {
		return !isUninitialized(getIndexOfMarker(MARKER_TIME_END));
	}

	private boolean hasDeadline() {
		return !isUninitialized(getIndexOfMarker(MARKER_DEADLINE));
	}

}
