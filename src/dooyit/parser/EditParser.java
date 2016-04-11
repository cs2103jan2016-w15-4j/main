//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * 
 * @author Annabel
 *
 */
public class EditParser implements ParserCommons {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_EDIT_COMMAND = "Invalid edit Command!";

	// Index constant of task ID
	private static final int INDEX_OF_TASK_ID = 0;
	
	// Logger for EditParser
	private static Logger logger = Logger.getLogger("EditParser");
	
	// EditParser object attributes
	private String userInput;
	private String taskName;
	private int taskId;
	private DateTime start;
	private DateTime end;
	private DateTime deadline;
	Command command;

	/** Possible types of edits */
	private enum EditType {
		NAME, DEADLINE, TIME_START_END, TIME_START, TIME_END, NAME_TIME_START_END, 
		NAME_TIME_START, NAME_TIME_END, NAME_DEADLINE, INVALID
	};

	/** */
	public EditParser() {
		taskId = UNINITIALIZED_INT;
		start = null;
		end = null;
		deadline = null;
		logger.log(Level.INFO, "Initialised EditParser object");
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public Command getCommand(String input) {
		userInput = input.trim();
		command = null;
		String taskIdString = userInput.split("\\s+")[INDEX_OF_TASK_ID];
		if (ParserCommons.isNumber(taskIdString)) {
			try {
				taskId = Integer.parseInt(taskIdString);
			} catch (NumberFormatException e) {
				setInvalidCommand(ERROR_MESSAGE_INTEGER_OVERFLOW);
			}
		}

		if (command == null) {
			setToCorrectEditCommand();
		}

		return command;
	}

	/**
	 * 
	 */
	private void setToCorrectEditCommand() {
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

		case NAME_TIME_START_END:
			try {
				parseNameTimeStartEndType();
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
	}

	/**
	 * 
	 * @param message
	 */
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	/**
	 * 
	 */
	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_EDIT_COMMAND);
	}

	/**
	 * 
	 */
	private void setEditNameAndDeadlineCommand() {
		command = CommandUtils.createEditCommandNameAndDeadline(taskId, taskName, deadline);
	}

	/**
	 * 
	 */
	private void setEditNameAndEventCommand() {
		command = CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
	}

	/**
	 * 
	 */
	private void setEditEventCommand() {
		command = CommandUtils.createEditCommandEvent(taskId, start, end);
	}

	/**
	 * 
	 */
	private void setEditDeadlineCommand() {
		command = CommandUtils.createEditCommandDeadline(taskId, deadline);
	}

	/**
	 * 
	 */
	private void setEditNameCommand() {
		command = CommandUtils.createEditCommandName(taskId, taskName);
	}

	/**
	 * 
	 */
	private void parseNameDeadline() {
		parseNameForDeadlineType();
		parseDeadline();
	}

	/**
	 * 
	 */
	private void parseNameForDeadlineType() {
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName, indexDeadline);
	}

	/**
	 * 
	 * @return
	 */
	private int getIndexOfName() {
		return userInput.indexOf(" ") + 1;
	}

	/**
	 * 
	 * @throws IncorrectInputException
	 */
	private void parseNameTimeStartEndType() throws IncorrectInputException {
		parseNameForTimeStartEnd();
		try {
			parseTimeStartEnd();
		} catch (IncorrectInputException e) {
			throw e;
		}

		if (end.compareTo(start) == BEFORE) {
			end.setDate(start);
		}

		if (end.compareTo(start) == BEFORE) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	}

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param marker
	 * @return
	 */
	private int getIndexOfMarker(String marker) {
		return userInput.lastIndexOf(marker);
	}

	/**
	 * 
	 * @throws IncorrectInputException
	 */
	private void parseTimeStartEnd() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = getIndexOfMarker(MARKER_START_EVENT);
		int indexTo = getIndexOfMarker(MARKER_END_EVENT);
		String startTimeString = ParserCommons.getStartDateTimeString(userInput, indexFrom, indexTo);
		String endTimeString = ParserCommons.getEndDateTimeString(userInput, indexFrom, indexTo);
		try {
			start = dateTimeParser.parse(startTimeString);
			end = dateTimeParser.parse(endTimeString);
		} catch (IncorrectInputException e) {
			throw e;
		}

		if (end.compareTo(start) == BEFORE) {
			end.setDate(start);
		}

		if (end.compareTo(start) == BEFORE) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	}

	/**
	 * 
	 * @throws IncorrectInputException
	 */
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

	/**
	 * 
	 * @param index
	 * @param marker
	 * @return
	 */
	private String getTimeString(int index, String marker) {
		return userInput.substring(index).replace(marker, EMPTY_STRING).trim();
	}

	/**
	 * 
	 */
	private void parseName() {
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName);
	}

	/**
	 * 
	 * @return
	 */
	private EditType getEditType() {
		EditType type;
		if (userInput.equals(EMPTY_STRING)) {
			type = EditType.INVALID;
		} else if (!hasTaskId(userInput)) {
			type = EditType.INVALID;
		} else if (hasName()) {
			if (!hasStart() && !hasEnd() && !hasDeadline()) {
				type = EditType.NAME;
			} else if (hasStart() && hasEnd() && !hasDeadline()) {
				type = EditType.NAME_TIME_START_END;
			} else if (hasStart() && !hasEnd() && !hasDeadline()) {
				type = EditType.NAME_TIME_START;
			} else if (!hasStart() && hasEnd() && !hasDeadline()) {
				type = EditType.NAME_TIME_END;
			} else if (!hasStart() && !hasEnd() && hasDeadline()) {
				type = EditType.NAME_DEADLINE;
			} else {
				type = EditType.INVALID;
			}

		} else if (hasDeadline() && !hasStart() && !hasEnd()) {
			type = EditType.DEADLINE;

		} else if (hasStart() || hasEnd()) {
			if (hasStart() && hasEnd()) {
				type = EditType.TIME_START_END;
			} else if (hasStart() && !hasEnd()) {
				type = EditType.TIME_START;
			} else { // !hasStart() && hasEnd()
				type = EditType.TIME_END;
			}

		} else {
			type = EditType.INVALID;
		}
		return type;
	}

	/**
	 * 
	 * @param userInput2
	 * @return
	 */
	private boolean hasTaskId(String userInput2) {
		String[] splittedInput = userInput.split("\\s+", 2);
		String taskId = splittedInput[0].toLowerCase();
		return ParserCommons.isNumber(taskId);
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasName() {
		boolean ans = false;
		String[] splitUserInput = userInput.split("\\s+");
		if (splitUserInput.length > 1) {
			String mayBeName = userInput.split("\\s+")[1];
			// This means that the names cannot be "by", "to" or "from"
			ans = !mayBeName.equals(MARKER_DEADLINE_TASK.trim()) && !mayBeName.equals(MARKER_END_EVENT.trim())
					&& !mayBeName.equals(MARKER_START_EVENT.trim());
		}
		return ans;
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasStart() {
		return ParserCommons.isInitialized(getIndexOfMarker(MARKER_START_EVENT));
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasEnd() {
		return ParserCommons.isInitialized(getIndexOfMarker(MARKER_END_EVENT));
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasDeadline() {
		return ParserCommons.isInitialized(getIndexOfMarker(MARKER_DEADLINE_TASK));
	}
}
