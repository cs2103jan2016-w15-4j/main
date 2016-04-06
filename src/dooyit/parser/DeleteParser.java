//@@author A0133338J
package dooyit.parser;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;
import dooyit.parser.TagParser.TAG_TYPE;

public class DeleteParser extends TagParser {
	private static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Invalid Delete Command!";
	private static final String ERROR_MESSAGE_NO_TASK_ID = "No Task IDs specified!";
	private Command command;
	private boolean hasCategory;
	private String categoryName;

	public DeleteParser() {
		super();
		
	}

	public Command getCommand(String input) throws IncorrectInputException {
		resetFields();
		setCategoryName(input);
		input = removeCategoryNameFromInput(input);
		
		if(input.equals(EMPTY_STRING)) {
			setInvalidCommand(ERROR_MESSAGE_NO_TASK_ID);
		} else {
			setVariables(input);
			
			try {
				parseTaskIds();
			} catch(IncorrectInputException e) {
				setInvalidCommand(e.getMessage());
			}
			
			if(command == null) {
				if(hasCategory) { 
					setDeleteCommandWithCategoryName(getTagType());
				} else {
					setCorrectDeleteCommand(getTagType());
				}
			}
			
		}
		return command;
	}

	private void setDeleteCommandWithCategoryName(TAG_TYPE tagType) {
		switch(tagType) {
		case SINGLE:
			setSingleTypeDeleteCommandWithCategoryName();
			break;

		case MULTIPLE:
			setMultipleTypeDeleteCommandWithCategoryName();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setMultipleTypeDeleteCommandWithCategoryName() {
		//command = CommandUtils.createDeleteCommand(taskIdsForTagging, categoryName);
	}

	private void setSingleTypeDeleteCommandWithCategoryName() {
		//command = CommandUtils.createDeleteCommand(taskIdForTagging, categoryName);
	}

	private String removeCategoryNameFromInput(String input) {
		if(hasCategory) {
			input = input.replace(categoryName, EMPTY_STRING);
		}
		return input;
	}

	private void setCategoryName(String input) {
		String[] splitInput = input.split("\\s+");
		int indexOfName = splitInput.length - 1;
		String currWord = splitInput[indexOfName];
		
		if(!ParserCommons.isNumber(currWord) && !currWord.contains(MARKER_FOR_INTERVAL_TAG_TYPE)) {
			hasCategory = true;
			System.out.println("reached here");
			categoryName = currWord;
		}
	}
	
	private void resetFields() {
		hasCategory = false;
		categoryName = EMPTY_STRING;
		command = null;
	}

	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	private void setCorrectDeleteCommand(TAG_TYPE tagType) {
		switch(tagType) {
		case SINGLE:
			setSingleTypeDeleteCommand();
			break;

		case MULTIPLE:
			setMultipleTypeDeleteCommand();
			break;

		default:
			setInvalidCommand();
			break;
		}
	}

	private void setMultipleTypeDeleteCommand() {
		command = CommandUtils.createDeleteCommand(taskIdsForTagging);
	}

	private void setSingleTypeDeleteCommand() {
		command = CommandUtils.createDeleteCommand(taskIdForTagging);
	}

	private void setInvalidCommand() {
		command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_COMMAND);
	}
}
