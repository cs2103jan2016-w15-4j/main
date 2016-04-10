//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * The MarkParser class takes in a "mark" command input and returns 
 * a Command object. It is needed to mark tasks as completed.
 * It is a child class of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class MarkParser extends TagParser{
	// Error message
	private static final String ERROR_MESSAGE_INVALID_MARK_COMMAND = "Invalid mark Command!";
	
	// Logger for MarkParser
	private static Logger logger = Logger.getLogger("MarkParser");

	/** Initializes a new MarkParser object */
	public MarkParser() {
		super();
		logger.log(Level.INFO, "Initialised MarkParser object");
	}

	/**
	 * Parses userInput and returns a MarkCommand object
	 * 
	 * @param input
	 * 		  The mark command input from the user
	 * 
	 * @return a MarkCommand object if the taskIds are valid
	 * 		   or an InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from MarkParser");
		// Reset object attributes for each call to the getCommand method
		setAttributesForTagging(input);
		resetCommandAttributeToNull();
		
		setCommandAttribute();
		return command;
	}

	/**
	 * Set command attribute to MarkCommand object or to InvalidCommand object
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
			// Set command attribute to a MarkCommand object 
			// if it hasn't been set set to an InvalidCommand object.
			setMarkCommand(getTagType());
		}
	}

	/**
	 * Sets command attribute to a MarkCommand object or to
	 * an InvalidCommand object.
	 */
	private void setMarkCommand(TagType tagType) {
		switch (tagType) {
		case VALID :
			command = CommandUtils.createMarkCommand(taskIdsForTagging);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_MARK_COMMAND);
			break;
		}
	}
}
