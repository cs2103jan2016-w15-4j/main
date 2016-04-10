//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The AddParser class provides methods for adding floating tasks, deadline tasks and 
 * events. It takes in an "add" command input and returns an Add command object. 
 * It implements the ParserCommons interface to use the shared constants of MARKER_WORK,
 * MARKER_START_EVENT, MARKER_END_EVENT, UNINITIALIZED_INT and the ERROR_MESSAGE_END_BEFORE_START.
 * 
 * @author Annabel
 *
 */
public class AddParser implements ParserCommons {
	// Error Message
	private static final String ERROR_MESSAGE_INVALID_ADD_COMMAND = "Invalid add command!";
	
	// AddParser object attributes
	private String userInput;
	private String taskName;
	private DateTime start;
	private DateTime end;
	private DateTime deadline;
	private Command command;

	// Logger for AddParser
	private static Logger logger = Logger.getLogger("AddParser");
	
	// Attributes of an AddParser object
	private enum TASK_TYPE {
		FLOATING, WORK, EVENT, INVALID
	};

	/** Initializes a new AddParser object */
	public AddParser() {
		logger.log(Level.INFO, "Initialised AddParser object");
	}

	public Command getCommand(String input) throws IncorrectInputException {
		logger.log(Level.INFO, "Getting command object from Add Parser");
		userInput = input.trim();
		
		// Sets the command attribute to the correct command object
		setAddCommand();
		return command;
	}

	private void setAddCommand() {
		switch (getTaskType()) {
		case FLOATING:
			try {
				parseFloat();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setFloatCommand();
			break;

		case WORK:
			try {
				parseWork();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setWorkCommand();
			break;

		case EVENT:
			try {
				parseEvent();
			} catch (IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
				break;
			}
			setEventCommand();
			break;

		default:
			setInvalidCommand(ERROR_MESSAGE_INVALID_ADD_COMMAND);
		}
	}

	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setEventCommand() {
		command = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	private void setWorkCommand() {
		command = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	private void setFloatCommand() {
		command = CommandUtils.createAddCommandFloat(taskName);
	}

	private void parseEvent() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);
		taskName = ParserCommons.getTaskName(userInput, indexFrom, indexTo);
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
		setUninitializedTimeToDefault();
	}

	private void setUninitializedTimeToDefault() {
		if (start.getTimeInt() == UNINITIALIZED_INT) {
			start.setTimeToMidnight();
		}
		if (end.getTimeInt() == UNINITIALIZED_INT) {
			end.setTimeToRightBeforeMidnight();
		}
	}

	private void parseWork() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		taskName = userInput.substring(0, indexBy);
		try {
			deadline = dateTimeParser.parse((userInput.substring(indexBy).replace(MARKER_WORK, "").trim()));
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private void parseFloat() {
		taskName = userInput;
	}

	private TASK_TYPE getTaskType() {
		TASK_TYPE type;
		if (isEvent()) {
			type = TASK_TYPE.EVENT;
			
		} else if (isWork()) {
			type = TASK_TYPE.WORK;
			
		} else if (isFloating()) {
			type = TASK_TYPE.FLOATING;
			
		} else {
			type = TASK_TYPE.INVALID;
		}
		return type;
	}
	
	private boolean isFloating() {
		return !userInput.equals("") && !isEvent() && !isWork();
	}

	private boolean isEvent() {
		boolean ans = false;
		if (!ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_START_EVENT))
				&& !ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_END_EVENT))) {
			ans = hasAValidDateTimeAfterEventMarkers();
		}
		return ans;
	}

	private boolean hasAValidDateTimeAfterEventMarkers() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);
		boolean ans = true;
		String startTimeString = ParserCommons.getStartTimeString(userInput, indexFrom, indexTo);
		String endTimeString = ParserCommons.getEndTimeString(userInput, indexFrom, indexTo);

		try {
			dateTimeParser.parse(startTimeString);
			dateTimeParser.parse(endTimeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}

	private boolean isWork() {
		boolean ans = false;
		if (!ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_WORK))) {
			ans = hasAValidDateTimeAfterWorkMarker();
		}
		return ans;
	}

	private boolean hasAValidDateTimeAfterWorkMarker() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		String timeString = userInput.substring(indexBy).replace(MARKER_WORK, "").trim();
		boolean ans = true;
		try {
			dateTimeParser.parse(timeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}
}