package dooyit.parser;

import dooyit.common.CommandUtils;
import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;

public class EditParser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_EDIT_COMMAND = "Invalid edit Command!";

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
		command = null;
		String taskIdString = userInput.split("\\s+")[0];
		if (ParserCommons.isNumber(taskIdString)) {
			taskId = Integer.parseInt(taskIdString);
		}

		switch (getEditType()) {
		case NAME :
			parseName();
			setEditNameCommand();
			break;

		case DEADLINE :
			try {
				parseDeadline();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditDeadlineCommand();
			break;

		case TIME_START_END :
			try {
				parseTimeStartEnd(); 
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditEventCommand();
			break;

		case NAME_TIME_START_END :
			try {
				parseNameTimeStartEndType();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndEventCommand();
			break;

		case NAME_DEADLINE :
			try {
				parseNameDeadline();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditNameAndDeadlineCommand();
			break;

		default :
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
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		int startOfName = getIndexOfName(); 
		taskName = userInput.substring(startOfName, indexDeadline);
	}

	private int getIndexOfName() {
		return userInput.indexOf(" ") + 1;
	}

	private void parseNameTimeStartEndType() throws IncorrectInputException {
		parseNameForTimeStartEnd();
		try {
			parseTimeStartEnd();
		} catch (IncorrectInputException e) {
			throw e;
		}
		
		if (end.compareTo(start) == -1) {
			end.setDate(start);
		}
		
		if (end.compareTo(start) == -1) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	} 

	private void parseNameForTimeStartEnd() {
		int startOfName = getIndexOfName();
		int indexFrom = getIndexOfMarker(MARKER_START_EVENT);
		int indexTo = getIndexOfMarker(MARKER_END_EVENT);
		if (indexFrom < indexTo) {
			taskName = userInput.substring(startOfName, indexFrom); 
		} else {
			taskName = userInput.substring(startOfName, indexTo);
		}
	}
	
	private int getIndexOfMarker(String marker) {
		return userInput.lastIndexOf(marker);
	}

	private void parseTimeStartEnd() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = getIndexOfMarker(MARKER_START_EVENT);
		int indexTo = getIndexOfMarker(MARKER_END_EVENT);
		String startTimeString = ParserCommons.getStartTimeString(userInput, indexFrom, indexTo);
		String endTimeString = ParserCommons.getEndTimeString(userInput, indexFrom, indexTo);
		try {
			start = dateTimeParser.parse(startTimeString);
			end = dateTimeParser.parse(endTimeString);
		} catch (IncorrectInputException e) {
			throw e;
		}
		
		if (end.compareTo(start) == -1) {
			end.setDate(start);
		}
		
		if (end.compareTo(start) == -1) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	}

	private void parseDeadline() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		String deadlineString = getTimeString(indexDeadline, MARKER_DEADLINE_TASK);
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
		EDIT_TYPE type;
		if(userInput.equals(EMPTY_STRING)) {
			type = EDIT_TYPE.INVALID;
		} else if(!hasTaskId(userInput)) {
			type = EDIT_TYPE.INVALID;
		} else if(hasName()) {
			if (!hasStart() && !hasEnd() && !hasDeadline()) {
				type = EDIT_TYPE.NAME;
			} else if (hasStart() && hasEnd() && !hasDeadline()) {
				type = EDIT_TYPE.NAME_TIME_START_END;
			} else if (hasStart() && !hasEnd() && !hasDeadline()) {
				type = EDIT_TYPE.NAME_TIME_START;
			} else if (!hasStart() && hasEnd() && !hasDeadline()) {
				type = EDIT_TYPE.NAME_TIME_END;
			} else if (!hasStart() && !hasEnd() && hasDeadline()) { 
				type = EDIT_TYPE.NAME_DEADLINE;
			} else {
				type = EDIT_TYPE.INVALID;
			}

		} else if(hasDeadline() && !hasStart() && !hasEnd()) {
			type = EDIT_TYPE.DEADLINE;

		} else if (hasStart() || hasEnd()) {
			if (hasStart() && hasEnd()) {
				type = EDIT_TYPE.TIME_START_END;
			} else if (hasStart() && !hasEnd()) {
				type = EDIT_TYPE.TIME_START;
			} else { // !hasStart() && hasEnd()
				type = EDIT_TYPE.TIME_END;
			}

		} else {
			type = EDIT_TYPE.INVALID;
		}
		return type;
	}

	private boolean hasTaskId(String userInput2) {
		String[] splittedInput = userInput.split("\\s+", 2);
		String taskId = splittedInput[0].toLowerCase();
		return ParserCommons.isNumber(taskId);
	}

	private boolean hasName() {
		boolean ans = false;
		String[] splitUserInput = userInput.split("\\s+");
		if(splitUserInput.length > 1) { 
			String mayBeName = userInput.split("\\s+")[1];
			// This means that the names cannot be "by", "to" or "from"
			ans = !mayBeName.equals(MARKER_DEADLINE_TASK.trim()) && !mayBeName.equals(MARKER_END_EVENT.trim())
				&& !mayBeName.equals(MARKER_START_EVENT.trim());
		} 
		return ans;
	}

	private boolean hasStart() {
		return !ParserCommons.isUninitialized(getIndexOfMarker(MARKER_START_EVENT));
	}

	private boolean hasEnd() {
		return !ParserCommons.isUninitialized(getIndexOfMarker(MARKER_END_EVENT));
	}

	private boolean hasDeadline() {
		return !ParserCommons.isUninitialized(getIndexOfMarker(MARKER_DEADLINE_TASK));
	}
}
