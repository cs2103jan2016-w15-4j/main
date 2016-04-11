//@@author A0133338J
package dooyit.parser;

import dooyit.common.datatype.DateTime;

/** 
 * 
 * @author Annabel
 *
 */
public interface DateTimeParserCommons {
	public static final int COMBINED_INDEX_DAY_OF_WEEK = 0;
	public static final int COMBINED_INDEX_TIME = 1;
	public static final int COMBINED_INDEX_DD = 2;
	public static final int COMBINED_INDEX_MM = 3;
	public static final int COMBINED_INDEX_YY = 4;
	public static final int COMBINED_INDEX_COUNTER = 5;
	
	public static final int DATE_INDEX_OF_DD = 0;
	public static final int DATE_INDEX_OF_MM = 1;
	public static final int DATE_INDEX_OF_YY = 2;
	public static final int UNINITIALIZED_INT = -1;
	
	public static final int NEXT_DAY = 1;
	public static final String EMPTY_STRING = "";
	public static final int STARTING_VALUE_OF_ARRAY_CONSTANTS = 1;
	
	public static final int MAX_NUM_OF_DAYS_IN_A_MONTH = 31;
	public static final int NUMBER_OF_DAYS_IN_WEEK = 7;
	public static final int NUMBER_OF_MONTHS_IN_A_YEAR = 12;
	
	public static String[] monthsInYear = new String[] { EMPTY_STRING, "jan", "feb", "mar", "apr", "may", "jun", 
														 "jul", "aug", "sep", "oct", "nov", "dec" };
	
	public static int[] daysInMonthNonLeapYear = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	public static int[] daysInMonthLeapYear = new int[] { UNINITIALIZED_INT, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	public static final String TIME_SEPARATOR_DOT = ".";
	public static final String TIME_SEPARATOR_COLON = ":";
	public static final String PM = "pm";
	public static final String AM = "am";
	
	/** 
	 * 
	 * @param arr
	 * @param index
	 * @return
	 */
	static boolean hasAWordAfterCurrWord(String[] arr, int index) {
		return index < arr.length - 1;
	}
	
	/** 
	 * 
	 * @param combined
	 * @param date
	 * @param day
	 * @return
	 */
	static int[] getNewCombinedArray(int[] combined, int[] date, int day ) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], combined[COMBINED_INDEX_COUNTER] };
	}
	
	/** 
	 * 
	 * @param combined
	 * @param date
	 * @param day
	 * @param index
	 * @return
	 */
	static int[] getNewCombinedArray(int[] combined, int[] date, int day, int index) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], index };
	}
	
	/** 
	 * 
	 * @param currWord
	 * @return
	 */
	static boolean isValidTime(String currWord) {
		boolean ans = currWord.contains(PM) || currWord.contains(AM);
		ans = ans || currWord.contains(TIME_SEPARATOR_COLON) || currWord.contains(TIME_SEPARATOR_DOT);
		return ans;
	}
	
	/** 
	 * 
	 * @param yy
	 * @return
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
	 * 
	 * @param day
	 * @return
	 */
	static int getNextDayInt(int day) {
		day += 1;
		day %= NUMBER_OF_DAYS_IN_WEEK ;
		return day;
	}

	/** 
	 * 
	 * @param fastForward
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	static int[] getDateAfterANumberOfDays(int fastForward, int day, int month, int year) {
		int newDay = day + fastForward;
		int newMonth = month;
		int newYear = year;
		
		int[] daysInMonth = getDaysInMonthArray(year);

		while (newDay > daysInMonth[newMonth]) {
			newDay -= daysInMonth[newMonth];
			newMonth += 1;
		}

		if (newMonth > NUMBER_OF_MONTHS_IN_A_YEAR) {
			newMonth = 1;
			newYear += 1;
		}

		int[] ans = new int[] { newDay, newMonth, newYear };
		return ans;
	}
}
