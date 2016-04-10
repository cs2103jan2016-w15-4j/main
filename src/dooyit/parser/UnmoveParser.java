//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The UnmoveParser class is needed for marking tasks as completed.
 * It takes in a "unmove" command input and returns a Command object. 
 * It is a child class of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class UnmoveParser extends TagParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_UNMOVE_COMMAND = "Invalid Unmove Command!";

	// Logger for UnmoveParser
	private static Logger logger = Logger.getLogger("UnmoveParser");
	
	/** Initializes a new UnmoveParser object */
	public UnmoveParser() {
		super();
		logger.log(Level.INFO, "Initialised UnmoveParser object");
	}

	/**
	 * Parses userInput and returns a UnMoveCommand object
	 * 
	 * @param input
	 * 		  The unmark command input from the user
	 * 
	 * @return an UnMarkCommand object if the taskIds are valid
	 * 		   or an InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from UnmoveParser");
		// Reset object attributes for each call to the getCommand method
		setAttributesForTagging(input);
		resetCommandAttributeToNull();
		
		// Checks if the taskIds are valid
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			setToInvalidCommand(e.getMessage());
		}
		
		// Checks if the command has been set to InvalidCommand object
		if (command == null) {
			// Set command attribute to an UnMoveCommand object 
			// if it hasn't been set to an InvalidCommand object.
			setToUnmarkCommand(getSingleMultipleTagType());
		}
		
		return command;
	}

	/**
	 * Sets command attribute to an UnMove Command object or to
	 * an InvalidCommand object.
	 */
	private void setToUnmarkCommand(TagType tagType) {
		switch (tagType) {
		case SINGLE :
			int taskId = getSingleTaskId();
			command = CommandUtils.createUnMoveCategoryCommand(taskId);
			break;
			
		case MULTIPLE :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_TOO_MANY_IDS);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_UNMOVE_COMMAND);
			break;
		}
	}
}