//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DateTime.Day;
import dooyit.common.datatype.DateTime.Month;
import dooyit.common.exception.IncorrectInputException;
import dooyit.common.utils.CommandUtils;
import dooyit.logic.commands.Command;

/**
 * The SearchParser class takes in a "search" command input and 
 * returns a Command object. It is needed to search for tasks with
 * the specified Day, Month, date or string. It implements the 
 * ParserCommons interface to use the shared constants.
 * 
 * @author Annabel
 *
 */
public class SearchParser implements ParserCommons {
	// Error message
	private static final String ERROR_MESSAGE_EMPTY_SEARCH_COMMAND = "Empty search command!";
	
	// SearchParser object attributes
	Command command;
	String userInput;
	boolean isValidDate, isValidDay, isValidMonth, isEmptyString;
	DateTime dateTime;
	Day dayEnum;
	Month monthEnum;
	
	// Logger for SearchParser
	private static Logger logger = Logger.getLogger("SearchParser");
	
	// Possible types of searches
	private enum SearchType {
		INVALID, DATE, DAY, MONTH, WHOLE_STRING
	}
	
	/** Initializes a new SearchParser object */
	public SearchParser() {
		logger.log(Level.INFO, "Initialised SearchParser object");
	}
	
	/**
	 * Parses userInput and returns a SearchCommand object
	 * 
	 * @param input
	 * 		  The search command input from the user
	 * 
	 * @return the correct SearchCommand object if the userInput is not an 
	 * 		   empty string or an InvalidCommand object if it is.
	 */
	public Command getCommand(String input) {
		resetFields(input);
		setBooleanValues();
		setCorrectSearchCommand();
		return command;
	}

	/**
	 * Checks the userInput and sets the object's boolean 
	 * attributes accordingly.
	 */
	private void setBooleanValues() {
		// Set isValidDate boolean value
		try {
			DateTimeParser dtParser = new DateTimeParser();
			dateTime = dtParser.parse(userInput);
		} catch (IncorrectInputException e) {
			isValidDate = false;
		}
		
		// Set isValidDay boolean value
		dayEnum = DateTime.getDayType(userInput);
		isValidDay = dayEnum != Day.INVALID;
		
		// Set isValidMonth boolean value
		monthEnum = DateTime.getMonthType(userInput);
		isValidMonth = monthEnum != Month.INVALID;
		
		// Check if userUnput is an empty string
		isEmptyString = userInput.equals(EMPTY_STRING);
	}

	/**
	 * Resets all the attributes.
	 */
	private void resetFields(String input) {
		userInput = input.toLowerCase();
		isValidDate = true;
		isValidDay = false;
		isValidMonth = false;
		isEmptyString = false;
		command = null;
	}

	/**
	 * Sets the command attribute to an InvalidCommand object if the 
	 * userInput is an empty string or the correct SearchCommand object 
	 * if the userInput is not an empty string.
	 */
	private void setCorrectSearchCommand() {
		switch (getCorrectSearchType()) {
		case INVALID :
			command = CommandUtils.createInvalidCommand(ERROR_MESSAGE_EMPTY_SEARCH_COMMAND);
			break;
		
		case DAY :
			command = CommandUtils.createSearchCommand(userInput, dayEnum);
			break;
		
		case MONTH :
			command = CommandUtils.createSearchCommand(userInput, monthEnum);
			break;
			
		case DATE :
			command = CommandUtils.createSearchCommand(dateTime);
			break;
			
		default :
			command = CommandUtils.createSearchCommand(userInput);
			break;
		}
	}
	
	/**
	 * Checks the object's boolean values and returns the 
	 * correct SearchType.
	 */
	private SearchType getCorrectSearchType() {
		SearchType type;
		if (isEmptyString) {
			type = SearchType.INVALID;
		} else if (isValidDay) {
			type = SearchType.DAY;
		} else if (isValidMonth) {
			type = SearchType.MONTH;
		} else if (isValidDate) {
			type = SearchType.DATE;
		} else {
			type = SearchType.WHOLE_STRING;
		}
		return type;
	}
}
