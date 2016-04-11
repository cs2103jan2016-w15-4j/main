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
	
	// For splitting a String into a 2-element String array
	public static final int SPLIT_INPUT_INTO_TWO_PARTS = 2;
	
	// To get substring from the start of a string
	public static final int START_OF_STRING = 0;
	
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
