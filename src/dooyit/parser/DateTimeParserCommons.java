//@@author A0133338J
package dooyit.parser;

import dooyit.common.datatype.DateTime;

/** 
 * DateParserCommons interface contains methods and constants that are shared 
 * by DateTimeParser, RealtiveDateParser, FixedDateParser and TimeParser.
 * 
 * @author Annabel
 *
 */
public interface DateTimeParserCommons {
	// Index of elements in the Combined int array
	public static final int COMBINED_INDEX_DAY_OF_WEEK = 0;
	public static final int COMBINED_INDEX_TIME = 1;
	public static final int COMBINED_INDEX_DD = 2;
	public static final int COMBINED_INDEX_MM = 3;
	public static final int COMBINED_INDEX_YY = 4;
	public static final int COMBINED_INDEX_COUNTER = 5;

	// Index of elements in the date int array
	public static final int DATE_INDEX_OF_DD = 0;
	public static final int DATE_INDEX_OF_MM = 1;
	public static final int DATE_INDEX_OF_YY = 2;

	// Constant for uninitialized int attributes and variables
	public static final int UNINITIALIZED_INT = -1;

	// Constant for the fast forwarding to the next day
	public static final int NEXT_DAY = 1;

	// For the replacement of words
	public static final String EMPTY_STRING = "";

	// For the iteration of array constants
	public static final int STARTING_VALUE_OF_ARRAY_CONSTANTS = 1;

	// Constants indicating number of elements in week, month and year
	public static final int NUMBER_OF_DAYS_IN_A_MONTH_MAXIMUM = 31;
	public static final int NUMBER_OF_DAYS_IN_WEEK = 7;
	public static final int NUMBER_OF_MONTHS_IN_A_YEAR = 12;

	// Array constants
	public static final String[] monthsInYear = new String[] { EMPTY_STRING, "jan", "feb", "mar", "apr", "may", "jun",
			"jul", "aug", "sep", "oct", "nov", "dec" };
	public static final int[] daysInMonthNonLeapYear = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };
	public static final int[] daysInMonthLeapYear = new int[] { UNINITIALIZED_INT, 31, 29, 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31 };

	// Time markers
	public static final String TIME_SEPARATOR_DOT = ".";
	public static final String TIME_SEPARATOR_COLON = ":";

	// Time indicators of morning and afternoon
	public static final String PM = "pm";
	public static final String AM = "am";
	
	/** 
	 * Checks if the is another word in the array after the current index.
	 * 
	 * @param arr
	 * 		  The String array of the date time inputs
	 * 
	 * @param index
	 * 		  The index of the current word.
	 * 
	 * @return true if there is another word after the current word and false 
	 * 		   otherwise.
	 */
	static boolean hasAWordAfterCurrWord(String[] arr, int index) {
		return index < arr.length - 1;
	}
	
	/**
	 * Puts the date array elements and the day int into the combined int array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *
	 * @param date
	 *        An int array containing a date in the form of DD, MM and YY.
	 * 
	 * @param day
	 *        Integer indicating the day of the week.
	 * 
	 * @return int array with the new values from the date array and the day
	 *         int.
	 */
	static int[] getNewCombinedArray(int[] combined, int[] date, int day) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM],
				date[DATE_INDEX_OF_YY], combined[COMBINED_INDEX_COUNTER] };
	}

	/**
	 * Puts the date array elements, day int and index into the combined int
	 * array.
	 * 
	 * @param combined
	 *        An int array that contains the day int, time int, date (DD,
	 *        MM, YY) and the current index in the user input string array.
	 *
	 * @param date
	 *        An int array containing a date in the form of DD, MM and YY.
	 *        
	 * @param day
	 *        Integer indicating the day of the week.
	 * 
	 * @param index
	 *        Current index in the user input string array.
	 * 
	 * @return int array with the new values from the date array, the day int
	 *         and the index.
	 */
	static int[] getNewCombinedArray(int[] combined, int[] date, int day, int index) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM],
				date[DATE_INDEX_OF_YY], index };
	}
	
	/**
	 * Checks if the word contains the noon or morning indicators or if it
	 * contains the time markers.
	 * 
	 * @param currWord
	 * 		  One word from the user input string.
	 * 
	 * @return true if any of the conditions are met and false otherwise.
	 */
	static boolean isValidTime(String currWord) {
		boolean ans = currWord.contains(PM) || currWord.contains(AM);
		ans = ans || currWord.contains(TIME_SEPARATOR_COLON) || currWord.contains(TIME_SEPARATOR_DOT);
		return ans;
	}
	
	/**
	 * Checks if the year is a leap year and returns the correct int array.
	 * 
	 * @param yy
	 *        The year input from the user.
	 * 
	 * @return the int array with 29 days for Feb if the year input is a leap
	 *         year or 28 days for Feb if the year input is not a leap year.
	 */
	static int[] getDaysInMonthArray(int yy) {
		int[] ans;
		if(DateTime.isLeapYear(yy)) {
			ans = daysInMonthLeapYear;
		} else {
			ans = daysInMonthNonLeapYear;
		}
		return ans;
	}
	
	/**
	 * Calculates the correct day of the week int for the next day
	 * 
	 * @param day
	 *        Today's day of the week in int
	 * 
	 * @return the day of the week for the next day.
	 */
	static int getNextDayInt(int day) {
		day += 1;
		day %= NUMBER_OF_DAYS_IN_WEEK ;
		return day;
	}

	/**
	 * Calculates the date after fast forwarding a given number of days from the
	 * current date.
	 * 
	 * @param fastForward
	 * 		  The number of days to fast forward from the current date
	 * 
	 * @param day
	 * 		  The DD of the present date.
	 * 
	 * @param month
	 * 		  The MM of the present date.
	 * 
	 * @param year
	 * 		  The YY of the present date.
	 * 
	 * @return int array of the future date.
	 */
	static int[] getDateAfterANumberOfDays(int fastForward, int day, int month, int year) {
		int newDay = day + fastForward;
		int newMonth = month;
		int newYear = year;
		
		// Getting the correct days in month int array
		int[] daysInMonth = getDaysInMonthArray(newYear);

		while (newDay > daysInMonth[newMonth]) {
			newDay -= daysInMonth[newMonth];
			newMonth += 1;
			
			// Check if newMonth exceeds the number of months in a year
			if (newMonth > NUMBER_OF_MONTHS_IN_A_YEAR) {
				newMonth = 1;	// Reset month to January
				newYear += 1;	// Increment year by 1
				daysInMonth = getDaysInMonthArray(newYear);	// Update array
			}
		}

		int[] ans = new int[] { newDay, newMonth, newYear };
		return ans;
	}
}
