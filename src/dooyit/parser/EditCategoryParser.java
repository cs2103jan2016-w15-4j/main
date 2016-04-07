//@@author A0133338J
package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class EditCategoryParser implements ParserCommons {
	private static final String ERROR_MESSAGE_INVALID_EDIT_CATEGORY_COMMAND = "Invalid Edit Category Command!";
	private static final String ERROR_MESSAGE_TOO_FEW_ARGUMENTS = "Too few arguments for Edit Category Command";
	private static final String ERROR_MESSAGE_BE_SUCCINCT = "Can't you be more succinct in your category naming?";
	private static final String MARKER_COLOUR = " to ";
	private static final int INDEX_ORIGINAL_NAME = 0;
	private static final int INDEX_NEW_NAME = 1;
	
	private static final int EDIT_NAME_AND_COLOUR = 1;
	private static final int EDIT_NAME_ONLY = 2;
	private static final int EDIT_COLOUR_ONLY = 3;
	private static final int INVALID_TOO_MANY_WORDS = 4;
	private static final int INVALID_TOO_FEW_ARGUMENTS = 5;
	private static final int EDIT_INVALID = 6;
	
	private String originalName;
	private String newName;
	private String newColour;

	private boolean hasInsufficientArguments;
	private boolean hasTooManyWordsInNewCategoryName;
	private boolean hasNewName;
	private boolean hasNewColour;
	
	private Command command;
	
	public EditCategoryParser() {
		
	}
	
	public Command getCommand(String input) {
		resetVariables();
		setVariables(input);
		switch(getEditType()) {
		
		case EDIT_NAME_AND_COLOUR: 
			command = CommandUtils.createEditCategoryCommand(originalName, newName, newColour);
			break;
		
		case EDIT_NAME_ONLY:
			command = CommandUtils.createEditCategoryCommand(originalName, newName);
			break;
		
		case EDIT_COLOUR_ONLY:
			command = CommandUtils.createEditCategoryCommand(originalName, originalName, newColour);
			break;
		
		case INVALID_TOO_MANY_WORDS: 
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_BE_SUCCINCT);
			break;
		
		case INVALID_TOO_FEW_ARGUMENTS:
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_TOO_FEW_ARGUMENTS);
			break;
		
		default:
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_EDIT_CATEGORY_COMMAND);
			break;
		}
		return command;
	}

	private int getEditType() {
		int editType;
		if(hasTooManyWordsInNewCategoryName) {
			editType = INVALID_TOO_MANY_WORDS;
			
		} else if(hasInsufficientArguments) {
			editType = INVALID_TOO_FEW_ARGUMENTS;
			
		} if(hasNewName && hasNewColour) {
			editType = EDIT_NAME_AND_COLOUR;
			
		} else if(!hasNewName && hasNewColour) {
			editType = EDIT_COLOUR_ONLY;
			
		} else if(hasNewName && !hasNewColour) {
			editType = EDIT_NAME_ONLY;
			
		} else {
			editType = EDIT_INVALID;
		}
		return editType;
	}

	private void setVariables(String input) {
		String[] inputArr = input.split("\\s+");
		originalName = inputArr[INDEX_ORIGINAL_NAME];
		
		if(inputArr.length == 1) {
			hasInsufficientArguments = true;
		} else {
			if(!inputArr[INDEX_NEW_NAME].equals(MARKER_COLOUR.trim())) {
				newName = inputArr[INDEX_NEW_NAME];
				hasNewName = true;
			}
			
			if(input.contains(MARKER_COLOUR)) {
				int indexOfColour = inputArr.length - 1;
				newColour = inputArr[indexOfColour];
				hasNewColour = true;
			}
			
			if(inputArr.length > 4) {
				hasTooManyWordsInNewCategoryName = true;
			}
		}
	}

	private void resetVariables() {
		command = null;
		originalName = null;
		newName = null;
		newColour = null;
		hasInsufficientArguments = false;
		hasNewName = false;
		hasNewColour = false;
		hasTooManyWordsInNewCategoryName = false;
	}

}
