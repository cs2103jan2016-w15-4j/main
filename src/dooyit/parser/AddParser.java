package dooyit.parser;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddParser {

	private static final String MARKER_START_EVENT = " from ";
	private static final String MARKER_END_EVENT = " to ";
	private static final String MARKER_WORK = " by ";
	private static final String MARKER_CATEGORY = " @ ";

	private static String userInput;
	private static String taskName;
	private static String categoryName;
	private static int categoryId;
	private static DateTime start;
	private static DateTime end;
	private static DateTime deadline;
	private static Command cmd;

	enum TASK_TYPE {
		FLOATING, WORK, EVENT, INVALID, CATEGORY_AND_EVENT, CATEGORY_AND_WORK, CATEGORY_AND_FLOATING
	};

	public AddParser(String input) {
		userInput = input.trim();
	}

	public Command getCommand() throws IncorrectInputException {
		switch (getTaskType()) {
		case FLOATING:
			try {
				parseFloat();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToFloatCmd();
			break;

		case WORK:
			try {
				parseWork();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToWorkCmd();
			break;

		case EVENT:
			try {
				parseEvent();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setToEventCmd();
			break;

		case CATEGORY_AND_FLOATING:
			try {
				parseCategoryAndFloating();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setCategoryAndFloatingCmd();
			break;

		case CATEGORY_AND_WORK:
			try {
				parseCategoryAndWork();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
				break;
			}
			setCategoryAndWorkCmd();
			break;

		case CATEGORY_AND_EVENT:
			try {
				parseCategoryAndEvent();
			} catch (IncorrectInputException e) {
				setToInvalidCmd(e.getMessage());
			}
			setCategoryAndEventCmd();
			break;

		case INVALID:
			setToInvalidCmd("Invalid input!");
		}

		return cmd;
	}

	private void parseCategoryAndFloating() {
		parseCategory();
		parseWork();
	}

	private void parseCategoryAndWork() {
		parseCategory();
		parseEvent();
	}

	private void parseCategoryAndEvent() {
		parseCategory();
		parseEvent();
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

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}

	private void setCategoryAndEventCmd() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, start, end,
		// categoryId, categoryName);
	}

	private void setCategoryAndWorkCmd() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, deadline,
		// categoryId, categoryName);
	}

	private void setCategoryAndFloatingCmd() {
		// cmd = CommandUtils.createAddCommandCategory(taskName, categoryId,
		// categoryName);
	}

	private void setToInvalidCmd(String message) {
		cmd = CommandUtils.createInvalidCommand(message);
	}

	private void setToEventCmd() {
		cmd = CommandUtils.createAddCommandEvent(taskName, start, end);
	}

	private void setToWorkCmd() {
		cmd = CommandUtils.createAddCommandDeadline(taskName, deadline);
	}

	private void setToFloatCmd() {
		cmd = CommandUtils.createAddCommandFloat(taskName);
	}

	private void parseEvent() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT); // what if
																// indexTo <
																// indexFrom
		taskName = userInput.substring(0, indexFrom);
		start = dateTimeParser.parse((userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, "").trim()));
		end = dateTimeParser.parse((userInput.substring(indexTo).replace(MARKER_END_EVENT, "").trim()));
	}

	private void parseWork() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_WORK);
		taskName = userInput.substring(0, indexBy);
		deadline = dateTimeParser.parse((userInput.substring(indexBy).replace(MARKER_WORK, "").trim()));
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
		} else if (isCatAndEvent()) {
			return TASK_TYPE.CATEGORY_AND_EVENT;
		} else if (isCatAndWork()) {
			return TASK_TYPE.CATEGORY_AND_WORK;
		} else if (isCatAndFloating()) {
			return TASK_TYPE.CATEGORY_AND_FLOATING;
		} else {
			return TASK_TYPE.INVALID;
		}
	}

	private boolean isCat() {
		return userInput.lastIndexOf(MARKER_CATEGORY) != -1;
	}

	private boolean isCatAndFloating() {
		return isCat() && isFloating();
	}

	private boolean isCatAndWork() {
		return isCat() && isWork();
	}

	private boolean isCatAndEvent() {
		return isCatAndEvent();
	}

	private boolean isFloating() {
		return !userInput.equals("") && !isEvent() && !isWork();
	}

	private boolean isEvent() {
		return userInput.lastIndexOf(MARKER_START_EVENT) != -1 && userInput.lastIndexOf(MARKER_END_EVENT) != -1;
	}

	private boolean isWork() {
		return userInput.lastIndexOf(MARKER_WORK) != -1;
	}
}