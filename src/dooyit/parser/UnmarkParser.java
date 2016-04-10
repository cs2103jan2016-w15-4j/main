//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The UnmarkParser class is needed for unmarking completed tasks.
 * Completed tasks that are unmarked will be removed from the completed view and 
 * placed back in the original location. This class takes in an "unmark" command 
 * input and returns a Command object. It is a child class of the 
 * TagParser class.
 * 
 * @author Annabel
 *
 */
public class UnmarkParser extends TagParser{
	// Error message
	private static final String ERROR_MESSAGE_INVALID_UNMARK_COMMAND = "Invalid Unmark Command!";

	// Logger for UnmarkParser
	private static Logger logger = Logger.getLogger("UnmarkParser");
	
	/** Initializes a new UnmarkParser object */
	public UnmarkParser() {
		super();
		logger.log(Level.INFO, "Initialised UnmarkParser object");
	}
	
	/**
	 * Parses userInput and returns an UnMarkCommand object
	 * 
	 * @param input
	 * 		  The delete command input from the user
	 * 
	 * @return a DeleteCommand object if the taskIds are valid
	 * 		   or an InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from UnmarkParser");
		// Reset object attributes for each call to the getCommand method
		setAttributesForTagging(input);
		resetCommandAttributeToNull();
		
		setCommandAttribute();
		return command;
	}

	/**
	 * Set command attribute to an UnMarkCommand object or 
	 * to an InvalidCommand object.
	 */
	private void setCommandAttribute() {
		// Checks if the taskIds are valid
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			setToInvalidCommand(e.getMessage());
		}
		
		// Checks if the command has been set to InvalidCommand object
		if (command == null) {
			// Set command attribute to an UnMarkCommand object 
			// if it hasn't been set to an InvalidCommand object.
			setUnmarkCommand(getTagType());
		}
	}
	
	/**
	 * Sets command attribute to a UnMarkCommand object or to
	 * an InvalidCommand object.
	 */
	private void setUnmarkCommand(TagType tagType) {
		switch (tagType) {
		case VALID :
			command = CommandUtils.createUnMarkCommand(taskIdsForTagging);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_UNMARK_COMMAND);
			break;
		}
	}
}
