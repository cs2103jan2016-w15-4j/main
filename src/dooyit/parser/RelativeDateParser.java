//@@author A0133338J
package dooyit.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

/** 
 * RelativeDateParser class takes in a date string and converts it into an int array. 
 * It implements DateTimeparserCommons and ParserCommons to use the shared methods and constants.
 * 
 * @author Annabel
 *
 */
public class RelativeDateParser implements DateTimeParserCommons, ParserCommons {
	// Error message
	private static final String ERROR_MESSAGE_INVALID_DATE_INPUT = "Invalid date input!";
	
	// Array Constants
	private static final String[] validWordForDay = new String[] { "day", "days", "dd" };
	private static final String[] validWordForWeek = new String[] { "week", "weeks", "wk" };
	private static final String[] validWordForToday = new String[] { "today", "tdy"};
	private static final String[] validWordForTomorrow = new String[] { "tomorrow", "tmr"};
	private static final String[] daysInWeekShortForm = new String[] { EMPTY_STRING, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	
	// Indicator of relative dates
	private static final String THIS = "this";
	private static final String NEXT = "next";

	// Logger for RelativeDateParser
	private static Logger logger = Logger.getLogger("RelativeDateParser");
	
	// RelativeDateParser object attributes
	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	
	/** Types of Relative Dates */
	private enum RelativeDateFormat {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, 
		TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS, TYPE_TODAY, 
		TYPE_TOMORROW, TYPE_INVALID
	};
	
	/** 
	 * Initializes a new RelativeDateParser object with a specified date and time
	 * 
	 * @param dateTime
	 * 		  DateTime object of a specified date and time
	 */
	public RelativeDateParser(DateTime dateTime) {
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();
		
		logger.log(Level.INFO, "Initialised RelativeDateParser object with a specified date time");
	}
	
	/** 
	 * Parses the RelativeDate input and returns a combined int array.
	 * 
	 * @param splitUserInput
	 * 		  The user input string array
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @param index
	 * 		  The array index of the current word in the user input String array.
	 * 
	 * @return the int array form of the date string from the user.
	 * 
	 * @throws IncorrectInputException if the date string isn't a valid RelativeDate type.
	 */
	public int[] parse(String[] splitUserInput, int[] combined, int index) throws IncorrectInputException {
		
		String currWord = splitUserInput[index];
		switch (getRelativeDateType(currWord, splitUserInput, index)) {
		case TYPE_THIS_DAY_OF_WEEK :
			combined = getThisDayOfWeek(splitUserInput, index, combined);
			break;

		case TYPE_NEXT_DAY_OF_WEEK :
			combined = getNextDayOfWeek(splitUserInput, index, combined);
			break;

		case TYPE_DAY_OF_WEEK :
			combined = getDayOfWeek(splitUserInput, index, combined);
			break;

		case TYPE_NEXT_WEEK :
			combined = getDateAfterOneWeek(splitUserInput, index, combined);
			break;

		case TYPE_NUM_DAYS : 
			combined = getDateAndDayAfterANumberOfDays(splitUserInput, index, combined);
			break;

		case TYPE_NUM_WEEKS :
			combined = getDateAndDayAfterNumberOfWeeks(splitUserInput, index, combined);
			break;

		case TYPE_TODAY :
			combined = getCombinedArrayForToday(combined);
			break;

		case TYPE_TOMORROW :
			combined = getCombinedArrayForTomorrow(combined);
			break;

		default :
			combined[COMBINED_INDEX_COUNTER] += 1;
			throw new IncorrectInputException(ERROR_MESSAGE_INVALID_DATE_INPUT);
		}
			
		return combined;
	}
	
	/**
	 * Checks the current word to see which RelativeDateType it is. 
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return DateTimeFormat type.
	 */
	private RelativeDateFormat getRelativeDateType(String currWord, String[] splitUserInput, int index) {
		RelativeDateFormat type;
		if (isThisMondayType(currWord, splitUserInput, index)) {
			// For parsing "this monday", "this tue" etc.
			type = RelativeDateFormat.TYPE_THIS_DAY_OF_WEEK;

		} else if (isNextWeek(currWord, splitUserInput, index)) {
			type = RelativeDateFormat.TYPE_NEXT_WEEK;

		} else if (isNextWeekday(currWord, splitUserInput, index)) {
			// For parsing "next wed", "next thur" etc.
			type = RelativeDateFormat.TYPE_NEXT_DAY_OF_WEEK;

		} else if (isValidDay(currWord)) {
			type = RelativeDateFormat.TYPE_DAY_OF_WEEK;

		} else if (isToday(currWord)) {
			type = RelativeDateFormat.TYPE_TODAY;

		} else if (isTomorrow(currWord)) {
			type = RelativeDateFormat.TYPE_TOMORROW;

		} else if (isNumberOfDays(currWord, splitUserInput, index)) {
			// For parsing "2 days", "4 day" etc.
			type = RelativeDateFormat.TYPE_NUM_DAYS;

		} else if (isNumberOfWeeks(currWord, splitUserInput, index)) {
			// For parsing "2 weeks", "4 wk" etc.
			type = RelativeDateFormat.TYPE_NUM_WEEKS;

		} else {
			type = RelativeDateFormat.TYPE_INVALID;
		}
		return type;
	}
	
	/** 
	 * Checks if the current word is a relative date string or is part of a relative date string.
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if current word is part of a valid relative date string and false otherwise.
	 */
	public boolean isRelativeDate(String currWord, String[] splitUserInput, int index) {
		return getRelativeDateType(currWord, splitUserInput, index) != RelativeDateFormat.TYPE_INVALID;
	}

	/**
	 * Checks if the current word is part of a date string of the format
	 * "4 days" or "4 dd" or "4 day".
	 * 
	 * @param currWord
	 *        Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 *        The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String
	 *        array.
	 *        
	 * @return true if the current word is part of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isNumberOfDays(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (ParserCommons.isNumber(currWord) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			int indexOfNextWord = index + 1;
			String nextWord = splitUserInput[indexOfNextWord];
			ans = checkIfWordIsInArray(nextWord, validWordForDay);
		}
		return ans;
	}

	/** 
	 * Checks if the current word is part of a date string of the format
	 * "2 weeks", "2 week" and "2 wk".
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the current word is part of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isNumberOfWeeks(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (ParserCommons.isNumber(currWord) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[index + 1];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	/** 
	 * Checks if the current word is of the format "tmr" or "tomorrow".
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @return true if the current word is of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isTomorrow(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForTomorrow);
	}

	/** 
	 * Checks if the current word is of the format "tdy" or "today".
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @return true if the current word is of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isToday(String currWord) {
		return checkIfWordIsInArray(currWord, validWordForToday);
	}
	
	/** 
	 * Checks if a word is in an array of aliases. For example, this method is used
	 * to check if "tdy" is an acceptable alias of the word "today" by checking if "tdy"
	 * if in the string array validWordForToday.
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param wordArray
	 * 		  The String arrays like validWordForToday, validWordForTomorrow, validWordForWeek,
	 * 		  and validWordForDay
	 * 
	 * @return true if the word is an alias and false otherwise.
	 */
	private boolean checkIfWordIsInArray(String currWord, String[] wordArray) {
		boolean ans = false;
		for (int i = 0; i < wordArray.length; i++) {
			if (currWord.equals(wordArray[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}
	
	/** 
	 * Checks if the current word is part of a date string of the format
	 * "next tue", "next wed" etc.
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the current word is part of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isNextWeekday(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) { 
			int indexOfNextWord = index + 1;
			String nextWord = splitUserInput[indexOfNextWord];
			ans = isValidDay(nextWord);
		}
		return ans;
	}

	/** 
	 * Checks if the current word is part of a date string of the format
	 * "next weeks", "next week" and "next wk".
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the current word is part of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isNextWeek(String currentWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (currentWord.equals(NEXT) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) {
			int indexOfNextWord = index + 1;
			String nextWord = splitUserInput[indexOfNextWord];
			ans = checkIfWordIsInArray(nextWord, validWordForWeek);
		}
		return ans;
	}
	
	// Eg. 2 weeks later
	/** 
	 * Converts date string of the formats "2 weeks", "2 wk", "2 week" into an integer array.
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
	 * @return integer array with the updated fields.
	 */
	private int[] getDateAndDayAfterNumberOfWeeks(String[] splitUserInput, int index, int[] combined) {
		int numWeeksLater = Integer.parseInt(splitUserInput[index]);
		int day = currDayInWeekInt;
		
		// Calculate the number of days to fast forward from the present date.
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK * numWeeksLater;
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		
		// Get the integer array
		int[] ans = DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
		return ans;
	}

	/** 
	 * Checks if the current word is part of a date string of the format
	 * "this tue", "this wed" etc.
	 * 
	 * @param currentWord
	 * 
	 * @param splitUserInput
	 * 		  The user input String array.
	 * 
	 * @param index
	 *        The array index of the current word in the user input String array.
	 *        
	 * @return true if the current word is part of the stated format of date string 
	 *		   and false otherwise. 
	 */
	private boolean isThisMondayType(String currentWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (currentWord.equals(THIS) && DateTimeParserCommons.hasAWordAfterCurrWord(splitUserInput, index)) { 
			int indexOfNextWord = index + 1;
			String nextWord = splitUserInput[indexOfNextWord];
			ans = isValidDay(nextWord);
		}
		return ans;
	}
	
	/** 
	 * Converts the date inputs of the format "tmr", "tomorrow" into an int array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getCombinedArrayForTomorrow(int[] combined) {
		// Get tomorrow's day of the week in int form
		int day = DateTimeParserCommons.getNextDayInt(currDayInWeekInt);
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(NEXT_DAY, currDD, currMM, currYY);
		
		// Get updated combined int array
		int[] ans = DateTimeParserCommons.getNewCombinedArray(combined, date, day);
		return ans;
	}
	
	/** 
	 * Converts the date inputs of the format "tdy", "today" into an int array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *        
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getCombinedArrayForToday(int[] combined) {
		return combined;
	}
	
	/** 
	 * 	Converts the date inputs of the format "2 days", "2 day" and "2 dd" into an int array.
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
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getDateAndDayAfterANumberOfDays(String[] splitUserInput, int index, int[] combined) {
		// Gets the number of days later
		int numDaysLater = Integer.parseInt(splitUserInput[index]);
		
		// Gets the future date's day of the week in int form
		int day = getDayOfWeekAfterANumberOfDays(numDaysLater);
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(numDaysLater, currDD, currMM, currYY);
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	/** 
	 * Converts the date inputs of the format "next week", "next wk" into an int array.
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
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getDateAfterOneWeek(String[] splitUserInput, int index, int[] combined) {
		int day = currDayInWeekInt;
		
		// Get the number of days to fast forward from the present date
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	/** 
	 * Converts the date inputs of the format "mon", "wednesday" etc into an int array.
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
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getDayOfWeek(String[] splitUserInput, int index, int[] combined) {
		// Convert the input day string into integer
		int day = convertDayStringToInt(splitUserInput[index]);
		
		// Get the number of days to fast forward from the present date
		int fastForward = getFastForwardFromDayOfWeek(day);
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index);
	}

	/** 
	 * Converts the date inputs of the format "next mon", "next wednesday" etc into an int array.
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
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getNextDayOfWeek(String[] splitUserInput, int index, int[] combined) {
		// Convert the input day string into integer
		int day = convertDayStringToInt(splitUserInput[index + 1]);
		
		// Get the number of days to fast forward from the present date
		int fastForward = getFastForwardFromDayOfWeek(day) + NUMBER_OF_DAYS_IN_WEEK;
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}

	/** 
	 * Converts the date inputs of the format "this mon", "this wednesday" etc into an int array.
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
	 * @return the combined int array with the updated date fields.
	 */
	private int[] getThisDayOfWeek(String[] splitUserInput, int index, int[] combined) {
		// Convert the input day string into integer
		int day = convertDayStringToInt(splitUserInput[index + 1]);
		
		// Get the number of days to fast forward from the present date
		int fastForward = getFastForwardFromDayOfWeek(day);
		
		// Get the future date in an int array
		int[] date = DateTimeParserCommons.getDateAfterANumberOfDays(fastForward, currDD, currMM, currYY);
		
		return DateTimeParserCommons.getNewCombinedArray(combined, date, day, index + 1);
	}
	
	/** 
	 * Converts a day input string into the day int form.
	 * 
	 * @param string
	 * 		  The day string like "wed", "tuesday" etc.
	 * 
	 * @return the int of the day string.
	 */
	private int convertDayStringToInt(String string) {
		int day = UNINITIALIZED_INT;
		for (int i = STARTING_VALUE_OF_ARRAY_CONSTANTS; i < daysInWeekShortForm.length; i++) {
			if (string.contains(daysInWeekShortForm[i])) {
				day = i;
				break;
			}
		}
		return day;
	}

	/** 
	 * Gets the day of the week in int form from the given number of days
	 * to fast forward from the present date.
	 * 
	 * @param daysLater
	 * 		  Number of days to fast forward from the present date
	 * 
	 * @return the day of the week in integer form.
	 */
	private int getDayOfWeekAfterANumberOfDays(int daysLater) {
		int dayOfWeek = daysLater + currDayInWeekInt;
		dayOfWeek %= NUMBER_OF_DAYS_IN_WEEK ;
		if (dayOfWeek == 0) {
			dayOfWeek = NUMBER_OF_DAYS_IN_WEEK;;
		}
		return dayOfWeek;
	}

	/** 
	 * Calculate the number of days to fast forward from the present date from the 
	 * given day string.
	 * 
	 * @param desiredDay
	 * 		  The day string like "wed", "tuesday" etc.
	 * 
	 * @return the number of days to fast forward from the present date.
	 */
	private int getFastForwardFromDayOfWeek(int desiredDay) {
		int fastForward = 0;
		if (currDayInWeekInt > desiredDay) {
			fastForward = NUMBER_OF_DAYS_IN_WEEK - currDayInWeekInt + desiredDay;
		} else if (currDayInWeekInt < desiredDay) {
			fastForward = desiredDay - currDayInWeekInt;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}
	
	/** 
	 * Checks if the word is a valid day of the week. For example "wed" is a 
	 * valid day but "wde" is not a valid day.
	 * 
	 * @param currWord
	 * 		  Current word in the user input string array.
	 * 
	 * @return true if the word is a valid day of the week and false otherwise.
	 */
	private boolean isValidDay(String currWord) {
		boolean ans = convertDayStringToInt(currWord) != -1;
		return ans;
	}
}
