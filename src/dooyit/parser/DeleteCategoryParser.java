//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The DeleteCategoryParser class provides methods for deleting a category. 
 * It takes in a "deletecat" command input and returns a DeleteCategoryCommand 
 * object. It implements the ParserCommons interface to use the shared
 * constants and methods.
 * 
 * @author Annabel
 *
 */
public class DeleteCategoryParser implements ParserCommons {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_DELETE_CATEGORY_COMMAND = "Invalid Delete Category Command!";

	// Logger for DeleteCategoryParser
	private static Logger logger = Logger.getLogger("DeleteCategoryParser");
	
	/** Initializes a new DeleteCategoryParser object */
	public DeleteCategoryParser() {
		logger.log(Level.INFO, "Initialised DeleteCategoryParser object");
	}
	
	/**
	 * Parses userInput and returns correct AddCommand object
	 * 
	 * @param input
	 *        The deletecat command input from the user
	 * 
	 * @return the correct DeleteCategoryCommand object if the command input is
	 *         valid or an invalid command object if the command input is
	 *         invalid
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from DeleteCategoryParser");
		Command command = null;
		
		if (input.equals(EMPTY_STRING)) {
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_DELETE_CATEGORY_COMMAND);
		} else {
			command = CommandUtils.createDeleteCategoryCommand(input);
		}
		return command;
	}
}
