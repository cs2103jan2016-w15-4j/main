//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;
import dooyit.logic.commands.Command;

/**
 * AddEditTaskParser is the parent class of Edit and Add parser. It has
 * the methods and constants that are used by both the AddParser and 
 * EditParser classes. 
 * 
 * @author Annabel
 *
 */
public class AddEditTaskParser implements ParserCommons {
	// Markers for event and deadline tasks
	public static final String MARKER_START_EVENT = " from ";
	public static final String MARKER_END_EVENT = " to ";
	public static final String MARKER_DEADLINE_TASK = " by ";
	
	// Logger for AddEditParser
	private static Logger logger = Logger.getLogger("AddEditParser");
	
	// AddEditTaskParser object attributes
	public String userInput;
	public String taskName;
	public DateTime start;
	public DateTime end;
	public DateTime deadline;
	public Command command;
	
	/** Initializes a new AddEditTaskParser object */
	public AddEditTaskParser() {
		logger.log(Level.INFO, "Initialised AddEditTaskParser object");
	}
	
	/**
	 * Resets the object attributes to the uninitialized values.
	 */
	public void resetAttributes() {
		start = null;
		end = null;
		deadline = null;
		command = null;
		taskName = null;
		userInput = null;
	}
	
	/**
	 * Sets the userInput attribute to the string input.
	 * 
	 * @param input
	 * 		  The add command input from the user
	 */
	public void setUserInput(String input) {
		userInput = input.trim();
	}
	
	/**
	 * Gets the End date time string of an event user input
	 * 
	 * @param userInput
	 * 		  The event string input from the user
	 * 
	 * @param indexFrom
	 * 		  The integer index position of the word "from"
	 * 
	 * @param indexTo
	 * 		  The integer index position of the word "to"
	 * 
	 * @return the string containing the end DateTime
	 */
	public String getEndDateTimeString(int indexFrom, int indexTo) {
		String endTimeString;
		if (indexFrom > indexTo) {
			// For the case where user keys in "To" before "From"
			endTimeString = userInput.substring(indexTo, indexFrom).replace(MARKER_END_EVENT, EMPTY_STRING).trim();
		} else {
			// For the case where the user keys in "From" before "To"
			endTimeString = userInput.substring(indexTo).replace(MARKER_END_EVENT, EMPTY_STRING).trim();
		}
		return endTimeString;
	}

	/**
	 * Gets the Start date time string of an event user input
	 * 
	 * @param userInput
	 * 		  The event string input from the user
	 * 
	 * @param indexFrom
	 * 		  The integer index position of the word "from"
	 * 
	 * @param indexTo
	 * 		  The integer index position of the word "to"
	 * 
	 * @return the string containing the end DateTime
	 */
	public String getStartDateTimeString(int indexFrom, int indexTo) {
		String startTimeString;
		if (indexFrom > indexTo) {
			// For the case where user keys in "To" before "From"
			startTimeString = userInput.substring(indexFrom).replace(MARKER_START_EVENT, EMPTY_STRING).trim();
		} else {
			// For the case where the user keys in "From" before "To"
			startTimeString = userInput.substring(indexFrom, indexTo).replace(MARKER_START_EVENT, EMPTY_STRING).trim();
		}
		return startTimeString;
	}
	

	/**
	 * Gets the task name from an event user input
	 * 
	 * @param userInput
	 * 		  The event string input from the user
	 * 
	 * @param indexFrom
	 * 		  The integer index position of the word "from"
	 * 
	 * @param indexTo
	 * 		  The integer index position of the word "to"
	 * 
	 * @return the string containing the end DateTime
	 */
	public String getTaskName(int indexFrom, int indexTo) {
		String name;
		if (indexFrom < indexTo) {
			// For the case where user keys in "To" before "From"
			name = userInput.substring(0, indexFrom);
		} else {
			// For the case where the user keys in "From" before "To"
			name = userInput.substring(0, indexTo);
		}
		return name;
	}
	
	/**
	 * Checks if the userInput is an event task by checking if input has 
	 * BOTH the event markers "from" and "to" and checking if the words 
	 * after the respective markers are valid DateTime inputs.
	 * 
	 * @return true if input is an event task and false if it isn't
	 */
	public boolean isEvent() {
		boolean ans = false;
		if (ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_START_EVENT))
				&& ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_END_EVENT))) {
			ans = hasAValidDateTimeAfterEventMarkers();
		}
		return ans;
	}

	/**
	 * Checks if the string inputs after the event markers "from" and "to" are valid
	 * DateTime inputs.
	 * 
	 * @return true if both strings are valid DateTime inputs and false otherwise.
	 */
	public boolean hasAValidDateTimeAfterEventMarkers() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		
		// Get the position indices of the event markers
		int indexFrom = userInput.lastIndexOf(MARKER_START_EVENT);
		int indexTo = userInput.lastIndexOf(MARKER_END_EVENT);
		
		// Get the start and end strings inputs 
		boolean ans = true;
		String startDateTimeString = getStartDateTimeString(indexFrom, indexTo);
		String endDateTimeString = getEndDateTimeString(indexFrom, indexTo);

		// Check if the start and end strings are valid DateTime inputs
		try {
			dateTimeParser.parse(startDateTimeString);
			dateTimeParser.parse(endDateTimeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}

	/**
	 * Checks if the userInput is a deadline task by checking if the input
	 * has the deadline marker "by" and checking if the words after the marker
	 * "by" are valid DateTime inputs.
	 * 
	 * @return true if the input is a deadline task and false otherwise.
	 */
	public boolean isDeadlineTask() {
		boolean ans = false;
		
		// Checks if the deadline input is a valid DateTime input
		if (ParserCommons.isInitialized(userInput.lastIndexOf(MARKER_DEADLINE_TASK))) {
			ans = hasAValidDateTimeAfterDeadlineMarker();
		}
		return ans;
	}

	/**
	 * Checks if the string input after the Deadline marker "by" is a valid
	 * DateTime input.
	 * 
	 * @return true if the string is a valid DateTime input and false otherwise.
	 */
	public boolean hasAValidDateTimeAfterDeadlineMarker() {
		DateTimeParser dateTimeParser = new DateTimeParser();
		int indexBy = userInput.lastIndexOf(MARKER_DEADLINE_TASK);
		String timeString = userInput.substring(indexBy).replace(MARKER_DEADLINE_TASK, EMPTY_STRING).trim();
		boolean ans = true;
		
		// Check if timeString is a valid DateTime input
		try {
			dateTimeParser.parse(timeString);
		} catch (IncorrectInputException e) {
			ans = false;
		}
		return ans;
	}
}
