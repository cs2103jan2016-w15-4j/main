//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/** 
 * EditCategoryParser takes in "editcat" command inputs and returns a 
 * Command object. It is needed to edit category name and colour.
 * 
 * @author Annabel
 *
 */
public class EditCategoryParser {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_EDIT_CATEGORY_COMMAND = "Invalid Edit Category Command!";
	private static final String ERROR_MESSAGE_TOO_FEW_ARGUMENTS = "Too few arguments for Edit Category Command";
	private static final String ERROR_MESSAGE_BE_SUCCINCT = "Can't you be more succinct in your category naming?";
	
	// Marker to specify colour in user input
	private static final String MARKER_COLOUR = " to ";
	
	// Position indices in string array of user input
	private static final int INDEX_ORIGINAL_NAME = 0;
	private static final int INDEX_NEW_NAME = 1;
	
	// Constants of number of words allowed 
	private static final int NUMBER_OF_WORDS_INSUFFICIENT = 1;
	private static final int NUMBER_OF_WORDS_MAXIMUM = 4;
	private static final int NUMBER_OF_WORDS_FOR_NEW_AND_ORIGINAL_NAME = 2;
	
	// Possible types of edits to a category
	private static final int EDIT_TYPE_NAME_AND_COLOUR = 1;
	private static final int EDIT_TYPE_NAME_ONLY = 2;
	private static final int EDIT_TYPE_COLOUR_ONLY = 3;
	private static final int EDIT_TYPE_INVALID_TOO_MANY_WORDS = 4;
	private static final int EDIT_TYPE_INVALID_TOO_FEW_ARGUMENTS = 5;
	private static final int EDIT_TYPE_INVALID = 6;
	
	// Logger for EditCategoryParser
	private static Logger logger = Logger.getLogger("EditCategoryParser");
	
	// EditCategoryParser object attributes
	private String originalName;
	private String newName;
	private String newColour;
	private boolean hasInsufficientArguments;
	private boolean hasTooManyWordsInNewCategoryName;
	private boolean hasNewName;
	private boolean hasNewColour;
	private Command command;
	
	/** Initializes a new EditCategoryParser object */
	public EditCategoryParser() {
		logger.log(Level.INFO, "Initialised EditCategoryParser object");
	}
	
	/** 
	 * Parses the user input and returns the correct command.
	 * 
	 * @param input
	 * 		  The user input
	 * 
	 * @return the correct Command object.
	 */
	public Command getCommand(String input) {
		resetAttributes();
		setAttributes(input);
		setCommandAttribute();
		return command;
	}

	/**
	 * Sets the command attribute to the correct EditCategoryCommand object
	 * if the user input is valid or to an InvalidCommand object if the input
	 * is invalid.
	 */
	private void setCommandAttribute() {
		switch (getEditType()) {
		case EDIT_TYPE_NAME_AND_COLOUR : 
			command = CommandUtils.createEditCategoryCommand(originalName, newName, newColour);
			break;
		
		case EDIT_TYPE_NAME_ONLY :
			command = CommandUtils.createEditCategoryCommand(originalName, newName);
			break;
		
		case EDIT_TYPE_COLOUR_ONLY :
			command = CommandUtils.createEditCategoryCommand(originalName, originalName, newColour);
			break;
		
		case EDIT_TYPE_INVALID_TOO_MANY_WORDS : 
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_BE_SUCCINCT);
			break;
		
		case EDIT_TYPE_INVALID_TOO_FEW_ARGUMENTS :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_TOO_FEW_ARGUMENTS);
			break;
		
		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_EDIT_CATEGORY_COMMAND);
			break;
		}
	}

	/** 
	 * Checks the object's boolean attributes to return the 
	 * correct type of category edit.
	 * 
	 * @return the type of category edit.
	 */
	private int getEditType() {
		int editType;
		if (hasTooManyWordsInNewCategoryName) {
			editType = EDIT_TYPE_INVALID_TOO_MANY_WORDS;
			
		} else if (hasInsufficientArguments) {
			editType = EDIT_TYPE_INVALID_TOO_FEW_ARGUMENTS;
			
		} else if (hasNewName && hasNewColour) {
			editType = EDIT_TYPE_NAME_AND_COLOUR;
			
		} else if (!hasNewName && hasNewColour) {
			editType = EDIT_TYPE_COLOUR_ONLY;
			
		} else if (hasNewName && !hasNewColour) { 
			editType = EDIT_TYPE_NAME_ONLY;
			
		} else {
			editType = EDIT_TYPE_INVALID;
		}
		return editType;
	}

	/** 
	 * Checks the user input and sets the object attributes
	 * accordingly.
	 * 
	 * @param input
	 * 		  The user input.
	 */
	private void setAttributes(String input) {
		String[] inputArr = input.split("\\s+");
		originalName = inputArr[INDEX_ORIGINAL_NAME];
		if (inputArr.length == NUMBER_OF_WORDS_INSUFFICIENT) {
			hasInsufficientArguments = true;
			
		} else if (inputArr.length > NUMBER_OF_WORDS_MAXIMUM){
			hasTooManyWordsInNewCategoryName = true;
			
		} else {
			if (input.contains(MARKER_COLOUR)) {
				setNewColour(inputArr);
				setNewName(input); 
			} else {
				setNewName(inputArr);
			}
		}
	}

	/** 
	 * Sets the category name related attributes. This is for 
	 * the case where the user does NOT specify the category colour.
	 * 
	 * @param inputArr
	 * 		  The user input that is split into a String array.
	 */
	private void setNewName(String[] inputArr) {
		newName = inputArr[INDEX_NEW_NAME];
		hasNewName = true;
	}

	/** 
	 * Sets the category colour related attributes.
	 * 
	 * @param inputArr
	 * 		  The user input that is split into a String array.
	 */
	private void setNewColour(String[] inputArr) {
		int indexOfColour = inputArr.length - 1;
		newColour = inputArr[indexOfColour];
		hasNewColour = true;
	}

	/** 
	 * Sets the category name related attributes. This is for 
	 * the case where the user specifies the category colour.
	 * 
	 * @param input
	 * 		  The user input
	 */
	private void setNewName(String input) {
		int lastIndexOfMarker = input.lastIndexOf(MARKER_COLOUR);
		String firstHalfOfInput = input.substring(0, lastIndexOfMarker).trim();
		if (firstHalfOfInput.equals(originalName)) {
			hasNewName = false;
		} else {
			String[] splitFirstHalf = firstHalfOfInput.split("\\s+");
			if (splitFirstHalf.length == NUMBER_OF_WORDS_FOR_NEW_AND_ORIGINAL_NAME) {
				hasNewName = true;
				newName = splitFirstHalf[INDEX_NEW_NAME];
			} else {
				hasTooManyWordsInNewCategoryName = true;
			}
		}
	}

	/** 
	 * Resets object attributes to null or false
	 */
	private void resetAttributes() {
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
