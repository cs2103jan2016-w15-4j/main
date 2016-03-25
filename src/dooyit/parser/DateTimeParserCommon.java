package dooyit.parser;

public interface DateTimeParserCommon {
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
	
	default boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}
	
	default int convertStringToInt(String timeString) {
		return Integer.parseInt(timeString);
	}
	
	default boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}
	
	default int incrementByOne(int index) {
		return index + 1;
	}
	
	default boolean hasAWordAfterCurrWord(String[] arr, int index) {
		return index < arr.length - 1;
	}
	
	default int[] getNewCombinedArray(int[] combined, int[] date, int day ) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], combined[COMBINED_INDEX_COUNTER] };
	}
	
	default int[] getNewCombinedArray(int[] combined, int[] date, int day, int index) {
		return new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], index };
	}
	
	default boolean isValidTime(String currWord) {
		boolean ans = currWord.contains(PM) || currWord.contains(AM);
		ans = ans || currWord.contains(TIME_SEPARATOR_COLON) || currWord.contains(TIME_SEPARATOR_DOT);
		return ans;
	}
	
	default boolean isLeapYear(int currYear) {
		int[] arrLeapYears = new int[] { 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096 };

		boolean ans = false;
		for (int i = 0; i < arrLeapYears.length; i++) {
			if (currYear < arrLeapYears[i]) {
				break;
			}
			if (currYear == arrLeapYears[i]) {
				ans = true;
				break;
			}
		}
		return ans;
	}

}
