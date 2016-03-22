package dooyit.parser;

import java.util.ArrayList;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class AddCategoryParser extends TagParser{
	
	private static String userInput;
	private static String catName;
	private static String catColour;
	private static final String DEFAULT_COLOUR = "";
	
	private static Command command;
	
	private static final int INDEX_NAME = 0;
	private static final int INDEX_COLOUR = 1;

	private static boolean hasTasks;
	private static boolean hasColour;
	
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
			command = CommandUtils.createInvalidCommand(userInput + " is an invalid addcat command!");
			break;
		}

		return command;
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
			command = getCreateCategoryCommand(taskIdForTagging);
			break;

		case MULTIPLE:
			command = getCreateCategoryCommand(taskIdsForTagging);
			break;

		case INTERVAL:
			command = getCreateCategoryCommand(taskIdsForTagging);
			break;

		default:
			command = getInvalidCmd();
			break;
		}
	}

	private Command getCreateCategoryCommand(ArrayList<Integer> taskIdsForTagging) {
		Command temp = null;
		if(hasColour) {
			//temp = CommandUtils.createAddCategoryCommand(catName, catColour, taskIdsForTagging);
		} else {
			//temp = CommandUtils.createAddCatergoryCommand(catName, taskIdsForTagging); 
		}
		return temp;
	}

	private Command getCreateCategoryCommand(int taskIdForTagging) {
		Command temp = null;
		if(hasColour) {
			//temp = CommandUtils.createAddCategoryCommand(catName, catColour, taskIdForTagging);
		} else {
			//temp = CommandUtils.createAddCatergoryCommand(catName, taskIdForTagging); 
		}
		return temp;
	}

	private Command getInvalidCmd() {
		return CommandUtils.createInvalidCommand("Invalid Add Category Command!");
	}
	
	private void getTaskIds() {
		if(hasColour) {
			int indexOfTaskIds = userInput.indexOf(catColour) + catColour.length() - 1;
			String taskIdString = userInput.substring(indexOfTaskIds).trim();
			setVariables(taskIdString);
		} else {
			int indexOfTaskIds = userInput.indexOf(catName) + catName.length() - 1;
			String taskIdString = userInput.substring(indexOfTaskIds).trim();
			System.out.println("taskId string is " + taskIdString);
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
		if(inputArr.length != 1) {
			catColour = inputArr[INDEX_COLOUR];
			hasColour = !isNumber(catColour);
		}
	}
}
