//@@author A0133338J 
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.logic.commands.Command;
import dooyit.logic.commands.CommandUtils;

/**
 * The AddCategoryParser class provides methods needed for adding a category and
 * specifying the category colour. It takes in an "addcat" command input and
 * returns an AddCategory command object. It implements the ParserCommons
 * interface to use the shared constant EMPTY_STRING.
 * 
 * @author Annabel
 *
 */
public class AddCategoryParser implements ParserCommons {
	// Error Messages
	private static final String ERROR_MESSAGE_INVALID_ADDCAT_COMMAND = "Invalid addcat command!";
	private static final String ERROR_MESSAGE_TOO_MANY_WORDS = "Category name can only be one word!";

	// Index of Category Name and Colour in a userInput String array
	private static final int INDEX_NAME = 0;
	private static final int INDEX_COLOUR = 1;

	// Constant of the maximum number of words
	// allowed in an AddCategory command input
	private static final int MAXIMUM_NUMBER_OF_WORDS = 2;

	// Logger for AddCategoryParser
	private static Logger logger = Logger.getLogger("AddCategoryParser");

	// Attributes of an AddCategoryParser object
	private String userInput;
	private String categoryName;
	private String categoryColour;
	private Command command;
	private boolean hasColour;

	// Types of addcat commands
	private enum ADD_CATEGORY_TYPE {
		VALID, INVALID_TOO_MANY_WORDS, INVALID_EMPTY_STRING
	};

	/** Initializes a new AddCategoryParser object */
	public AddCategoryParser() {
		logger.log(Level.INFO, "Initialised AddCategoryParser object");
	}

	/**
	 * Parses userInput and returns correct AddCategory command object
	 * 
	 * @param input
	 *        The addcat command input from the user
	 * 
	 * @return the correct AddCategory command object if the command input is
	 *         valid or an invalid command object if the command input is
	 *         invalid
	 */
	public Command getCommand(String input) {
		logger.log(Level.INFO, "Getting command object from AddCategoryParser");

		// Resets object attributes for each call of the getCommand method
		resetAttributes(input);

		// Sets the categoryName and categoryColour attributes
		parse();

		// Sets the command attribute
		setAddCategoryCommand();
		return command;
	}

	/**
	 * Sets the command attribute to either an AddCategory command object or an
	 * InvalidCommand object
	 */
	private void setAddCategoryCommand() {
		switch (getCommandType()) {
		case VALID:
			setCreateCategoryCommand();
			break;

		case INVALID_TOO_MANY_WORDS:
			setInvalidCommand(ERROR_MESSAGE_TOO_MANY_WORDS);
			break;

		default:
			setInvalidCommand(ERROR_MESSAGE_INVALID_ADDCAT_COMMAND);
			break;
		}
	}

	/**
	 * Checks the number of words in the userInput attribute to determine if the
	 * add category command input is valid
	 * 
	 * @return the correct ADD_CATEGORY_TYPE enum constant
	 */
	private ADD_CATEGORY_TYPE getCommandType() {
		ADD_CATEGORY_TYPE type;
		if (userInput.equals(EMPTY_STRING)) {
			type = ADD_CATEGORY_TYPE.INVALID_EMPTY_STRING;
			
		} else if (userInputHasTooManyWords()) {
			type = ADD_CATEGORY_TYPE.INVALID_TOO_MANY_WORDS;
			
		} else {
			type = ADD_CATEGORY_TYPE.VALID;
		}
		return type;
	}

	/**
	 * Sets the command attribute to an InvalidCommand object.
	 * 
	 * @param errorMessage
	 *        Error message String constants defined in this class
	 */
	private void setInvalidCommand(String errorMessage) {
		command = CommandUtils.createInvalidCommand(errorMessage);
	}

	/**
	 * Sets the command attribute to the correct AddCategoryCommand object.
	 */
	private void setCreateCategoryCommand() {
		if (hasColour) {
			command = CommandUtils.createAddCategoryCommand(categoryName, categoryColour);
		} else {
			command = CommandUtils.createAddCategoryCommand(categoryName);
		}
	}

	/**
	 * Parses the userInput to set the categoryName and categoryColour
	 * attributes. Sets the hasColour boolean attribute to true if the user
	 * specified a colour.
	 */
	private void parse() {
		String[] inputArr = userInput.split("\\s+");
		categoryName = inputArr[INDEX_NAME];
		if (!isOneWordUserInput()) {
			categoryColour = inputArr[INDEX_COLOUR];
			hasColour = true;
		}
	}

	/**
	 * Sets the userInput attribute to the method parameter. Resets the
	 * hasColour and categoryColour object attributes.
	 * 
	 * @param input
	 *        The addcat command input from the user
	 */
	private void resetAttributes(String input) {
		userInput = input;
		categoryColour = EMPTY_STRING;
		hasColour = false;
	}

	/**
	 * Checks if the userInput attribute has more than the maximum number of
	 * words allowed for a AddCategory command input.
	 * 
	 * @return true if the userInput exceeds the maximum number of words and
	 *         false if the userInput does not exceed the maximum number of
	 *         words allowed.
	 */
	private boolean userInputHasTooManyWords() {
		String[] splitInput = userInput.split("\\s+");
		return splitInput.length > MAXIMUM_NUMBER_OF_WORDS;
	}

	/**
	 * Checks if the userInput is exactly one word.
	 * 
	 * @return true if the input if one word and false if it is less than or
	 *         more than one word.
	 */
	private boolean isOneWordUserInput() {
		String[] inputArr = userInput.split("\\s+");
		return inputArr.length == 1;
	}
}
