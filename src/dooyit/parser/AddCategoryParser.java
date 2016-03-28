package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCategoryParser extends TagParser{
	
	private static final String ERROR_MESSAGE_INVALID_ADDCAT_COMMAND = "Error: Invalid addcat command!";
	private static final String DEFAULT_COLOUR = "";
	private static final int INDEX_NAME = 0;
	private static final int INDEX_COLOUR = 1;
	
	private String userInput;
	private String catName;
	private String catColour;
	private Command command;
	private boolean hasTasks;
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
		case CREATE_NEW_CATEGORY_WITHOUT_TASKS:
			setCommandToCreateCategoryWithoutTasks();
			break;

		case CREATE_NEW_CATEGORY_WITH_TASKS: 
			getTaskIds();
			setCommandToCreateCategoryWithTasks();
			break;

		default:
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
		hasTasks = false;
		hasColour = false;
	}

	private void setCommandToCreateCategoryWithTasks() {
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			command = getInvalidCommand(e.getMessage());
		}
		
		if(command == null) {
			setCorrectCategoryWithTasksCommand(getTagType());
		}
	}
	
	private void setCorrectCategoryWithTasksCommand(TAG_TYPE tagType) {
		switch (tagType) {
		case SINGLE:
			setCreateCategoryWithOneTaskCommand();
			break;

		case MULTIPLE:
			setCreateCategoryWithManyTasksCommand();
			break;

		case INTERVAL:
			setCreateCategoryWithManyTasksCommand();
			break;

		default: 
			setInvalidCmd();
			break;
		}
	}

	private void setCreateCategoryWithOneTaskCommand() {
		if(hasColour) {
			//command = CommandUtils.createAddCategoryCommand(catName, catColour, taskIdsForTagging);
		} else {
			//command = CommandUtils.createAddCatergoryCommand(catName, taskIdsForTagging); 
		}
	}

	private void setCreateCategoryWithManyTasksCommand() {
		if(hasColour) {
			//command = CommandUtils.createAddCategoryCommand(catName, catColour, taskIdForTagging);
		} else {
			//command = CommandUtils.createAddCatergoryCommand(catName, taskIdForTagging); 
		}
	}

	private void setInvalidCmd() {
		command = CommandUtils.createInvalidCommand("Invalid Add Category Command!");
	}
	
	private void getTaskIds() {
		if(hasColour) {
			int indexOfTaskIds = userInput.indexOf(catColour) + catColour.length();
			String taskIdString = userInput.substring(indexOfTaskIds).trim();
			setVariables(taskIdString);
		} else {
			int indexOfTaskIds = userInput.indexOf(catName) + catName.length();
			String taskIdString = userInput.substring(indexOfTaskIds).trim();
			setVariables(taskIdString);
		}
	}

	private ADD_CATEGORY_TYPE getCommandType() {
		if(userInput.equals("")) {
			return ADD_CATEGORY_TYPE.INVALID;
		} else if(hasTasks) {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITH_TASKS;
		} else {
			return ADD_CATEGORY_TYPE.CREATE_NEW_CATEGORY_WITHOUT_TASKS;
		} 
	}

	private void setCommandToCreateCategoryWithoutTasks() {
		if(hasColour) {
			command = CommandUtils.createAddCategoryCommand(catName, catColour);
		} else { 
			command = CommandUtils.createAddCategoryCommand(catName);
		}
	}

	private void parse() {
		String[] inputArr = userInput.split("\\s+");
		catName = inputArr[INDEX_NAME];
		if(!isOneWordInput(inputArr)) {
			catColour = inputArr[INDEX_COLOUR];
			hasColour = !isNumber(catColour);
		}
		
		for(int i = INDEX_COLOUR; i < inputArr.length; i++) {
			if(isNumber(inputArr[i])) {
				hasTasks = true;
				break;
			}
		}
	}

	private boolean isOneWordInput(String[] inputArr) {
		return inputArr.length == 1;
	}
}
