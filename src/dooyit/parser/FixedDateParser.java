//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

/**
 * FixedDateParser takes in a word input and returns an integer array [DD, MM, YY] that indicates
 * the date. It parses fixed date user inputs like "18/2/2016" or "18 May 2016".
 * It implements DateTimeparserCommons and ParserCommons to use the shared methods and constants.
 * 
 * @author Annabel
 *
 */
public class FixedDateParser implements DateTimeParserCommons, ParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_NUMBER_OF_DATE_INPUTS = "Invalid number of date inputs";
	private static final String ERROR_MESSAGE_DATE_INPUTS_MUST_BE_NUMBERS = "Date inputs must be numbers";
	private static final String ERROR_MESSAGE_INVALID_DATE = "Invalid date!";
	private static final String ERROR_MESSAGE_DATE_INPUTS_MUST_GREATER_THAN_ZERO = "Date Inputs must be greater than 0!";
	
	// Maximum number of words in dates of the format "2 Feb 2016"
	private static final int MAXIMUM_NUMBER_OF_WORDS_IN_WORD_DATE = 2;
	
	// Constant of the minimum value for year
	private static final int YEARY_2000 = 2000;
	
	// Index of the array index in the dateWithArrayIndex array
	private static final int DATE_INDEX_OF_ARRAY_INDEX = 3;
	
	// For user inputs with just the month, the DD will be set to 15
	private static final int DEFAULT_DD_IN_MONTH = 15;
	
	// Indicator for date strings of the format 12/3/2016
	private static final String DATE_SEPARATOR = "/";
	
	// Number of numeric values in number dates of the format "12/3/2016"
	private static final int NUMBER_OF_VALUES_IN_NUMBER_DATE_INVALID = 1;
	private static final int NUMBER_OF_VALUES_IN_NUMBER_DATE_MAXIMUM = 3;

	// Logger for FixedDateParser
	private static Logger logger = Logger.getLogger("FixedDateParser");
	
	// DateTime object attributes
	private int currMM;
	private int currYY;
	private int currDD;

	/**
	 * Initializes a FixedDateParser object
	 * 
	 * @param dateTime
	 * 		  DateTime object with specified date and time.
	 */
	public FixedDateParser(DateTime dateTime) {
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		logger.log(Level.INFO, "Initialised FixedDateParser object");
	}

	/**
	 * Checks if the user input string array has a fixed date string.
	 * 
	 * @param currWord
	 * 		  The word of the current word in the user input string array
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the fixed date string is present in the user input and false otherwise.
	 */
	public boolean isFixedDate(String currWord, String[] splitUserInput, int index) {
		return isValidDate(currWord, splitUserInput, index);
	}

	/**
	 * Sets the fields of DD, MM and YY in the combined int array to the correct values based on
	 * the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @return the combined int array with the updated values.
	 * 
	 * @throws IncorrectInputException if the user input has an invalid fixed date string.
	 */
	public int[] parse(String[] splitUserInput, int[] combined, int index) throws IncorrectInputException {
		try {
			combined = getDate(splitUserInput, index, combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return combined;
	}

	/**
	 * Calculates the new updated array index.
	 * 
	 * @param newDate
	 * 		  The int array with DD, MM, YY and ArrayIndex fields.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return the new array index for the combined int array.
	 */
	private int getArrayIndex(int[] newDate, int index) {
		int ans = newDate[DATE_INDEX_OF_ARRAY_INDEX] + index;
		return ans;
	}

	/**
	 * Get the day of week in int form from a date. For example the int array
	 * that represents the date "11 April 2016" will return 1 which represents Monday.
	 * 
	 * @param date
	 * 		  The int array representing a date. Contains fields DD, MM, YY.
	 * 
	 * @return the day of the week that the date falls on in int form.
	 */
	private int getDayOfWeekFromADate(int[] date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayInt();
	}

	/**
	 * Converts the user input string array into a dateWithArrayIndex int array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return the dateWithArrayIndex with updated values.
	 * 
	 * @throws IncorrectInputException if the date in the dateWithArrayIndex int array
	 * 		   is an invalid date.
	 */
	private int[] getdateWithArrayIndex(String[] splitUserInput, int index) throws IncorrectInputException {
		String firstWord = splitUserInput[index];
		int[] dateWithArrayIndex = new int[] { UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT,
				UNINITIALIZED_INT };
		try {
			if (firstWord.contains(DATE_SEPARATOR)) {
				String[] userInputForDate = firstWord.split(DATE_SEPARATOR);
				dateWithArrayIndex = getNumberDate(dateWithArrayIndex, userInputForDate);
			} else {
				dateWithArrayIndex = getWordDate(dateWithArrayIndex, index, splitUserInput);
			}
		} catch (IncorrectInputException e) {
			throw e;
		}

		dateWithArrayIndex = setUninitializedValuesToDefault(dateWithArrayIndex);
		if (isInvalidDate(dateWithArrayIndex)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
		}

		return dateWithArrayIndex;
	}

	/**
	 * Converts the user input string array into the dateWithArrayIndex int array. This is for
	 * cases where the date is a word date like "2 feb"
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @return dateWithArrayIndex int array with the value updated.
	 * 
	 * @throws IncorrectInputException values in given in the user input are invalid DD or YY values.
	 */
	private int[] getWordDate(int[] dateWithArrayIndex, int index, String[] splitUserInput)
			throws IncorrectInputException {
		int counter = 0;
		for (int j = index; j < splitUserInput.length; j++) {
			if (counter > MAXIMUM_NUMBER_OF_WORDS_IN_WORD_DATE) {
				break;
			} else {
				String currWord = splitUserInput[j];
				try {
					dateWithArrayIndex = setValuesInArray(currWord, dateWithArrayIndex);
				} catch (IncorrectInputException e) {
					throw e;
				}
			}
			counter++;
		}
		return dateWithArrayIndex;
	}

	/**
	 * 	Converts the user input string array into the dateWithArrayIndex int array. This is for
	 * cases where the date is a number date like "2/12/2016"
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @return dateWithArrayIndex int array with the value updated.
	 * 
	 * @throws IncorrectInputException values are not numbers like "2/a/bbbb".
	 */
	private int[] getNumberDate(int[] dateWithArrayIndex, String[] splitUserInput) throws IncorrectInputException {
		if (splitUserInput.length > NUMBER_OF_VALUES_IN_NUMBER_DATE_MAXIMUM
				|| splitUserInput.length == NUMBER_OF_VALUES_IN_NUMBER_DATE_INVALID) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_NUMBER_OF_DATE_INPUTS);
		}
		for (int j = 0; j < splitUserInput.length; j++) {
			if (!ParserCommons.isNumber(splitUserInput[j])) {
				throw new IncorrectInputException(ERROR_MESSAGE_DATE_INPUTS_MUST_BE_NUMBERS);
			}

			dateWithArrayIndex[j] = Integer.parseInt(splitUserInput[j]);

			if (dateWithArrayIndex[j] <= 0) {
				throw new IncorrectInputException(ERROR_MESSAGE_DATE_INPUTS_MUST_GREATER_THAN_ZERO);
			}
		}
		dateWithArrayIndex[DATE_INDEX_OF_ARRAY_INDEX] += 1;
		return dateWithArrayIndex;
	}

	/**
	 * Updates the dateWithArray index array according to the type of value that the currWord is.
	 * For eg if the currWord is a valid year value, the year field will be updated.
	 * 
	 * @param currWord
	 * 		  The word of the current word in the user input string array
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @return dateWithArray int array with the fields set to the correct values
	 * 
	 * @throws IncorrectInputException if the currWord is an invalid date and an invalid time.
	 */
	private int[] setValuesInArray(String currWord, int[] dateWithArrayIndex) throws IncorrectInputException {
		if (ParserCommons.isNumber(currWord)) { // currWord is either DD or YY
			int currInt = Integer.parseInt(currWord);
			try {
				dateWithArrayIndex = setDayAndYearValues(dateWithArrayIndex, currInt);
				dateWithArrayIndex[DATE_INDEX_OF_ARRAY_INDEX] += 1;
			} catch (IncorrectInputException e) {
				throw e;
			}
		} else if (isMonth(currWord) && !ParserCommons.isInitialized(dateWithArrayIndex, DATE_INDEX_OF_MM)) {
			dateWithArrayIndex[DATE_INDEX_OF_MM] = convertMonthStrToInt(currWord);
			dateWithArrayIndex[DATE_INDEX_OF_ARRAY_INDEX] += 1;
		} else {
			if (!DateTimeParserCommons.isValidTime(currWord)) {
				throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
			}
		}

		return dateWithArrayIndex;
	}

	/**
	 * Converts the fixed date string from the split user input string array into the
	 * int array new date that is of the form [dd, mm, yy, arrayIndex]. Then converts the 
	 * new date array into the combined int array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @return the combined int array with the updated DD, MM, YY fields.
	 * 
	 * @throws IncorrectInputException if the user input string array does not have a 
	 * 		   valied fixed date string.
	 */
	private int[] getDate(String[] splitUserInput, int index, int[] combined) throws IncorrectInputException {
		int[] newDate;
		try {
			// int array is of the form [dd, mm, yy, arrayIndex]
			newDate = getdateWithArrayIndex(splitUserInput, index); 
			
		} catch (IncorrectInputException e) {
			throw e;
		}

		if (isInvalidDate(newDate)) {
			throw new IncorrectInputException();
		}

		return DateTimeParserCommons.getNewCombinedArray(combined, newDate, getDayOfWeekFromADate(newDate),
				getArrayIndex(newDate, combined[COMBINED_INDEX_COUNTER]));
	}

	/**
	 * Sets the DD and YY values in the dateWitArrayIndex int array.
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @param currInt
	 * 		  The current int from the user input string.
	 * 		  
	 * @return the int array with the updated values for the DD and YY fields
	 * 
	 * @throws IncorrectInputException if the DD and YY fields have already been initialized or if
	 * 		   the currInt is not a valid YY or DD value.
	 */
	private int[] setDayAndYearValues(int[] dateWithArrayIndex, int currInt) throws IncorrectInputException {
		if (currInt <= NUMBER_OF_DAYS_IN_A_MONTH_MAXIMUM
				&& !ParserCommons.isInitialized(dateWithArrayIndex, DATE_INDEX_OF_DD)) {
			
			dateWithArrayIndex[DATE_INDEX_OF_DD] = currInt;

		} else if (currInt >= YEARY_2000 && !ParserCommons.isInitialized(dateWithArrayIndex, DATE_INDEX_OF_YY)) {
			dateWithArrayIndex[DATE_INDEX_OF_YY] = currInt;

		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
		}
		return dateWithArrayIndex;
	}

	/**
	 * Checks if a YY is uninitialized, if it is, then set the value to this year or next year 
	 * depending on whether the date has passed. If the DD is uninitialized then set the value
	 * to the default value 15.
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @return dateWithArrayIndex array with the uninitialized fields set to the default
	 */
	private int[] setUninitializedValuesToDefault(int[] dateWithArrayIndex) {
		if (!ParserCommons.isInitialized(dateWithArrayIndex, DATE_INDEX_OF_YY)) {
			dateWithArrayIndex[DATE_INDEX_OF_YY] = getCorrectYear(dateWithArrayIndex);
		}

		if (!ParserCommons.isInitialized(dateWithArrayIndex, DATE_INDEX_OF_DD)) {
			dateWithArrayIndex[DATE_INDEX_OF_DD] = DEFAULT_DD_IN_MONTH; 
		}
		return dateWithArrayIndex;
	}

	/**
	 * Checks if the user input date has already passed with relative to the present date. 
	 * If it has passed then return the next year and it hasn't passed then return the
	 * present year. This is to parse user inputs like "4/2" and "4 Feb" where the year
	 * is not specified.
	 * 
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @return this year if date hasn't passed and next year if it has passed.
	 */
	private int getCorrectYear(int[] dateWithArrayIndex) {
		int newDD = dateWithArrayIndex[DATE_INDEX_OF_DD];
		int newMM = dateWithArrayIndex[DATE_INDEX_OF_MM];
		int newYY = UNINITIALIZED_INT;
		DateTime newDateTime = new DateTime(new int[] { newDD, newMM, currYY });
		DateTime currDateTime = new DateTime(new int[] { currDD, currMM, currYY });
		
		// Check if date has passed
		if (newDateTime.compareTo(currDateTime) == UNINITIALIZED_INT) { 
			// Increase the year by one
			newYY = currYY + 1; 						
		} else {
			// Date has not passed
			// Set the year to current year
			newYY = currYY; 
		}
		return newYY;
	}

	/**
	 *  Checks that the number of months does not exceed the max number of days 
	 *  in that month. For example "30 Feb" and "100 Mar" will return false.
	 *  
	 * @param dateWithArrayIndex
	 * 		  The int array containing the date in DD, MM, YY format and also the index of the
	 * 		  current word in the array.
	 * 
	 * @return true if the date is invalid and false otherwise.
	 */
	private static boolean isInvalidDate(int[] dateWithArrayIndex) {
		int[] daysInMonth;
		int mm = dateWithArrayIndex[DATE_INDEX_OF_MM];

		boolean output = false;
		if (mm > NUMBER_OF_MONTHS_IN_A_YEAR) {
			output = true;
		} else {
			int yy = dateWithArrayIndex[DATE_INDEX_OF_YY];
			daysInMonth = DateTimeParserCommons.getDaysInMonthArray(yy);
			output = dateWithArrayIndex[DATE_INDEX_OF_DD] > daysInMonth[mm];
		}
		return output;
	}

	/**
	 * Checks if there is a valid fixed date string in the user input.
	 * 
	 * @param currWord
	 * 		  The current word in the user input string.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if there is a valid fixed date string and false otherwise.
	 */
	private boolean isValidDate(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (currWord.contains(DATE_SEPARATOR)) {
			 // Date is of the format 11/2/2016
			ans = true;
		} else {
			ans = checkIfInputIsAValidWordDate(splitUserInput, index);
		}
		return ans;
	}

	/**
	 * Checks if the user input has a valid word date like "2 Feb 2016" and "2 Feb" etc.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the user input string array contains a valid word date and 
	 * 		   false otherwise.
	 */
	private boolean checkIfInputIsAValidWordDate(String[] splitUserInput, int index) {
		int numEntries = 0;
		boolean ans = false; 
		
		for (int j = index; j < splitUserInput.length; j++) {
			if (numEntries > MAXIMUM_NUMBER_OF_WORDS_IN_WORD_DATE) {
				break;
			} else {
				String splitWord = splitUserInput[j];
				if (!ParserCommons.isNumber(splitWord) && isMonth(splitWord)) {
					ans = true;
					break;
				}
			}
			numEntries++;
		}
		return ans;
	}

	/**
	 * Checks if the current word is a month. "Feb", "March" will return true but
	 * "Fbe" will return false.
	 * 
	 * @param currWord
	 * 		  The word of the current word in the user input string array
	 * 
	 * @return true if the word is a valid month and false otherwise
	 */
	private static boolean isMonth(String currWord) {
		boolean ans = false;
		for (int i = STARTING_VALUE_OF_ARRAY_CONSTANTS; i < monthsInYear.length; i++) {
			if (currWord.contains(monthsInYear[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	/**
	 * Changes a month string to the respective int. For example "Feb" will return 2.
	 * 
	 * @param monthInput
	 * 		  The month string from the user
	 * 
	 * @return the int form of the month string.
	 */
	private static int convertMonthStrToInt(String monthInput) {
		int monthInt = UNINITIALIZED_INT;
		for (int j = 1; j < monthsInYear.length; j++) {
			if (monthInput.contains(monthsInYear[j])) {
				monthInt = j;
				break;
			}
		}
		return monthInt;
	}
}
