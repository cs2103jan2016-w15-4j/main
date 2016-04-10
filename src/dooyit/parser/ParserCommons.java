//@@author A0133338J
package dooyit.parser;

/**
 * ParserCommons interface contains all the constants and methods that
 * are shared by two or more parser classes.
 * 
 * @author Annabel
 *
 */
public interface ParserCommons {
	// Error message
	public static final String ERROR_MESSAGE_END_BEFORE_START = "End timing cannot be before Start timing";
	public static final String ERROR_MESSAGE_INTEGER_OVERFLOW = "Do you even need to sleep with that many things to do?";
	
	// Uninitialized int and String constants
	public static final int UNINITIALIZED_INT = -1;
	public static final String UNINITIALIZED_STRING = "-1";
	
	// Constant for DateTime comparison
	public static final int BEFORE = -1;
	
	// Constant for replacing words in a string
	public static final String EMPTY_STRING = "";
	
	// Markers for event and deadline tasks
	public static final String MARKER_START_EVENT = " from ";
	public static final String MARKER_END_EVENT = " to ";
	public static final String MARKER_DEADLINE_TASK = " by ";
	
	/**
	 * Checks if the input is a valid number
	 * 
	 * @param currWord
	 * 		  A string input from the user
	 * 
	 * @return true if currWord is a valid number, and false
	 * 		   otherwise.
	 */
	static boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
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
	static String getEndDateTimeString(String userInput, int indexFrom, int indexTo) {
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
	static String getStartDateTimeString(String userInput, int indexFrom, int indexTo) {
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
	static String getTaskName(String userInput, int indexFrom, int indexTo) {
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
	 * Checks if the input has been initialized.
	 * 
	 * @param number
	 * 		  Integer that was converted from a one word String 
	 * 		  from the user input.
	 * 
	 * @return true if the number is not the uninitialized int constant.
	 */
	static boolean isInitialized(int number) {
		return number != UNINITIALIZED_INT;
	}

	/**
	 * Checks if the integer in the specified position of the array 
	 * has been initialized.
	 * 
	 * @param array
	 * 		  Integer array
	 * 
	 * @param index
	 * 		  Array position of the integer to be checked.
	 * 
	 * @return true if the number is not the uninitialized int constant.
	 */
	static boolean isInitialized(int[] array, int index) {
		return array[index] != UNINITIALIZED_INT;
	}
}
