//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * EditParser takes in "edit" commands and returns a Command object.
 * It is needed to edit the name, deadline and event timings of tasks.
 * 
 * @author Annabel
 *
 */
public class EditParser extends AddEditTaskParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_EDIT_COMMAND = "Invalid edit Command!";

	// Index of task ID in user input string array
	private static final int INDEX_OF_TASK_ID = 0;
	
	// Index of input without task ID in 2-element user input string array
	private static final int INDEX_OF_INPUT_WITHOUT_TASK_ID = 1;

	// Number of words in user input
	private static final int INSUFFICIENT_LENGTH = 1;
	
	// Marker for new task name
	private static final String MARKER_FOR_NAME = " ";
	
	// Logger for EditParser
	private static Logger logger = Logger.getLogger("EditParser");
	
	// EditParser object attributes
	private int taskId;

	/** Possible types of edits */
	private enum EditType {
		NAME, DEADLINE, TIME_START_END, NAME_TIME_START_END, NAME_DEADLINE, INVALID
	};

	/** Initializes a new EditParser object */
	public EditParser() {
		logger.log(Level.INFO, "Initialised EditParser object");
	}

	/**
	 * Parses user input and returns a command object.
	 * 
	 * @param input
	 * 		  The user input.
	 * 
	 * @return a Command object
	 */
	public Command getCommand(String input) {
		setAttributes(input);
		setCommandAttribute();
		return command;
	}

	/**
	 * Checks if the command attributes is uninitialized. If it is 
	 * uninitialized, then set the command attribute to the correct 
	 * EditCommand object.
	 */
	private void setCommandAttribute() {
		if (command == null) {
			setToCorrectEditCommand();
		}
	}

	/**
	 * Set the object attributes according to user input.
	 * Sets command to InvalidCommand if there is an Integer overflow.
	 * 
	 * @param input
	 * 		  The user input
	 */
	private void setAttributes(String input) {
		taskId = UNINITIALIZED_INT;
		setUserInput(input);

		// Check for Integer overflow
		String taskIdString = userInput.split("\\s+")[INDEX_OF_TASK_ID];
		if (ParserCommons.isNumber(taskIdString)) {
			try {
				taskId = Integer.parseInt(taskIdString);
			} catch (NumberFormatException e) {
				setInvalidCommand(ERROR_MESSAGE_INTEGER_OVERFLOW);
			}
		}
	}

	/**
	 * Checks which edit type the user input is, then parses the 
	 * input and sets the command attribute.
	 */
	private void setToCorrectEditCommand() {
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
			try {
				parseEventDateTime();
			} catch (IncorrectInputException e){ 
				setInvalidCommand(e.getMessage());
				break;
			}
			setEditEventCommand();
			break;

		case NAME_TIME_START_END:
			try { 
				parseNameAndEventType();
			} catch (IncorrectInputException e){ 
				setInvalidCommand(e.getMessage());
				break;
			}
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
	}

	/**
	 * Sets the command attribute to an InvalidComamnd object.
	 * @param message
	 */
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	/**
	 * Sets the command attribute to an InvalidCommand object.
	 */
	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_EDIT_COMMAND);
	}

	/**
	 * Sets the command attribute to an EditCommand object for editing a task
	 * name and deadline.
	 */
	private void setEditNameAndDeadlineCommand() {
		command = CommandUtils.createEditCommandNameAndDeadline(taskId, taskName, deadline);
	}

	/**
	 * Sets the command attribute to an EditCommand object for editing an event name and
	 * the event's start and end dates and timings.
	 */
	private void setEditNameAndEventCommand() {
		command = CommandUtils.createEditCommandNameAndEvent(taskId, taskName, start, end);
	}

	/**
	 * Sets the command attribute to an EditCommand object for editing an event start and 
	 * end dates and timings.
	 */
	private void setEditEventCommand() {
		command = CommandUtils.createEditCommandEvent(taskId, start, end);
	}

	/**
	 * Sets the command attribute to an EditCommand object for editing a task's deadline
	 * date and timings.
	 */
	private void setEditDeadlineCommand() {
		command = CommandUtils.createEditCommandDeadline(taskId, deadline);
	}

	/**
	 * Sets the command attribute to an EditCommand object for editing a task's name.
	 */
	private void setEditNameCommand() {
		command = CommandUtils.createEditCommandName(taskId, taskName);
	}

	/**
	 * Gets the new task name and new deadline from the user input
	 * and sets the object attributes accordingly.
	 */
	private void parseNameDeadline() {
		parseNameForDeadlineType();
		parseDeadline();
	}

	/**
	 * Gets the task name from the user input that edits both the
	 * name and deadline and sets the task name attribute.
	 */
	private void parseNameForDeadlineType() {
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName, indexDeadline);
	}

	/**
	 * Checks for the marker of the new name in the user input and
	 * returns the index of the name.
	 * 
	 * @return the position of the name in the user input.
	 */
	private int getIndexOfName() {
		return userInput.indexOf(MARKER_FOR_NAME) + 1;
	}

	/**
	 * Gets the new task name and new event timings from the user input
	 * and sets the object attributes accordingly.
	 */
	private void parseNameAndEventType() throws IncorrectInputException {
		parseNameForTimeStartEnd();
		parseEventDateTime();

		if (end.compareTo(start) == BEFORE) {
			end.setDate(start);
		}

		if (end.compareTo(start) == BEFORE) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	}

	/**
	 * Gets the task name from the user input that edits both the
	 * name and event timings and sets the task name attribute.
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
	 * Checks for the position of the marker like "by", "to" and 
	 * "from" in the user input and returns the index of the marker.
	 * 
	 * @param marker
	 * 		  String markers of deadline tasks or events
	 * 
	 * @return the index of the marker in the user input
	 */
	private int getIndexOfMarker(String marker) {
		return userInput.lastIndexOf(marker);
	}

	/**
	 * Parses the userInput on the assumption that it is an Event task.
	 * Sets the start and end DateTime objects attributes to the correct values.
	 */
	private void parseEventDateTime() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		
		int indexFrom = getIndexOfMarker(MARKER_START_EVENT);
		int indexTo = getIndexOfMarker(MARKER_END_EVENT);
		String startTimeString = getStartDateTimeString(indexFrom, indexTo);
		String endTimeString =getEndDateTimeString(indexFrom, indexTo);
		start = dateTimeParser.parse(startTimeString);
		end = dateTimeParser.parse(endTimeString);

		if (end.compareTo(start) == BEFORE) {
			end.setDate(start);
		}

		if (end.compareTo(start) == BEFORE) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
	}

	/**
	 * Parses the userInput on the assumption that it is a deadline task.
	 * Sets the deadline attribute from the userInput
	 */
	private void parseDeadline() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		String deadlineString = getDateTimeString(indexDeadline, MARKER_DEADLINE_TASK);
		deadline = dateTimeParser.parse(deadlineString);
	}

	/**
	 * Gets just the Date and Time string from the user input.
	 *  
	 * @param index
	 * 		  Position of the marker in the user input.
	 * 
	 * @param marker
	 * 		  Marker strings "by", "from" and "to".
	 * 
	 * @return the Date and Time string like "17/4 8pm"
	 */
	private String getDateTimeString(int index, String marker) {
		return userInput.substring(index).replace(marker, EMPTY_STRING).trim();
	}

	/**
	 * Sets the taskName attribute assuming that the user input edits name only.
	 */
	private void parseName() {
		int startOfName = getIndexOfName();
		taskName = userInput.substring(startOfName);
	}

	/**
	 * Checks the user input and returns the type that indicates which edits
	 * the user is trying to do such as editing name only or editing both the name
	 * and deadline of the task.
	 * 
	 * @return the EditType of the user input.
	 */
	private EditType getEditType() {
		EditType type;
		if (isInvalidType()) {
			type = EditType.INVALID;
		} else if (hasName()) {
			if (!isEvent() && !isDeadlineTask()) {
				type = EditType.NAME;
			} else if (isEvent() && !isDeadlineTask()) {
				type = EditType.NAME_TIME_START_END;
			} else if (!isEvent() && isDeadlineTask()) {
				type = EditType.NAME_DEADLINE;
			} else {
				type = EditType.INVALID;
			}

		} else if (isDeadlineTask() && !isEvent()) {
			type = EditType.DEADLINE;

		} else if (isEvent()) {
			type = EditType.TIME_START_END;

		} else {
			type = EditType.INVALID;
		}
		return type;
	}

	/**
	 * Checks if the user input falls into any of the more common invalid cases.
	 * 
	 * @return true if the user input is one of the common invalid cases and 
	 * 		   false otherwise.
	 */
	private boolean isInvalidType() {
		boolean isEmptyString = userInput.equals(EMPTY_STRING);
		boolean hasNoTaskId = !hasTaskId(userInput);
		boolean hasTooFewWords = hasInsufficientWords();
		return isEmptyString || hasNoTaskId || hasTooFewWords;
	}

	/**
	 * Checks if the the user input has too few words which indicates that 
	 * there are missing parameters.
	 * 
	 * @return true if the user input has too few words, and false otherwise.
	 */
	private boolean hasInsufficientWords() {
		String[] split = userInput.split("\\s+");
		return split.length == INSUFFICIENT_LENGTH;
	}

	/**
	 * Checks if the user input has a valid task ID.
	 * 
	 * @param input
	 * 		  The user input
	 * 
	 * @return true if a valid numeric task ID is present, and false otherwise.
	 */
	private boolean hasTaskId(String input) {
		String[] splittedInput = userInput.split("\\s+", SPLIT_INPUT_INTO_TWO_PARTS);
		String taskId = splittedInput[INDEX_OF_TASK_ID].toLowerCase();
		return ParserCommons.isNumber(taskId);
	}

	/**
	 * Checks if the user input has a name specified.
	 * 
	 * @return true if the user input has a name and false otherwise.
	 */
	private boolean hasName() {
		boolean ans;
		String[] inputWithoutTaskIdArray = userInput.split(MARKER_FOR_NAME, SPLIT_INPUT_INTO_TWO_PARTS);
		String inputWithoutTaskId = inputWithoutTaskIdArray[INDEX_OF_INPUT_WITHOUT_TASK_ID];
		
		if (isDeadlineTask())  {
			ans = checkForNameForDeadlineType(inputWithoutTaskId);
			
		} else if (isEvent()) {
			ans = checkForNameForEventType(inputWithoutTaskId);
		} else {
			ans = !inputWithoutTaskId.equals(EMPTY_STRING);
		}
		return ans;
	}

	/**
	 * Checks if the user input has a name. This is in the case where the user 
	 * input specifies a new deadline timing.
	 * 
	 * @param inputWithoutTaskId
	 * 		  This is the user input that has the task ID removed.
	 * 
	 * @return true if there is a name, and false otherwise.
	 */
	private boolean checkForNameForDeadlineType(String inputWithoutTaskId) {
		boolean ans;
		int indexBy = inputWithoutTaskId.lastIndexOf(MARKER_DEADLINE_TASK.trim());
		ans = indexBy != START_OF_STRING;
		return ans;
	}

	/**
	 * Checks if the user input has a name. This is in the case where the user
	 * input specifies new event timings.
	 * 
	 * @param inputWithoutTaskId
	 * 		  This is the user input that has the task ID removed. 
	 * 
	 * @return true if there is a name, and false otherwise.
	 */
	private boolean checkForNameForEventType(String inputWithoutTaskId) {
		boolean ans;
		int indexFrom = inputWithoutTaskId.lastIndexOf(MARKER_START_EVENT.trim());
		int indexTo = inputWithoutTaskId.lastIndexOf(MARKER_END_EVENT.trim());
		if (indexTo < indexFrom) {
			ans = indexTo != START_OF_STRING;
		} else {
			ans = indexFrom != START_OF_STRING;
		}
		return ans;
	}
}
