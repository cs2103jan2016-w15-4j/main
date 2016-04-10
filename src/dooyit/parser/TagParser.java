//@@author A0133338J
package dooyit.parser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;
import dooyit.common.utils.CommandUtils;

/**
 * 
 * @author Annabel
 *
 */
public class TagParser implements ParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_TASK_ID = "Invalid Task ID: ";
	protected static final String MARKER_FOR_INTERVAL_TAG_TYPE = "-";
	protected static final String ERROR_MESSAGE_TOO_MANY_IDS = "Please type in ONE task ID";
	
	// Class Constants
	private static final int INDEX_OF_SINGLE_ID = 0;
	private static final int SIZE_ONE = 1;
	
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
	 * 
	 * @throws IncorrectInputException
	 */
	protected void parseTaskIds() throws IncorrectInputException {
		for (int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			if (ParserCommons.isNumber(currWord)) {
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
	}
	
	/**
	 * 
	 * @param currWord
	 * @return
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
	 * 
	 * @param splitByDash
	 * @return
	 */
	private static boolean checkIfStartAndEndAreNumbers(String[] splitByDash) {
		boolean ans = false;
		if (splitByDash.length == 2) {
			String start = splitByDash[0];
			String end = splitByDash[1];
			ans = ParserCommons.isNumber(start) && ParserCommons.isNumber(end);
		}
		return ans;
	}

	/**
	 * 
	 * @param input
	 */
	protected void setAttributesForTagging(String input) {
		userInput = input;
		splitInput = userInput.split("\\s+");
		taskIdsForTagging = new ArrayList<Integer>();
		command = null;
	}

	/**
	 * 
	 * @param currWord
	 */
	protected void setInterval(String currWord) throws IncorrectInputException {
		String[] splitByDash = currWord.split(MARKER_FOR_INTERVAL_TAG_TYPE);
		try {
			int start = Integer.parseInt(splitByDash[0]); 
			int end = Integer.parseInt(splitByDash[1]);
			for (int i = start; i <= end; i++) {
				taskIdsForTagging.add(i);
			}
		} catch (NumberFormatException e) {
			throw new IncorrectInputException(ERROR_MESSAGE_INTEGER_OVERFLOW);
		}
	}

	/**
	 * 
	 * @return
	 */
	protected TagType getTagType() {
		TagType type;
		if (taskIdsForTagging.size() >= 1) {
			type = TagType.VALID;
		} else {
			type = TagType.INVALID;
		}
		return type;
	}
	
	/**
	 * 
	 * @return
	 */
	protected TagType getSingleMultipleTagType() {
		TagType type;
		if (taskIdsForTagging.size() == SIZE_ONE) {
			type = TagType.SINGLE;
		} else if (taskIdsForTagging.size() > SIZE_ONE) {
			type = TagType.MULTIPLE;
		} else {
			type = TagType.INVALID;
		}
		return type;
	}
	
	/**
	 * 
	 */
	protected void resetCommandAttributeToNull() {
		command = null;
	}
	
	/**
	 * 
	 * @return
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
