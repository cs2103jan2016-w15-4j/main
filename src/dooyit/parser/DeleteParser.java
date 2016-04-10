//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The DeleteParser class takes in a "delete" or "remove" or "rm" 
 * command input and returns a DeleteCcommand object. It is needed
 * for deleting tasks. It is a child class of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class DeleteParser extends TagParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Invalid Delete Command!";
	
	// Logger for DeleteParser
	private static Logger logger = Logger.getLogger("DeleteParser");

	/** Initializes a new DeleteParser object */
	public DeleteParser() {
		super();
		logger.log(Level.INFO, "Initialised DeleteParser object");
	}

	/**
	 * Parses userInput and returns a DeleteCommand object
	 * 
	 * @param input
	 * 		  The delete command input from the user
	 * 
	 * @return a DeleteCommand object if the taskIds are valid
	 * 		   or an InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from DeleteParser");
		// Reset object attributes for each call to the getCommand method
		setAttributesForTagging(input);
		resetCommandAttributeToNull();
		
		setCommandAttribute();
		return command;
	}

	/**
	 * Sets command attribute to DeleteCommand or to InvalidCommand
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
			// Set command attribute to a DeleteCommand object 
			// if it hasn't been set set to an InvalidCommand object.
			setToDeleteCommand();
		}
	}

	/**
	 * Sets command attribute to a DeleteCommand object or to
	 * an InvalidCommand object.
	 */
	private void setToDeleteCommand() {
		switch (getTagType()) {
		case VALID :
			command = CommandUtils.createDeleteCommand(taskIdsForTagging);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_COMMAND);
			break;
		}
	}
}
