//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * The DeleteParser class provides methods needed for deleting tasks.
 * It takes in a "delete" or "remove" or "rm" command input and returns 
 * a DeleteCcommand object. It is a child class of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class DeleteParser extends TagParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_DELETE_COMMAND = "Invalid Delete Command!";
	
	// DeleteParser object attribute
	private Command command;
	
	// Logger for DeleteParser
	private static Logger logger = Logger.getLogger("DeleteParser");

	/** Initializes a new DeleteParser object */
	public DeleteParser() {
		super();
		logger.log(Level.INFO, "Initialised DeleteParser object");
	}

	/**
	 * Parses userInput and returns the correct DeleteCommand object
	 * 
	 * @param input
	 * 		  The delete command input from the user
	 * 
	 * @return the correct DeleteCommand object if the taskIds are valid
	 * 		   or the InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from DeleteParser");
		// Reset object attribute for each call to the getCommand method
		resetAttribute();
		setAttributesForTagging(input);
		
		// Checks if the taskIds are valid
		try {
			parseTaskIds();
		} catch (IncorrectInputException e) {
			setInvalidCommand(e.getMessage());
		}
		
		// Checks if the command has been set to InvalidCommand object
		if (command == null) {
			setCorrectDeleteCommand();
		}
			
		return command;
	}
	
	/**
	 * Resets the command attribute to null;
	 */
	private void resetAttribute() {
		command = null;
	}

	/**
	 * Sets the command attribute to an InvalidCommand object.
	 * 
	 * @param message
	 * 		  Error message that indicates why the user input is wrong
	 */
	private void setInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}

	/**
	 * Sets command attribute to a DeleteCommand object or to
	 * an InvalidCommand object.
	 * 
	 */
	private void setCorrectDeleteCommand() {
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
