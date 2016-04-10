//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The ShowParser class takes in a "show" command input and 
 * returns a Command object. It is needed to switch between the 
 * standard views on the Dooyit application.
 * 
 * @author Annabel
 *
 */
public class ShowParser {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_SHOW_COMMAND = "Invalid show command";
	
	// Types of views
	private static final String VIEW_TODAY = "today";
	private static final String VIEW_NEXT_SEVEN = "next7";
	private static final String VIEW_DONE = "done";
	private static final String VIEW_ALL = "all";
	private static final String VIEW_COMPLETED = "completed";
	private static final String VIEW_FLOAT = "float";
	
	// ShowParser object attributes
	private String userInput;
	private Command command;
	
	// Logger for ShowParser
	private static Logger logger = Logger.getLogger("ShowParser");
	
	/** Initializes a new ShowParser object */
	public ShowParser() {
		logger.log(Level.INFO, "Initialised ShowParser object");
	}

	/**
	 * Parses userInput and returns a ShowCommand object
	 * 
	 * @param input
	 * 		  The show command input from the user
	 * 
	 * @return the correct ShowCommand object if the input is a valid view
	 * 		   or an InvalidCommand object if the input is an invalid view.
	 */
	public Command getCommand(String input) { 
		logger.log(Level.INFO, "Getting command object from ShowParser");
		setObjectAttributes(input);
		setCommandAttribute();
		return command;
	}

	/**
	 * Sets the command attribute to a ShowCommand object or to
	 * an InvalidCommand object.
	 */
	private void setCommandAttribute() {
		switch (userInput) { 
		case VIEW_TODAY :
			command = CommandUtils.createShowTodayCommand();
			break;

		case VIEW_NEXT_SEVEN :
			command = CommandUtils.createShowNext7DaysCommand();
			break;

		case VIEW_DONE :
			command = CommandUtils.createShowCompletedCommand();
			break;

		case VIEW_ALL :
			command = CommandUtils.createShowAllCommand();
			break;
			
		case VIEW_FLOAT :
			command = CommandUtils.createShowFloatCommand();
			break;

		case VIEW_COMPLETED :
			command = CommandUtils.createShowCompletedCommand();
			break;

		default :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_INVALID_SHOW_COMMAND);
			break;
		}
	}

	/**
	 * Resets object attributes for the new input string that the 
	 * ShowParser object is supposed to parse.
	 * 
	 * @param input
	 * 		  The show command input from the user
	 */
	private void setObjectAttributes(String input) {
		userInput = input.toLowerCase();
		command = null;
	}
}
