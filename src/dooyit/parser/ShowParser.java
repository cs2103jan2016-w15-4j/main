//@@author A0133338J
package dooyit.parser;

import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

public class ShowParser {
	private static final String ERROR_MESSAGE_INVALID_SHOW_COMMAND = "Invalid show command";
	private static final String TODAY = "today";
	private static final String NEXT_SEVEN = "next7";
	private static final String DONE = "done";
	private static final String ALL = "all";
	private static final String COMPLETED = "completed";
	private static final String FLOAT = "float";
	
	private String userInput;
	private Command command;
	
	public ShowParser() {
	
	}

	public Command getCommand(String input) { 
		userInput = input.toLowerCase();
		command = null;
		switch (userInput) { 
		case TODAY :
			setShowTodayCommand();
			break;

		case NEXT_SEVEN :
			setShowNextSevenDaysCommand();
			break;

		case DONE :
			setShowDoneCommand();
			break;

		case ALL :
			setShowAllCommand();
			break;
			
		case FLOAT :
			setShowFloatCommand();
			break;

		case COMPLETED :
			setShowDoneCommand();
			break;

		default :
			setShowInvalidMessageCommand();
			break;
		}
		return command;
	}

	private void setShowInvalidMessageCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_SHOW_COMMAND);
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
}
