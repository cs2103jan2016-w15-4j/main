//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * The AddParser class takes in an "add" command input and returns a Command 
 * object. It is needed for adding floating tasks, deadline tasks and events.
 * It implements the ParserCommons interface to use the shared
 * constants and methods.
 * 
 * @author Annabel
 *
 */
public class AddParser extends AddEditTaskParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_ADD_COMMAND = "Invalid add command!";

	// Logger for AddParser
	private static Logger logger = Logger.getLogger("AddParser");

	// Types of Add command inputs
	private enum TaskType {
		FLOATING, WORK, EVENT, INVALID
	};

	/** Initializes a new AddParser object */
	public AddParser() {
		super();
		logger.log(Level.INFO, "Initialised AddParser object");
	}

	/**
	 * Parses userInput and returns the correct AddCommand object
	 * 
	 * @param input
	 *        The add command input from the user
	 * 
	 * @return the correct AddCommand object if the command input is
	 *         valid or an invalid command object if the command input is
	 *         invalid
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from AddParser");
		resetAttributes();
		setUserInput(input);

		// Sets the command attribute to the correct command object
		setToAddCommand();

		return command;
	}

	/**
	 * Sets the command attribute to the correct type of AddCommand object or to
	 * an InvalidCommand object
	 */
	private void setToAddCommand() {
		switch (getTaskType()) {
		case FLOATING:
			parseFloat();
			setFloatCommand();
			break;

		case WORK:
			parseDeadlineTask();
			setDeadlineTaskCommand();
			break;

		case EVENT:
			try {
				parseEvent();
			} catch (IncorrectInputException e){ 
				setInvalidCommand(e.getMessage());
				break;
			}
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
	 *        Error message that indicates why the user input is wrong.
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
	private void setDeadlineTaskCommand() {
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
	 * Sets the start and end DateTime objects and the taskName attributes
	 * to the correct values.
	 */
	private void parseEvent() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();

		// Gets the position indices of the event markers "from" and "to"
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);

		// Sets the taskName attribute by excluding the event markers and start
		// and end date time inputs
		taskName = getTaskName(indexFrom, indexTo);

		// Gets the start and end date time string inputs
		String startDateTimeString = getStartDateTimeString(indexFrom, indexTo);
		String endDateTimeString = getEndDateTimeString(indexFrom, indexTo);

		// Sets the start and end DateTime objects
		start = dateTimeParser.parse(startDateTimeString);
		end = dateTimeParser.parse(endDateTimeString);

		// Check if end DateTime object is before start DateTime object
		if (end.compareTo(start) == BEFORE) {
			// Set date of end DateTime object to the date of start DateTime
			// object. 
			end.setDate(start);
		}
		
		// Check if end DateTime object is before start DateTime object
		// Timing of end DateTime object may be before timing of start DateTime object.
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
		taskName = userInput.substring(START_OF_STRING, indexBy);
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
		return !userInput.equals(EMPTY_STRING) && !isEvent()
				&& !isDeadlineTask();
	}
}