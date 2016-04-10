//@@author A0133338J
package dooyit.parser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.common.utils.CommandUtils;

/**
 * TagParser class takes in a string of task IDs and returns an array 
 * list of task IDs. It is a parent class of DeleteParser, MarkParser, 
 * MoveParser, UnmarkParser and UnmoveParser. It implements the 
 * ParserCommons interface to use the shared constants.
 * 
 * @author Annabel
 *
 */
public class TagParser implements ParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_TASK_ID = "Invalid Task ID: ";
	protected static final String MARKER_FOR_INTERVAL_TAG_TYPE = "-";
	protected static final String ERROR_MESSAGE_TOO_MANY_IDS = "Please type in ONE task ID";
	
	// TagParser Class Constants
	private static final int INDEX_OF_SINGLE_ID = 0;
	private static final int SIZE_ONE_ELEMENT = 1;
	private static final int MAXIMUM_NUMBER_OF_NUMBERS_IN_INTERVAL_TYPE = 2;
	private static final int INDEX_OF_START_IN_INTERVAL_TYPE = 0;
	private static final int INDEX_OF_END_IN_INTERVAL_TYPE = 1;
	
	// TagParser object attributes
	protected String userInput;
	protected String[] splitInput;
	protected ArrayList<Integer> taskIdsForTagging;
	protected Command command;
	
	// Logger for TagParser
	private static Logger logger = Logger.getLogger("TagParser");
	
	// Types of Task ID inputs
	protected enum TagType {
		VALID, INVALID, SINGLE, MULTIPLE
	}

	/** Initializes a new TagParser object */
	public TagParser() {
		logger.log(Level.INFO, "Initialised TagParser object");
	}
	
	/** 
	 * Parses the task IDs and puts the integers into the 
	 * taskIdsForTagging arraylist.
	 * 
	 * @throws IncorrectInputException if there is a non-integer
	 * 		   taskID or if there is IntegerOverflow.
	 */
	protected void parseTaskIds() throws IncorrectInputException {
		for (int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			try {
				addTaskIds(currWord);
			} catch (IncorrectInputException e) {
				throw e;
			}
		}
	}

	/**
	 * 
	 * @param currWord
	 * 		  The taskID or interval of taskIDs 
	 * 
	 * @throws IncorrectInputException if there is a non-integer
	 * 		   taskID or if there is IntegerOverflow.
	 */
	private void addTaskIds(String currWord) throws IncorrectInputException {
		if (ParserCommons.isNumber(currWord)) {
			// Check for Integer Overflow
			try {
				int taggedId = Integer.parseInt(currWord);
				taskIdsForTagging.add(taggedId);
			} catch (NumberFormatException e) {
				throw new IncorrectInputException(ERROR_MESSAGE_INTEGER_OVERFLOW);
			}
			
		} else if (isIntervalType(currWord)) {
			setInterval(currWord);
			
		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_TASK_ID + currWord);
		}
	}
	
	/**
	 * Check if the current string is a valid interval of taskIDs
	 * 
	 * @param currWord
	 * 		  A one word string from the user input
	 * 
	 * @return true if the word contains the interval marker and also
	 * 		   if the start and end are both numbers.
	 */
	protected static boolean isIntervalType(String currWord) {
		boolean isInterval = false;
		if (currWord.contains(MARKER_FOR_INTERVAL_TAG_TYPE)) {
			String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
			isInterval = checkIfStartAndEndAreNumbers(splitByDash);
		}
		return isInterval;
	}

	/**
	 * Checks if there is a correct number of elements in the String array.
	 * If the number of elements is correct, checks if the start and end
	 * strings are valid numbers.
	 * 
	 * @param splitByDash
	 * 		  String array of one of the words from the
	 * 		  user input that contains the interval marker
	 * 
	 * @return true if the number of elements in the String array is correct and 
	 * 		   also if the words are valid numbers.
	 */
	private static boolean checkIfStartAndEndAreNumbers(String[] splitByDash) {
		boolean ans = false;
		if (splitByDash.length == MAXIMUM_NUMBER_OF_NUMBERS_IN_INTERVAL_TYPE) {
			String start = splitByDash[INDEX_OF_START_IN_INTERVAL_TYPE];
			String end = splitByDash[INDEX_OF_END_IN_INTERVAL_TYPE];
			ans = ParserCommons.isNumber(start) && ParserCommons.isNumber(end);
		}
		return ans;
	}

	/**
	 * Sets the object attributes.
	 * 
	 * @param input
	 * 		  This is the user input that contains just the task IDs
	 */
	protected void setAttributesForTagging(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsForTagging = new ArrayList<Integer>();
		command = null;
	}

	/**
	 * Adds the task IDs to the ArrayList taskIdsForTagging
	 * 
	 * @param currWord
	 * 		  A word in the user input that is of a similar 
	 * 		  format "4-7".
	 * 			
	 */
	protected void setInterval(String currWord) throws IncorrectInputException {
		String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
		
		// Check for Integer overflow
		try {
			int start = Integer.parseInt(splitByDash[INDEX_OF_START_IN_INTERVAL_TYPE]); 
			int end = Integer.parseInt(splitByDash[INDEX_OF_END_IN_INTERVAL_TYPE]);
			for (int i = start; i <= end; i++) {
				taskIdsForTagging.add(i);
			}
		} catch (NumberFormatException e) {
			throw new IncorrectInputException(ERROR_MESSAGE_INTEGER_OVERFLOW);
		}
	}

	/**
	 * Checks if the user input has valid task IDs and returns 
	 * the correct TagType.
	 * 
	 * @return the TagType VALID if the ArrayList taskIdsForTagging has at 
	 * 		   least one element and the TagType INVALID otherwise.
	 */
	protected TagType getTagType() {
		TagType type;
		if (taskIdsForTagging.size() >= SIZE_ONE_ELEMENT) {
			type = TagType.VALID;
		} else {
			type = TagType.INVALID;
		}
		return type;
	}
	
	/**
	 * Checks if the user input has one task ID or more and 
	 * returns the correct TagType.
	 * 
	 * @return the SINGLE TagType if the ArrayList only has one element
	 * 		   or the MULTIPLE TagType if the ArrayList has more than one
	 * 		   element or the INVALID TagType the ArrayList does not 
	 * 		   contain any elements.
	 */
	protected TagType getSingleMultipleTagType() {
		TagType type;
		if (taskIdsForTagging.size() == SIZE_ONE_ELEMENT) {
			type = TagType.SINGLE;
		} else if (taskIdsForTagging.size() > SIZE_ONE_ELEMENT) {
			type = TagType.MULTIPLE;
		} else {
			type = TagType.INVALID;
		}
		return type;
	}
	
	/**
	 * Resets the command attribute to null.
	 */
	protected void resetCommandAttributeToNull() {
		command = null;
	}
	
	/**
	 * Gets the single task ID from the arraylist of task IDs.
	 * 
	 * @return the single task ID.
	 */
	protected int getSingleTaskId() {
		return taskIdsForTagging.get(INDEX_OF_SINGLE_ID);
	}
	
	/**
	 * Sets the command attribute to an InvalidCommand object.
	 * 
	 * @param message
	 * 		  Error message that indicates why the user input is wrong
	 */
	protected void setToInvalidCommand(String message) {
		command = CommandUtils.createInvalidCommand(message);
	}
}
