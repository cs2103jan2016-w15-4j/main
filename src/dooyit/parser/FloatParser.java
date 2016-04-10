//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The FloatParser takes in a "float" or "editToFloat" command input 
 * and returns an EditToFloatCommand object. It is needed for changing 
 * deadline and event tasks into floating tasks. It is a child class 
 * of the TagParser class.
 * 
 * @author Annabel
 *
 */
public class FloatParser extends TagParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_FLOAT_COMMAND = "Invalid float Command!";

	// Logger for FloatParser
	private static Logger logger = Logger.getLogger("FloatParser");
	
	/** Initializes a new FloatParser object */
	public FloatParser() {
		super();
		logger.log(Level.INFO, "Initialised FloatParser object");
	}

	/**
	 * Parses userInput and returns an EditToFloatCommand object
	 * 
	 * @param input
	 * 		  The float or EditToFloat command input from the user
	 * 
	 * @return an EditToFloatCommand object if the taskIds are valid
	 * 		   or an InvalidCommand object if the taskIds are invalid.
	 */
	public Command getCommand(String input) throws IncorrectInputException {
		logger.log(Level.INFO, "Getting command object from FloatParser");
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
			// Set command attribute to a EditToFloatCommand object 
			// if it hasn't been set set to an InvalidCommand object.
			setCorrectFloatCommand(getSingleMultipleTagType());
		}
		return command;
	}
	
	/**
	 * Sets command attribute to a EditToFloatCommand object or to
	 * an InvalidCommand object.
	 */
	private void setCorrectFloatCommand(TagType tagType) {
		switch (tagType) {
		case SINGLE :
			int taskId = getSingleTaskId();
			command = CommandUtils.createEditCommandToFloat(taskId);
			break;
			
		case MULTIPLE :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_TOO_MANY_IDS);
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_FLOAT_COMMAND);
			break;
		}
	}
}
