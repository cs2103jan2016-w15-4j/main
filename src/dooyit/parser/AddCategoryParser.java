//@@author A0133338J 
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCategoryParser extends TagParser{
	private static final String ERROR_MESSAGE_INVALID_ADDCAT_COMMAND = "Invalid addcat command!";
	private static final String DEFAULT_COLOUR = "";
	private static final int INDEX_NAME = 0;
	private static final int INDEX_COLOUR = 1;
	
	private String userInput;
	private String catName;
	private String catColour;
	private Command command;
	private boolean hasColour;
	
	enum ADD_CATEGORY_TYPE {
		CREATE_NEW_CATEGORY_WITHOUT_TASKS, CREATE_NEW_CATEGORY_WITH_TASKS, INVALID
	};

	public AddCategoryParser() {
		super();
	}

	public Command getCommand(String input) {
		resetVariables(input);
		parse();
		
		switch (getCommandType()) {
		case CREATE_NEW_CATEGORY_WITHOUT_TASKS :
			setCommandToCreateCategoryWithoutTasks();
			break;

		default :
			setInvalidCommand();
			break;
		}

		return command;
	} 

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_ADDCAT_COMMAND);
	}

	private void resetVariables(String input) {
		userInput = input;
		catColour = DEFAULT_COLOUR;
		hasColour = false;
	}

	private ADD_CATEGORY_TYPE getCommandType() {
		if (userInput.equals(EMPTY_STRING)) {
			return ADD_CATEGORY_TYPE.INVALID;
		} else  {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITHOUT_TASKS;
		} 
	}

	private void setCommandToCreateCategoryWithoutTasks() {
		if (hasColour) {
			command = CommandUtils.createAddCategoryCommand(catName, catColour);
		} else { 
			command = CommandUtils.createAddCategoryCommand(catName);
		}
	}

	private void parse() {
		String[] inputArr = userInput.split("\\s+");
		catName = inputArr[INDEX_NAME];
		if (!isOneWordInput(inputArr)) {
			catColour = inputArr[INDEX_COLOUR];
			hasColour = !ParserCommons.isNumber(catColour);
		}
	}

	private static boolean isOneWordInput(String[] inputArr) {
		return inputArr.length == 1;
	}
}
