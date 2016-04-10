//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The moveParser class is needed for moving tasks into a category.
 * It takes in a "move" command input and returns a Command object. 
 * It is a child class of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class MoveParser extends TagParser {
	// Error messages
	private static final String ERROR_MESSAGE_NO_CATEGORY_SPECIFIED = "No category specified!";
	private static final String ERROR_MESSAGE_INVALID_MOVE_COMMAND = "Invalid move Command!";
	
	// MoveParser object attributes
	private String categoryName;
	private String taskIds;
	private boolean hasCategory;
	
	// Logger for MoveParser
	private static Logger logger = Logger.getLogger("MoveParser");
	
	/** Initializes a new MoveParser object */
	public MoveParser() {
		super();
		logger.log(Level.INFO, "Initialised MoveParser object");
	}
	
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from MoveParser");
		parse(input);
		
		setCommandAttribute();
		return command;
	}

	/**
	 * Sets command attribute to a MoveToCategory command object 
	 * or to an InvalidCommand object
	 */
	private void setCommandAttribute() {
		if (!hasCategory) {
			setToInvalidCommand(ERROR_MESSAGE_NO_CATEGORY_SPECIFIED); 
		} else {
			setAttributesForTagging(taskIds);
			resetCommandAttributeToNull();
			
			// Checks if the taskIds are valid
			try {
				parseTaskIds();
			} catch (IncorrectInputException e) {
				setToInvalidCommand(e.getMessage()); 
			}
		}
		
		// Checks if the command has been set to InvalidCommand object
		if (command == null) {
			// Set command attribute to a MoveToCategoryCommand object 
			// if it hasn't been set to an InvalidCommand object.
			setMoveCommand(getTagType());
		}
	}
	
	/**
	 * Sets command attribute to a MoveToCategoryCommand object or to
	 * an InvalidCommand object.
	 */
	private void setMoveCommand(TagType tagType) {
		switch (tagType) {
		case VALID :
			command = CommandUtils.createMoveToCategoryCommand(taskIdsForTagging, categoryName);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MOVE_COMMAND);
			break;
		}
	}

	private void parse(String input) {
		hasCategory = false;
		String[] splitInput = input.split("\\s+");
		int indexOfCategoryName = splitInput.length - 1;
		categoryName = splitInput[indexOfCategoryName];
		
		if (ParserCommons.isNumber(categoryName) || isIntervalType(categoryName)) {
			hasCategory = false;
		} else {
			hasCategory = true;
			taskIds = input.replace(categoryName, EMPTY_STRING).trim();
		}
	}
}
