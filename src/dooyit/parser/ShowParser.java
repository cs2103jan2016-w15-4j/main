package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class ShowParser {
	private static String userInput;
	private static final String TODAY = "today";
	private static final String NEXT_SEVEN = "next7";
	private static final String DONE = "done";
	private static final String ALL = "all";
	private static final String DATE = "date";
	private static final String CATEGORY = "category";
	private static final String COMPLETED = "completed";
	private static final String FLOAT = "float";

	public ShowParser() {
	
	}

	public Command getCommand(String input) {
		userInput = input.toLowerCase();
		switch (userInput) {
		case TODAY:
			return CommandUtils.createShowTodayCommand();

		case NEXT_SEVEN:
			return CommandUtils.createShowNext7DaysCommand();

		case DONE:
			return CommandUtils.createShowCompletedCommand();

		case ALL:
			return CommandUtils.createShowAllCommand();
			
		case FLOAT:
			return CommandUtils.createShowFloatCommand();

		case COMPLETED:
			return CommandUtils.createShowCompletedCommand();

		// case DATE :
		// DateTimeParser dateTimeParser = new DateTimeParser();
		// DateTime date = dateTimeParser.parse(userInput);
		// return CommandUtils.createShowCommand(COMMAND_DATE, date);

		case CATEGORY:
			return CommandUtils.createShowCategoryCommand(getCatName(userInput));

		default:
			return CommandUtils.createInvalidCommand("Invalid Show command");
		}
	}

	private String getCatName(String userInput2) {
		int index = userInput.indexOf(" ");
		String ans = userInput.substring(index).trim();
		return ans;
	}
}
