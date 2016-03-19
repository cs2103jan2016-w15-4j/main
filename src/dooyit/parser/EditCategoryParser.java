package dooyit.parser;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

public class EditCategoryParser {
	private static final String MARKER_COLOUR = " to ";
	private static final int INDEX_ORIGINAL_NAME = 0;
	private static final int INDEX_NEW_NAME = 1;
	private static final int LENGTH_OF_COLOUR_MARKER = 5;
	private static final int UNINITIALIZED = -1;

	private String originalName;
	private String newName;
	private String newColour;
	
	private static final int EDIT_NAME_AND_COLOUR = 1;
	private static final int EDIT_NAME_ONLY = 2;
	private static final int EDIT_COLOUR_ONLY = 3;
	private static final int INVALID_TOO_MANY_WORDS = 4;
	private static final int INVALID_TOO_FEW_ARGUMENTS = 5;
	
	private boolean hasInsufficientArguments;
	private boolean hasTooManyWordsInNewCategoryName;
	
	private static Command command;
	
	public EditCategoryParser() {
		
	}
	
	public Command getCommand(String input) {
		resetVariables();
		setVariables(input);
		switch(getEditType()) {
		
		case EDIT_NAME_AND_COLOUR: 
			//command = CommandUtils.createEditCategoryCommand(originalName, newName, newColour);
			break;
		
		case EDIT_NAME_ONLY:
			//command = CommandUtils.creatEditCategoryNameCommand(originalName, newName);
			break;
		
		case EDIT_COLOUR_ONLY:
			//command = CommandUtils.createEditCategoryColourCommand(originalName, newColour);
			break;
		
		case INVALID_TOO_MANY_WORDS: 
			//command = CommandUtils.creatInvalidEditCategoryCommand("Can't you be more succinct in your category naming?");
			break;
		
		case INVALID_TOO_FEW_ARGUMENTS:
			command = CommandUtils.createInvalidCommand("Too few arguments for Edit Category Command");
			break;
		
		default:
			command = CommandUtils.createInvalidCommand("Invalid Edit Category Command!");
			break;
		}
		return command;
	}

	private int getEditType() {
		int editType = UNINITIALIZED;
		if(hasTooManyWordsInNewCategoryName) {
			editType = INVALID_TOO_MANY_WORDS;
		} else if(hasInsufficientArguments) {
			editType = INVALID_TOO_FEW_ARGUMENTS;
		} if(newName != null && newColour != null) {
			editType = EDIT_NAME_AND_COLOUR;
		} else if(newName == null && newColour != null) {
			editType = EDIT_COLOUR_ONLY;
		} else if(newName != null && newColour == null) {
			editType = EDIT_NAME_ONLY;
		} else {
			
		}
		return editType;
	}

	private void setVariables(String input) {
		String[] inputArr = input.split("//s+");
		originalName = inputArr[INDEX_ORIGINAL_NAME];
		
		if(inputArr.length == 1) {
			hasInsufficientArguments = true;
		} else {
			if(!inputArr[INDEX_NEW_NAME].equals(MARKER_COLOUR.trim())) {
				newName = inputArr[INDEX_NEW_NAME];
			}
			
			int indexOfMarkerColour = input.toLowerCase().indexOf(MARKER_COLOUR);
			if(indexOfMarkerColour != -1) {
				int indexOfColour = indexOfMarkerColour + LENGTH_OF_COLOUR_MARKER;
				newColour = input.substring(indexOfColour);
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
		hasTooManyWordsInNewCategoryName = false;
	}

}
