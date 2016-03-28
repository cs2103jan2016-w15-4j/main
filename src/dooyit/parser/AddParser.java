package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddParser implements ParserCommons {

	private static final String ERROR_MESSAGE_INVALID_ADD_COMMAND = "Error: Invalid add command!";
	private static final String MARKER_START_EVENT = " from ";
	private static final String MARKER_END_EVENT = " to ";
	private static final String MARKER_WORK = " by ";
	private static final String MARKER_CATEGORY = " @ ";

	private String userInput;
	private String taskName;
	private String categoryName;
	private int categoryId;
	private DateTime start;
	private DateTime end;
	private DateTime deadline;
	private Command cmd;

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

		return cmd;
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
		if (isNumber(category)) {
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
		cmd = CommandUtils.createInvalidCommand(message);
	}

	private void setToEventCommand() {
		cmd = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	private void setToWorkCommand() {
		cmd = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	private void setToFloatCommand() {
		cmd = CommandUtils.createAddCommandFloat(taskName);
	}

	private void parseEvent() throws IncorrectInputException {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT); // what if
																// indexTo <
																// indexFrom
		taskName = userInput.substring(0, indexFrom);
		try {
			start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, "").trim()));
			end = dateTimeParser.parse((userInput.substring(indexTo).replace(MARKER_END_EVENT, "").trim()));
		} catch(IncorrectInputException e) {
			throw e;
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
		if (isEvent()) {
			return TASK_TYPE.EVENT;
		} else if (isWork()) {
			return TASK_TYPE.WORK;
		} else if (isFloating()) {
			return TASK_TYPE.FLOATING;
		} else if (isCategoryAndEvent()) {
			return TASK_TYPE.CATEGORY_AND_EVENT;
		} else if (isCategoryAndWork()) {
			return TASK_TYPE.CATEGORY_AND_WORK;
		} else if (isCategoryAndFloating()) {
			return TASK_TYPE.CATEGORY_AND_FLOATING;
		} else {
			return TASK_TYPE.INVALID;
		}
	}

	private boolean isCategory() {
		return !isUninitialized(userInput.lastIndexOf(MARKER_CATEGORY));
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
		return !isUninitialized(userInput.lastIndexOf(MARKER_START_EVENT)) && 
				!isUninitialized(userInput.lastIndexOf(MARKER_END_EVENT));
	}

	private boolean isWork() {
		return !isUninitialized(userInput.lastIndexOf(MARKER_WORK));
	}
}