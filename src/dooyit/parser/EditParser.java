//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
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
	private static final String MARKER_FOR_NAME = " ";

	// Error message
	private static final String ERROR_MESSAGE_INVALID_EDIT_COMMAND = "Invalid edit Command!";

	// Index of task ID in user input string array
	private static final int INDEX_OF_TASK_ID = 0;

	private static final int INDEX_OF_INPUT_WITHOUT_TASK_ID = 1;

	private static final int INSUFFICIENT_LENGTH = 1;
	
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
	 * 
	 */
	private void setCommandAttribute() {
		if (command == null) {
			setToCorrectEditCommand();
		}
	}

	/**
	 * 
	 * @param input
	 */
	private void setAttributes(String input) {
		taskId = UNINITIALIZED_INT;

		setUserInput(input);

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
	 * 
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
			parseEventDateTime();
			setEditEventCommand();
			break;

		case NAME_TIME_START_END:
			parseNameAndEventType();
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
		return userInput.indexOf(MARKER_FOR_NAME) + 1;
	}

	/**
	 * 
	 */
	private void parseNameAndEventType() {
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
	 */
	private void parseEventDateTime() {
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
	 * 
	 * @throws IncorrectInputException
	 */
	private void parseDeadline() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexDeadline = getIndexOfMarker(MARKER_DEADLINE_TASK);
		String deadlineString = getTimeString(indexDeadline, MARKER_DEADLINE_TASK);
		deadline = dateTimeParser.parse(deadlineString);
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
		} else if (hasInsufficientWords()) {
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
		System.out.println("type is " + type);
		return type;
	}

	private boolean hasInsufficientWords() {
		String[] split = userInput.split("\\s+");
		return split.length == INSUFFICIENT_LENGTH;
	}

	/**
	 * 
	 * @param input
	 * 		  The user input
	 * 
	 * @return
	 */
	private boolean hasTaskId(String input) {
		String[] splittedInput = userInput.split("\\s+", SPLIT_INPUT_INTO_TWO_PARTS);
		String taskId = splittedInput[INDEX_OF_TASK_ID].toLowerCase();
		return ParserCommons.isNumber(taskId);
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasName() {
		boolean ans;
		String[] inputWithoutTaskIdArray = userInput.split(MARKER_FOR_NAME, SPLIT_INPUT_INTO_TWO_PARTS);
		String inputWithoutTaskId = inputWithoutTaskIdArray[INDEX_OF_INPUT_WITHOUT_TASK_ID];
		if (isDeadlineTask())  {
			int indexBy = inputWithoutTaskId.lastIndexOf(MARKER_DEADLINE_TASK.trim());
			ans = indexBy != START_OF_STRING;
			
		} else if (isEvent()) {
			int indexFrom = inputWithoutTaskId.lastIndexOf(MARKER_START_EVENT.trim());
			int indexTo = inputWithoutTaskId.lastIndexOf(MARKER_END_EVENT.trim());
			if (indexTo < indexFrom) {
				ans = indexTo != START_OF_STRING;
			} else {
				ans = indexFrom != START_OF_STRING;
			}
		} else {
			ans = !inputWithoutTaskId.equals(EMPTY_STRING);
		}
		return ans;
	}
}
