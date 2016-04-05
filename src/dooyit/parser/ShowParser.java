//@@author A0133338J
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class ShowParser {
	private static final String ERROR_MESSAGE_INVALID_SHOW_COMMAND = "Error: Invalid show command";
	private static final String TODAY = "today";
	private static final String NEXT_SEVEN = "next7";
	private static final String DONE = "done";
	private static final String ALL = "all";
	private static final String DATE = "date";
	private static final String CATEGORY = "cat";
	private static final String COMPLETED = "completed";
	private static final String FLOAT = "float";
	private static final String OVERDUE = "overdue";
	
	private String userInput;
	private Command command;
	
	public ShowParser() {
	
	}

	public Command getCommand(String input) { 
		userInput = input.toLowerCase();
		command = null;
		switch (getShowType()) { 
		case TODAY:
			setShowTodayCommand();
			break;

		case NEXT_SEVEN:
			setShowNextSevenDaysCommand();
			break;

		case DONE:
			setShowDoneCommand();
			break;

		case ALL:
			setShowAllCommand();
			break;
			
		case FLOAT:
			setShowFloatCommand();
			break;

		case COMPLETED:
			setShowDoneCommand();
			break;
			
		case OVERDUE:
			//return CommandUtils.createShowOverdueCommand();

		// case DATE :
		// DateTimeParser dateTimeParser = new DateTimeParser();
		// DateTime date = dateTimeParser.parse(userInput);
		// return CommandUtils.createShowCommand(COMMAND_DATE, date);

		case CATEGORY:
			setShowCategoryCommand();
			break;

		default:
			setShowInvalidMessageCommand();
			break;
		}
		return command;
	}

	private String getShowType() {
		String type;
		if(!userInput.contains(CATEGORY)) {
			type = userInput;
		} else {
			type = CATEGORY;
		}
		return type;
	}

	private void setShowInvalidMessageCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_SHOW_COMMAND);
	}

	private void setShowCategoryCommand() {
		command = CommandUtils.createShowCategoryCommand(getCatName());
	}

	private void setShowFloatCommand() {
		command = CommandUtils.createShowFloatCommand();
	}

	private void setShowAllCommand() {
		command = CommandUtils.createShowAllCommand();
	}

	private void setShowDoneCommand() {
		command = CommandUtils.createShowCompletedCommand();
	}

	private void setShowNextSevenDaysCommand() {
		command = CommandUtils.createShowNext7DaysCommand();
	}

	private void setShowTodayCommand() {
		command = CommandUtils.createShowTodayCommand();
	}

	private String getCatName() {
		int index = userInput.indexOf(CATEGORY) + CATEGORY.length();
		String ans = userInput.substring(index).trim();
		return ans;
	}
}
