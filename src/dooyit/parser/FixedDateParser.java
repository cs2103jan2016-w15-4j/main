//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

/**
 * 
 * @author Annabel
 *
 */
public class FixedDateParser implements DateTimeParserCommons {
	// Error messages
	private static final String ERROR_MESSAGE_INVALID_NUMBER_OF_DATE_INPUTS = "Invalid number of date inputs";
	private static final String ERROR_MESSAGE_DATE_INPUTS_MUST_BE_NUMBERS = "Date inputs must be numbers";
	private static final String ERROR_MESSAGE_INVALID_DATE = "Invalid date!";
	private static final String ERROR_MESSAGE_DATE_INPUTS_MUST_GREATER_THAN_ZERO = "Date Inputs must be greater than 0!";
	
	private static final int MAXIMUM_NUMBER_OF_WORDS_IN_WORD_DATE = 2;
	private static final int LAST_INDEX_OF_WORD_DATE = 2;
	private static final int YEARY_2000 = 2000;
	private static final int DATE_INDEX_OF_ADVANCE_INT = 3;
	private static final int DEFAULT_DD_IN_MONTH = 15;
	private static final String DATE_SEPARATOR = "/";

	// Logger for FixedDateParser
	private static Logger logger = Logger.getLogger("FixedDateParser");
	
	// DateTime object attributes
	private int currMM;
	private int currYY;
	private int currDD;

	/**
	 * 
	 * @param dateTime
	 */
	public FixedDateParser(DateTime dateTime) {
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		logger.log(Level.INFO, "Initialised FixedDateParser object");
	}

	/**
	 * 
	 * @param currWord
	 * @param splitUserInput
	 * @param index
	 * @return
	 */
	public boolean isFixedDate(String currWord, String[] splitUserInput, int index) {
		return isValidDate(currWord, splitUserInput, index);
	}

	/**
	 * 
	 * @param splitInput
	 * @param combined
	 * @param i
	 * @return
	 * @throws IncorrectInputException
	 */
	public int[] parse(String[] splitInput, int[] combined, int i) throws IncorrectInputException {
		try {
			combined = getDate(splitInput, i, combined);
		} catch (IncorrectInputException e) {
			throw e;
		}
		return combined;
	}

	/**
	 * 
	 * @param newDate
	 * @param index
	 * @return
	 */
	private int getAdvanceInt(int[] newDate, int index) {
		int ans = newDate[DATE_INDEX_OF_ADVANCE_INT] + index;
		return ans;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	private int getDayOfWeekFromADate(int[] date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayInt();
	}

	/**
	 * 
	 * @param splitInput
	 * @param currIndexInSplitInput
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] getDateAndArrayIndex(String[] splitInput, int currIndexInSplitInput) throws IncorrectInputException {
		String firstWord = splitInput[currIndexInSplitInput];
		int[] dateAndArrayIndex = new int[] { UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT,
				UNINITIALIZED_INT };

		try {
			if (firstWord.contains(DATE_SEPARATOR)) {
				String[] userInputForDate = firstWord.split(DATE_SEPARATOR);
				dateAndArrayIndex = getNumberDate(dateAndArrayIndex, userInputForDate);
			} else {
				dateAndArrayIndex = getWordDate(dateAndArrayIndex, currIndexInSplitInput, splitInput);
			}
		} catch (IncorrectInputException e) {
			throw e;
		}

		dateAndArrayIndex = setUninitializedValuesToDefault(dateAndArrayIndex);
		if (isInvalidDate(dateAndArrayIndex)) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
		}

		return dateAndArrayIndex;
	}

	/**
	 * 
	 * @param dateAndArrayIndex
	 * @param currIndexInSplitInput
	 * @param splitInput
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] getWordDate(int[] dateAndArrayIndex, int currIndexInSplitInput, String[] splitInput)
			throws IncorrectInputException {
		int counter = 0;
		for (int j = currIndexInSplitInput; j < splitInput.length; j++) {
			if (counter > LAST_INDEX_OF_WORD_DATE) {
				break;
			} else {
				String currWord = splitInput[j];
				try {
					dateAndArrayIndex = setValuesInArray(currWord, dateAndArrayIndex);
				} catch (IncorrectInputException e) {
					throw e;
				}
			}
			counter++;
		}
		return dateAndArrayIndex;
	}

	/**
	 * 
	 * @param dateAndArrayIndex
	 * @param userInputForDate
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] getNumberDate(int[] dateAndArrayIndex, String[] userInputForDate) throws IncorrectInputException {
		if (userInputForDate.length > 3 || userInputForDate.length == 1) {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_NUMBER_OF_DATE_INPUTS);
		}
		for (int j = 0; j < userInputForDate.length; j++) {
			if (!ParserCommons.isNumber(userInputForDate[j])) {
				throw new IncorrectInputException(ERROR_MESSAGE_DATE_INPUTS_MUST_BE_NUMBERS);
			}

			dateAndArrayIndex[j] = Integer.parseInt(userInputForDate[j]);

			if (dateAndArrayIndex[j] <= 0) {
				throw new IncorrectInputException(ERROR_MESSAGE_DATE_INPUTS_MUST_GREATER_THAN_ZERO);
			}
		}
		dateAndArrayIndex[DATE_INDEX_OF_ADVANCE_INT] += 1;
		return dateAndArrayIndex;
	}

	/**
	 * 
	 * @param currWord
	 * @param dateAndAdvanceIntArray
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] setValuesInArray(String currWord, int[] dateAndAdvanceIntArray) throws IncorrectInputException {
		if (ParserCommons.isNumber(currWord)) { // currWord is either DD or YY
			int currInt = Integer.parseInt(currWord);
			try {
				dateAndAdvanceIntArray = setDayAndYearValues(dateAndAdvanceIntArray, currInt);
				dateAndAdvanceIntArray[DATE_INDEX_OF_ADVANCE_INT] += 1;
			} catch (IncorrectInputException e) {
				throw e;
			}
		} else if (isMonth(currWord) && !ParserCommons.isInitialized(dateAndAdvanceIntArray, DATE_INDEX_OF_MM)) {
			dateAndAdvanceIntArray[DATE_INDEX_OF_MM] = convertMonthStrToInt(currWord);
			dateAndAdvanceIntArray[DATE_INDEX_OF_ADVANCE_INT] += 1;
		} else {
			if (!DateTimeParserCommons.isValidTime(currWord)) {
				throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
			}
		}

		return dateAndAdvanceIntArray;
	}

	/**
	 * 
	 * @param splitInput
	 * @param index
	 * @param combined
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] getDate(String[] splitInput, int index, int[] combined) throws IncorrectInputException {
		int[] newDate;
		try {
			newDate = getDateAndArrayIndex(splitInput, index); // [dd, mm, yy,
																// advanceInt]
		} catch (IncorrectInputException e) {
			throw e;
		}

		if (isInvalidDate(newDate)) {
			throw new IncorrectInputException();
		}

		return DateTimeParserCommons.getNewCombinedArray(combined, newDate, getDayOfWeekFromADate(newDate),
				getAdvanceInt(newDate, combined[COMBINED_INDEX_COUNTER]));
	}

	/**
	 * 
	 * @param dateAdvanceIntArray
	 * @param currInt
	 * @return
	 * @throws IncorrectInputException
	 */
	private int[] setDayAndYearValues(int[] dateAdvanceIntArray, int currInt) throws IncorrectInputException {
		if (currInt <= MAX_NUM_OF_DAYS_IN_A_MONTH
				&& !ParserCommons.isInitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = currInt;

		} else if (currInt >= YEARY_2000 && !ParserCommons.isInitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = currInt;

		} else {
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE);
		}
		return dateAdvanceIntArray;
	}

	/**
	 * 
	 * @param dateAdvanceIntArray
	 * @return
	 */
	private int[] setUninitializedValuesToDefault(int[] dateAdvanceIntArray) {
		if (!ParserCommons.isInitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = getCorrectYear(dateAdvanceIntArray);
		}

		if (!ParserCommons.isInitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = DEFAULT_DD_IN_MONTH; // Default
		}
		return dateAdvanceIntArray;
	}

	/**
	 * 
	 * @param dateAdvanceIntArray
	 * @return
	 */
	private int getCorrectYear(int[] dateAdvanceIntArray) {
		int newDD = dateAdvanceIntArray[DATE_INDEX_OF_DD];
		int newMM = dateAdvanceIntArray[DATE_INDEX_OF_MM];
		int newYY = UNINITIALIZED_INT;
		DateTime newDateTime = new DateTime(new int[] { newDD, newMM, currYY });
		DateTime currDateTime = new DateTime(new int[] { currDD, currMM, currYY });
		if (newDateTime.compareTo(currDateTime) == -1) { // Date has passed
			newYY = currYY + 1; // Increase the year by one
		} else { // Date has not passed
			newYY = currYY; // Set the year to current year
		}
		return newYY;
	}

	// Checks that the num of months does not exceed the max num of days in that
	// month,
	// Eg. 30 Feb will return an error
	/**
	 * 
	 * @param ans
	 * @return
	 */
	private static boolean isInvalidDate(int[] ans) {
		int[] daysInMonth;
		int mm = ans[DATE_INDEX_OF_MM];

		boolean output = false;
		if (mm > NUMBER_OF_MONTHS_IN_A_YEAR) {
			output = true;
		} else {
			int yy = ans[DATE_INDEX_OF_YY];
			daysInMonth = DateTimeParserCommons.getDaysInMonthArray(yy);
			output = ans[DATE_INDEX_OF_DD] > daysInMonth[mm];
		}
		return output;
	}

	/**
	 * 
	 * @param currWord
	 * @param splitInput
	 * @param i
	 * @return
	 */
	private boolean isValidDate(String currWord, String[] splitInput, int i) {
		boolean ans = false;
		if (currWord.contains(DATE_SEPARATOR)) { // Date is 11/2/2016
			ans = true;
		} else {
			ans = checkIfInputIsAValidWordDate(splitInput, i, ans);
		}
		return ans;
	}

	/**
	 * 
	 * @param splitInput
	 * @param i
	 * @param ans
	 * @return
	 */
	private boolean checkIfInputIsAValidWordDate(String[] splitInput, int i, boolean ans) {
		int numEntries = 0;

		for (int j = i; j < splitInput.length; j++) {
			if (numEntries > MAXIMUM_NUMBER_OF_WORDS_IN_WORD_DATE) {
				break;
			} else {
				String splitWord = splitInput[j];
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
	 * 
	 * @param currWord
	 * @return
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
	 * 
	 * @param monthInput
	 * @return
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
