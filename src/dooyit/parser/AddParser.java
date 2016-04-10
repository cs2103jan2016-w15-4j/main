//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The AddParser class provides methods for adding floating tasks, deadline
 * tasks and events. It takes in an "add" command input and returns an AddCommand 
 * object. It implements the ParserCommons interface to use the shared
 * constants and methods.
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
	private enum TaskType {
		FLOATING, WORK, EVENT, INVALID
	};

	/** Initializes a new AddParser object */
	public AddParser() {
		logger.log(Level.INFO, "Initialised AddParser object");
	}

	/**
	 * Parses userInput and returns correct AddCommand object
	 * 
	 * @param input
	 *        The add command input from the user
	 * 
	 * @return the correct AddCommand object if the command input is
	 *         valid or an invalid command object if the command input is
	 *         invalid
	 */
	public Command getCommand(String input) throws IncorrectInputException {
		logger.log(Level.INFO, "Getting command object from AddParser");
		userInput = input.trim();

		// Sets the command attribute to the correct command object
		setAddCommand();

		return command;
	}

	/**
	 * Sets the command attribute to the correct type of AddCommand object or to
	 * an InvalidCommand object
	 */
	private void setAddCommand() {
		switch (getTaskType()) {
		case FLOATING:
			parseFloat();
			setFloatCommand();
			break;

		case WORK:
			parseDeadlineTask();
			setWorkCommand();
			break;

		case EVENT:
			parseEvent();
			setEventCommand();
			break;

		default:
			setInvalidCommand(ERROR_MESSAGE_INVALID_ADD_COMMAND);
		}
	}

	/**
	 * Sets the command attribute to an InvalidCommand object
	 * 
	 * @param message
	 *            Error message that indicates why the user input is wrong
	 */
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	/**
	 * Sets the command attribute to an AddCommand object for events
	 */
	private void setEventCommand() {
		command = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	/**
	 * Sets the command attribute to an AddCommand object for deadline tasks
	 */
	private void setWorkCommand() {
		command = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	/**
	 * Sets the command attribute to an AddComamnd object for floating tasks
	 */
	private void setFloatCommand() {
		command = CommandUtils.createAddCommandFloat(taskName);
	}

	/**
	 * Parses the userInput on the assumption that it is an Event task.
	 * Sets the start and end DateTime objects and the taskName object
	 * to the correct values.
	 */
	private void parseEvent() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();

		// Gets the position indices of the event markers "from" and "to"
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);

		// Sets the taskName attribute by excluding the event markers and start
		// and end date time inputs
		taskName = ParserCommons.getTaskName(userInput, indexFrom, indexTo);

		// Gets the start and end date time string inputs
		String startDateTimeString = ParserCommons.getStartTimeString(userInput, indexFrom, indexTo);
		String endDateTimeString = ParserCommons.getEndTimeString(userInput, indexFrom, indexTo);

		// Sets the start and end DateTime objects
		start = dateTimeParser.parse(startDateTimeString);
		end = dateTimeParser.parse(endDateTimeString);

		// Check if end DateTime object is before start DateTime object
		if (end.compareTo(start) == BEFORE) {
			// Set date of end DateTime object to the date of start DateTime
			// object. Note that timing of end DateTime object may still be 
			// before timing of start DateTime object.
			end.setDate(start);
		}
		
		// Check if end DateTime object is before start DateTime object
		if (end.compareTo(start) == BEFORE) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
		setUninitializedTimeToDefault();
	}
	
	/**
	 * Sets the uninitialized timings of an event to the default timings of
	 * 00:00 for the start timing and 23:59 for end timing.
	 */
	private void setUninitializedTimeToDefault() {
		if (start.getTimeInt() == UNINITIALIZED_INT) {
			start.setTimeToMidnight();
		}
		if (end.getTimeInt() == UNINITIALIZED_INT) {
			end.setTimeToRightBeforeMidnight();
		}
	}

	/**
	 * Parses the userInput on the assumption that it is a deadline task.
	 * Sets the deadline and taskName attributes from the userInput
	 */
	private void parseDeadlineTask() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_DEADLINE_TASK);
		taskName = userInput.substring(0, indexBy);
		String deadlineString = userInput.substring(indexBy).replace(MARKER_DEADLINE_TASK, EMPTY_STRING).trim();
		deadline = dateTimeParser.parse(deadlineString);
	}

	/**
	 * Parses userInput on the assumption that it is a floating task
	 */
	private void parseFloat() {
		taskName = userInput;
	}

	/**
	 * Checks if the userInput is a addCommand input for a floating task,
	 * deadline task or an event task.
	 * 
	 * @return the correct TaskType enum constant.
	 */
	private TaskType getTaskType() {
		TaskType type;
		if (isEvent()) {
			type = TaskType.EVENT;

		} else if (isDeadlineTask()) {
			type = TaskType.WORK;

		} else if (isFloating()) {
			type = TaskType.FLOATING;

		} else {
			type = TaskType.INVALID;
		}
		return type;
	}
	
	/**
	 * Checks if the userInput is a floating task
	 * 
	 * @return true if input is a floating task and false if it isn't.
	 */
	private boolean isFloating() {
		return !userInput.equals(EMPTY_STRING) && !isEvent() && !isDeadlineTask();
	}

	/**
	 * Checks if the userInput is an event task by checking if input has 
	 * BOTH the event markers "from" and "to" and checking if the words 
	 * after the respective markers are valid DateTime inputs.
	 * 
	 * @return true if input is an event task and false if it isn't
	 */
	private boolean isEvent() {
		boolean ans = false;
		if (ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_START_EVENT))
				&& ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_END_EVENT))) {
			ans = hasAValidDateTimeAfterEventMarkers();
		}
		return ans;
	}

	/**
	 * Checks if the string inputs after the event markers "from" and "to" are valid
	 * DateTime inputs.
	 * 
	 * @return true if both strings are valid DateTime inputs and false otherwise.
	 */
	private boolean hasAValidDateTimeAfterEventMarkers() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		
		// Get the position indices of the event markers
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);
		
		// Get the start and end strings inputs 
		boolean ans = true;
		String startDateTimeString = ParserCommons.getStartTimeString(userInput, indexFrom, indexTo);
		String endDateTimeString = ParserCommons.getEndTimeString(userInput, indexFrom, indexTo);

		// Check if the start and end strings are valid DateTime inputs
		try {
			dateTimeParser.parse(startDateTimeString);
			dateTimeParser.parse(endDateTimeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}

	/**
	 * Checks if the userInput is a deadline task by checking if the input
	 * has the deadline marker "by" and checking if the words after the marker
	 * "by" are valid DateTime inputs.
	 * 
	 * @return true if the input is a deadline task and false otherwise.
	 */
	private boolean isDeadlineTask() {
		boolean ans = false;
		
		// Checks if the deadline input is a valid DateTime input
		if (ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_DEADLINE_TASK))) {
			ans = hasAValidDateTimeAfterWorkMarker();
		}
		return ans;
	}

	/**
	 * Checks if the string input after the Deadline marker "by" is a valid
	 * DateTime input.
	 * 
	 * @return true if the string is a valid DateTime input and false otherwise.
	 */
	private boolean hasAValidDateTimeAfterWorkMarker() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_DEADLINE_TASK);
		String timeString = userInput.substring(indexBy).replace(MARKER_DEADLINE_TASK, EMPTY_STRING).trim();
		boolean ans = true;
		
		// Check if timeString is a valid DateTime input
		try {
			dateTimeParser.parse(timeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}
}