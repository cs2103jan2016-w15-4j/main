package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddParser implements ParserCommons {

	private static final String ERROR_MESSAGE_END_BEFORE_START = "Error: End timing cannot be before Start timing";
	private static final String ERROR_MESSAGE_INVALID_ADD_COMMAND = "Error: Invalid add command!";
	private static final String MARKER_CATEGORY = " @ ";

	private String userInput;
	private String taskName;
	private String categoryName;
	private int categoryId;
	private DateTime start;
	private DateTime end;
	private DateTime deadline;
	private Command command; 

	enum TASK_TYPE {
		FLOATING, WORK, EVENT, INVALID, CATEGORY_AND_EVENT, CATEGORY_AND_WORK, CATEGORY_AND_FLOATING
	};

	public AddParser() {
		
	}

	public Command getCommand(String input) throws IncorrectInputException {
		userInput = input.trim();
		switch (getTaskType()) { 
		case FLOATING:
			try {
				parseFloat();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
				break;
			}
			setToFloatCommand();
			break;

		case WORK:
			try {
				parseWork();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
				break;
			}
			setToWorkCommand();
			break;

		case EVENT:
			try {
				parseEvent();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
				break;
			}
			setToEventCommand();
			break;

		case CATEGORY_AND_FLOATING:
			try {
				parseCategoryAndFloating();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
				break;
			}
			setCategoryAndFloatingCommand();
			break;

		case CATEGORY_AND_WORK:
			try {
				parseCategoryAndWork();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
				break;
			}
			setCategoryAndWorkCommand();
			break;

		case CATEGORY_AND_EVENT:
			try {
				parseCategoryAndEvent();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage());
			}
			setCategoryAndEventCommand();
			break;

		default:
			setToInvalidCommand(ERROR_MESSAGE_INVALID_ADD_COMMAND);
		}

		return command;
	}

	private void parseCategoryAndFloating() throws IncorrectInputException {
		parseCategory();
		try {
			parseWork();
		} catch(IncorrectInputException e) {
			throw e;
		}
	}

	private void parseCategoryAndWork() throws IncorrectInputException {
		parseCategory();
		try {
			parseEvent();
		} catch(IncorrectInputException e) {
			throw e;
		}
	}

	private void parseCategoryAndEvent() throws IncorrectInputException {
		parseCategory();
		try {
			parseEvent();
		} catch (IncorrectInputException e) {
			throw e;
		}
	}

	private void parseCategory() {
		int indexCategory = userInput.lastIndexOf(MARKER_CATEGORY);
		String category = userInput.substring(indexCategory).replace(MARKER_CATEGORY, "").trim();
		if (ParserCommons.isNumber(category)) {
			categoryId = Integer.parseInt(category);
		} else {
			categoryName = category;
		} 
	}

	private void setCategoryAndEventCommand() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, start, end,
		// categoryId, categoryName);
	}

	private void setCategoryAndWorkCommand() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, deadline,
		// categoryId, categoryName);
	}

	private void setCategoryAndFloatingCommand() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, categoryId,
		// categoryName);
	}

	private void setToInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setToEventCommand() {
		command = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	private void setToWorkCommand() {
		command = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	private void setToFloatCommand() {
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
		} catch(IncorrectInputException e) {
			throw e;
		}
		if(end.compareTo(start) == -1) {
			end.setDate(start);
		}
		if(end.compareTo(start) == -1) {
			throw new IncorrectInputException(ERROR_MESSAGE_END_BEFORE_START);
		}
		setUninitializedTimeToDefault();
	}

	private void setUninitializedTimeToDefault() {
		if(start.getTimeInt() == UNINITIALIZED_INT) {
			start.setTimeToMidnight();
		}
		if(end.getTimeInt() == UNINITIALIZED_INT) {
			end.setTimeToRightBeforeMidnight();
		}
	}

	private void parseWork() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		taskName = userInput.substring(0, indexBy);
		try {
			deadline = dateTimeParser.parse((userInput.substring(indexBy).replace(MARKER_WORK, "").trim()));
		} catch(IncorrectInputException e) {
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
		} else if (isCategoryAndEvent()) {
			type = TASK_TYPE.CATEGORY_AND_EVENT;
		} else if (isCategoryAndWork()) {
			type = TASK_TYPE.CATEGORY_AND_WORK;
		} else if (isCategoryAndFloating()) {
			type = TASK_TYPE.CATEGORY_AND_FLOATING;
		} else {
			type = TASK_TYPE.INVALID;
		}
		return type;
	}

	private boolean isCategory() {
		return !ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_CATEGORY));
	}

	private boolean isCategoryAndFloating() {
		return isCategory() && isFloating();
	}

	private boolean isCategoryAndWork() {
		return isCategory() && isWork();
	}

	private boolean isCategoryAndEvent() {
		return isCategory() && isEvent();
	}

	private boolean isFloating() {
		return !userInput.equals("") && !isEvent() && !isWork();
	}

	private boolean isEvent() {
		boolean ans = false;
		if(!ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_START_EVENT)) && 
				!ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_END_EVENT))) {
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
		if(!ParserCommons.isUninitialized(userInput.lastIndexOf(MARKER_WORK))) {
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