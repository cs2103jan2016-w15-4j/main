package dooyit.parser;

import java.text.DateFormat;
import java.util.logging.*;

import dooyit.common.datatype.DateTime;
import dooyit.common.exception.IncorrectInputException;

public class DateTimeParser {
	
	private static final int MAX_NUM_OF_DAYS_IN_A_MONTH = 31;
	private static final String PM = "pm";
	private static final String AM = "am";
	private static final String DATE_SEPARATOR = "/";
	private static final int TWELVE_HOURS = 1200;
	private static final int NUM_DAYS_IN_WEEK = 7;
	private static final int NEXT_DAY = 1;

	private static final int COMBINED_INDEX_DAY_OF_WEEK = 0;
	private static final int COMBINED_INDEX_TIME = 1;
	private static final int COMBINED_INDEX_DD = 2;
	private static final int COMBINED_INDEX_MM = 3;
	private static final int COMBINED_INDEX_YY = 4;
	private static final int COMBINED_INDEX_COUNTER = 5;

	private static final int DATE_INDEX_OF_DD = 0;
	private static final int DATE_INDEX_OF_MM = 1;
	private static final int DATE_INDEX_OF_YY = 2;
	private static final int DATE_INDEX_OF_ADVANCE_INT = 3;

	private static final String DUMMY_STR = "Dummy_Str";
	private static final int UNINITIALIZED_INT = -1;
	private static final int DEFAULT_DD_IN_MONTH = 15;
	private static final int INDEX_FEBRUARY = 2;
	private static final String THIS = "this";
	private static final String NEXT = "next";

	private static boolean isInvalidDateTime;
	DateFormat dateFormat;

	// Can just look for substrings
	private static String[] daysInWeek = new String[] { DUMMY_STR, "mon", "tue", "wed", "thu", "fri", "sat", "sun" };
	private static String[] monthsInYear = new String[] { DUMMY_STR, "jan", "feb", "mar", "apr", "may", "jun", 
															"jul", "aug", "sep", "oct", "nov", "dec" };

	int LEAP_YEAR_FEB = 29;
	private static int[] daysInMonth = new int[] { UNINITIALIZED_INT, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


	private int currMM;
	private int currYY;
	private int currDD;
	private int currDayInWeekInt;
	private int currTime;
	private String currDayInWeekString;


	private static Logger logger = Logger.getLogger("DateTimeParser");

	enum DATE_TIME_FORMAT {
		TYPE_THIS_DAY_OF_WEEK, TYPE_NEXT_DAY_OF_WEEK, TYPE_DAY_OF_WEEK, TYPE_NEXT_WEEK, TYPE_NUM_DAYS, TYPE_NUM_WEEKS, TYPE_TODAY, TYPE_TOMORROW, TYPE_DATE, TYPE_TIME, TYPE_INVALID
	};

	public DateTimeParser() {
		DateTime dateTime = new DateTime();
		currTime = dateTime.getTimeInt();
		currDayInWeekString = dateTime.getDayStr();
		currDayInWeekInt = dateTime.getDayInt();
		currDD = dateTime.getDD();
		currMM = dateTime.getMM();
		currYY = dateTime.getYY();

		if (isLeapYear(currYY)) {
			daysInMonth[INDEX_FEBRUARY] = LEAP_YEAR_FEB;
		}
	}

	private boolean isLeapYear(int currYear) {
		int[] arrLeapYears = new int[] { 2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064,
				2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096 };

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

	private DATE_TIME_FORMAT getDateTimeType(String currWord, String[] splitUserInput, int index) {
		if (isThisMonday(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "is this Monday");
			return DATE_TIME_FORMAT.TYPE_THIS_DAY_OF_WEEK;

		} else if (isNextWeek(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNextWeek");
			return DATE_TIME_FORMAT.TYPE_NEXT_WEEK;

		} else if (isNextWeekday(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNextWeekday");
			return DATE_TIME_FORMAT.TYPE_NEXT_DAY_OF_WEEK;

		} else if (isValidDay(currWord)) {
			logger.log(Level.INFO, "isValidDay");
			return DATE_TIME_FORMAT.TYPE_DAY_OF_WEEK;

		} else if (isValidTime(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isValidTime");
			return DATE_TIME_FORMAT.TYPE_TIME;

		} else if (isValidDate(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isValidDate");
			return DATE_TIME_FORMAT.TYPE_DATE;

		} else if (isToday(currWord)) {
			logger.log(Level.INFO, "isToday");
			return DATE_TIME_FORMAT.TYPE_TODAY;

		} else if (isTomorrow(currWord)) {
			logger.log(Level.INFO, "isTomorrow");
			return DATE_TIME_FORMAT.TYPE_TOMORROW;

		} else if (isNumberOfDays(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNumDays");
			return DATE_TIME_FORMAT.TYPE_NUM_DAYS;

		} else if (isNumberOfWeeks(currWord, splitUserInput, index)) {
			logger.log(Level.INFO, "isNumWeeks");
			return DATE_TIME_FORMAT.TYPE_NUM_WEEKS;

		} else {
			logger.log(Level.INFO, "isInvalidType");
			return DATE_TIME_FORMAT.TYPE_INVALID;
		}
	}

	private boolean isNumberOfDays(String currWord, String[] splitUserInput, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(splitUserInput, index)) {
			String nextWord = splitUserInput[incrementByOne(index)];
			ans = nextWord.equals("days") || nextWord.equals("day") || nextWord.equals("dd");
		}
		return ans;
	}

	private boolean isNumberOfWeeks(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (isNumber(currWord) && hasAWordAfterCurrWord(arr, index)) {
			String nextWord = arr[incrementByOne(index)];
			ans = nextWord.equals("weeks") || nextWord.equals("week") || nextWord.equals("wk");
		}
		return ans;
	}

	private boolean hasAWordAfterCurrWord(String[] arr, int index) {
		return index < arr.length - 1;
	}

	private int incrementByOne(int index) {
		return index + 1;
	}

	public DateTime parse(String input) throws IncorrectInputException {
		String[] splitInput = input.toLowerCase().split("\\s+");
		// [dayOfWeek, time, DD, MM, YY, indexInArray]
		int[] combined = new int[] { currDayInWeekInt, UNINITIALIZED_INT, currDD, currMM, currYY, 0 };

		for (int i = 0; i < splitInput.length; i++) {
			String currWord = splitInput[i];
			switch (getDateTimeType(currWord, splitInput, i)) {
			case TYPE_THIS_DAY_OF_WEEK:
				combined = getThisDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_NEXT_DAY_OF_WEEK:
				combined = getNextDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_DAY_OF_WEEK:
				combined = getDayOfWeek(splitInput, i, combined);
				break;

			case TYPE_NEXT_WEEK:
				combined = getNextWeek(splitInput, i, combined);
				break;

			case TYPE_NUM_DAYS:
				combined = getNumDays(splitInput, i, combined);
				break;

			case TYPE_NUM_WEEKS:
				combined = getNumWeeks(splitInput, i, combined);
				break;

			case TYPE_TODAY:
				combined = getToday(combined);
				break;

			case TYPE_TOMORROW:
				combined = getTomorrow(combined);
				break;

			case TYPE_DATE:
				try {
					combined = getDate(splitInput, i, combined);
				} catch (IncorrectInputException e) {
					throw e;
				}
				break;

			case TYPE_TIME:
				try {
					combined = getTime(splitInput, i, combined);
				} catch (IncorrectInputException e) {
					throw e;
				}
				break;

			default:
				combined[COMBINED_INDEX_COUNTER] += 1;
				throw new IncorrectInputException("\"" + input + "\"" + " is an invalid date time input");

			}
			i = combined[COMBINED_INDEX_COUNTER];
		}
		return getDateTimeObject(combined);
	}

	// This method converts the array into a DateTime object
	private DateTime getDateTimeObject(int[] combined) {
		DateTime dateTime;
		if (!isInvalidDateTime) {
			int[] date = new int[] { combined[COMBINED_INDEX_DD], combined[COMBINED_INDEX_MM], combined[COMBINED_INDEX_YY] };
			int time = combined[COMBINED_INDEX_TIME];
			int day = combined[COMBINED_INDEX_DAY_OF_WEEK];

			if (hasPassed(currTime, time, date)) {
				date = getDate(NEXT_DAY);
				dateTime = new DateTime(date, daysInWeek[getNextDayInt()], time);
			} else {
				dateTime = new DateTime(date, daysInWeek[day], time);
			}
		} else {
			dateTime = null;
		}
		return dateTime;
	}

	private int[] getTomorrow(int[] combined) {
		int day = getNextDayInt();
		int[] date = getDate(NEXT_DAY);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], combined[COMBINED_INDEX_COUNTER] };
		printArray(ans);
		return ans;
	}

	private int[] getToday(int[] combined) {
		return combined;
	}

	private void printArray(int[] arr) {
		String temp = "";
		for (int i = 0; i < arr.length; i++) {
			temp += arr[i];
			temp += " ";
		}
		System.out.println(temp);
	}

	// Eg. 2 weeks later
	private int[] getNumWeeks(String[] splitInput, int i, int[] combined) {
		int numWeeksLater = Integer.parseInt(splitInput[i]);
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK * numWeeksLater;
		int[] date = getDate(fastForward);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM],
				date[DATE_INDEX_OF_YY], incrementByOne(i) };
		return ans;
	}

	// Eg. 2 days
	private int[] getNumDays(String[] splitInput, int i, int[] combined) {
		int numDaysLater = Integer.parseInt(splitInput[i]);
		int day = get_Int_Day_From(numDaysLater);
		int[] date = getDate(numDaysLater);
		printArray(date);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], incrementByOne(i) };
		return ans;
	}

	// Eg. next week
	private int[] getNextWeek(String[] splitInput, int i, int[] combined) {
		int day = currDayInWeekInt;
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
		int[] date = getDate(fastForward);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM],
				date[DATE_INDEX_OF_YY], incrementByOne(i) };
		return ans;
	}

	// Eg. monday, wed
	private int[] getDayOfWeek(String[] splitInput, int currIndexInSplitInputArray, int[] combined) {
		int day = convertDayStrToInt(splitInput[currIndexInSplitInputArray]);
		int fastForward = getFastForward(day);
		int[] date = getDate(fastForward);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], currIndexInSplitInputArray };
		return ans;
	}

	// Eg. next monday, next wed
	private int[] getNextDayOfWeek(String[] splitInput, int currIndexInSplitInputArray, int[] combined) {
		int day = convertDayStrToInt(splitInput[incrementByOne(currIndexInSplitInputArray)]);
		int fastForward = getFastForward(day) + NUM_DAYS_IN_WEEK;
		int[] date = getDate(fastForward);
		int[] ans = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], incrementByOne(currIndexInSplitInputArray) };
		return ans;
	}

	// Eg. this monday, this wed, this wednesday
	private int[] getThisDayOfWeek(String[] splitInput, int currIndexInSplitInputArray, int[] combined) {
		// splitInput[i].equals("this")
		int day = convertDayStrToInt(splitInput[incrementByOne(currIndexInSplitInputArray)]);
		int fastForward = getFastForward(day);
		int[] date = getDate(fastForward);
		int[] ansDate = new int[] { day, combined[COMBINED_INDEX_TIME], date[DATE_INDEX_OF_DD], date[DATE_INDEX_OF_MM], date[DATE_INDEX_OF_YY], incrementByOne(currIndexInSplitInputArray) };
		return ansDate;
	}

	private int[] getDate(String[] splitInput, int currIndexInSplitInputArray, int[] combined) {
		int[] newDate = getDateAndAdvanceInt(splitInput, currIndexInSplitInputArray); // [dd, mm, yy, advanceInt]
		int[] ans = new int[] { getDay(newDate), combined[COMBINED_INDEX_TIME], newDate[DATE_INDEX_OF_DD], newDate[DATE_INDEX_OF_MM], newDate[DATE_INDEX_OF_YY],
								getAdvanceInt(newDate, combined[COMBINED_INDEX_COUNTER]) };
		return ans;
	}

	private int getAdvanceInt(int[] newDate, int index) {
		int ans = newDate[DATE_INDEX_OF_ADVANCE_INT] + index;
		return ans;
	}

	// This method calculates that day of the week that the date falls on
	private int getDay(int[] date) {
		int[] dayTable = new int[] { 7, 1, 2, 3, 4, 5, 6 };
		int[] monthTable = new int[] {UNINITIALIZED_INT, 6, 2, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
		int dd = date[DATE_INDEX_OF_DD];
		int mm = date[DATE_INDEX_OF_MM];
		int yy = date[DATE_INDEX_OF_YY];
		
		int lastTwoDigitsOfYear = yy % 1000;
		int divideLastTwoDigitsOfYearByFour = lastTwoDigitsOfYear / 4;
		int sum = lastTwoDigitsOfYear + divideLastTwoDigitsOfYearByFour + dd + monthTable[mm];
		if(isLeapYear(yy) && mm <= 2 && dd <= 31) {
			sum--;
		}
		return dayTable[sum % 7];
		
	}

	// To Do: include the check for valid dates
	private int[] getDateAndAdvanceInt(String[] splitInput, int currIndexInSplitInput) {
		String firstWord = splitInput[currIndexInSplitInput];
		int[] dateAdvanceIntArray= new int[] { UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT, UNINITIALIZED_INT };
		int numEntries = UNINITIALIZED_INT;

		if (firstWord.contains(DATE_SEPARATOR)) {
			//firstWord is in the form of DD/MM or DD/MM/YY
			String[] userInputForDate = firstWord.split(DATE_SEPARATOR);
			for (int j = 0; j < userInputForDate.length; j++) {
				dateAdvanceIntArray[j] = Integer.parseInt(userInputForDate[j]);
			}
			numEntries++;
			
		} else {
			int counter = 0;
			for (int j = currIndexInSplitInput; j < splitInput.length; j++) {
				if (counter > 2) {
					break;
				} else {
					String currWord = splitInput[j];
					logger.log(Level.INFO, "currWord is " + currWord);
					if (isNumber(currWord)) {			//currWord is either DD or YY
						int currInt = Integer.parseInt(currWord);
						logger.log(Level.INFO, "currInt in getDateAndAdvanceInt is " + currInt);
						if (currInt <= MAX_NUM_OF_DAYS_IN_A_MONTH && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
							dateAdvanceIntArray[DATE_INDEX_OF_DD] = currInt;
							numEntries++;

						} else if (currInt >= 2000 && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
							dateAdvanceIntArray[DATE_INDEX_OF_YY] = currInt;
							numEntries++;
						}
					} else if (isMonth(currWord) && isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_MM)) {
						logger.log(Level.INFO, "currWord in Month check is " + currWord);
						dateAdvanceIntArray[DATE_INDEX_OF_MM] = convertMonthStrToInt(currWord);
						numEntries++;
					}
				}
				counter++;
			}
		}

		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_YY)) {
			dateAdvanceIntArray[DATE_INDEX_OF_YY] = getCorrectYear(dateAdvanceIntArray);
		}

		if (isUninitialized(dateAdvanceIntArray, DATE_INDEX_OF_DD)) {
			dateAdvanceIntArray[DATE_INDEX_OF_DD] = DEFAULT_DD_IN_MONTH; // Default
		}

		if (isInvalidDate(dateAdvanceIntArray)) {
			throw new IncorrectInputException("Invalid date!");
		}

		dateAdvanceIntArray[DATE_INDEX_OF_ADVANCE_INT] = numEntries;
		return dateAdvanceIntArray;
	}

	private int getCorrectYear(int[] dateAdvanceIntArray) {
		int newDD = dateAdvanceIntArray[DATE_INDEX_OF_DD];
		int newMM = dateAdvanceIntArray[DATE_INDEX_OF_MM];
		int newYY = UNINITIALIZED_INT;
		if(newMM <= currMM && newDD < currDD) {		// Date has passed
			newYY = incrementByOne(currYY);			// Increase the year by one
		} else {				// Date has not passed
			newYY = currYY;		// Set the year to current year
		}
		return newYY;
	}

	private boolean isUninitialized(int[] ans, int index) {
		return ans[index] == UNINITIALIZED_INT;
	}

	// Checks that the num of months does not exceed the max num of days in that
	// month,
	// Eg. 30 Feb will return an error
	private boolean isInvalidDate(int[] ans) {
		int mm = ans[DATE_INDEX_OF_MM];
		System.out.println("this.mm is "  + mm);
		return ans[DATE_INDEX_OF_DD] > daysInMonth[mm];
	}

	private boolean isValidTime(String currWord, String[] arr, int index) {
		boolean ans = false;

		if (currWord.contains(PM) || currWord.contains(AM)) {
			logger.log(Level.INFO, "reached here in isValidTime");
			currWord = currWord.replace(PM, "").replace(AM, "");
			if (currWord.contains(":") || currWord.contains(".")) { // Eg: 12pm,
																	// 12.30pm,
																	// 12:50am
				currWord = currWord.replace(":", "").replace(".", "");
				ans = true;
			} else {
				ans = true;
			}

		} else if (isNumber(currWord.replace(":", "").replace(".", "")) && hasAWordAfterCurrWord(arr, index)) { // Eg.9.30 pm, 9
			String nextWord = arr[incrementByOne(index)];
			if (nextWord.equals(AM) ^ nextWord.equals(PM)) {
				ans = true;
			}
		} else if (currWord.contains(":")) { // 24Hour formats eg: 23:30
			currWord = currWord.replace(":", "");
			if (isNumber(currWord)) {
				ans = true;
			}
		} else {
			// Invalid command here
		}
		return ans;
	}

	private boolean isTomorrow(String currWord) {
		return currWord.equals("tomorrow") || currWord.equals("tmr");
	}

	private boolean isNextWeekday(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && hasAWordAfterCurrWord(arr, index)) { 
			//Check if there is a word that follow "this"
			ans = isValidDay(arr[incrementByOne(index)]);
		}
		return ans;
	}

	private boolean isNextWeek(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (currWord.equals(NEXT) && hasAWordAfterCurrWord(arr, index)) {
			ans = arr[incrementByOne(index)].equals("week") || arr[incrementByOne(index)].equals("weeks") || arr[incrementByOne(index)].equals("wk");
		}
		return ans;
	}

	private boolean isThisMonday(String currWord, String[] arr, int index) {
		boolean ans = false;
		if (currWord.equals(THIS) && hasAWordAfterCurrWord(arr, index)) { 
			ans = isValidDay(arr[incrementByOne(index)]);
		}
		return ans;
	}

	private boolean isValidDay(String currWord) {
		return convertDayStrToInt(currWord) != -1;
	}

	private boolean isValidDate(String currWord, String[] splitInput, int i) {
		boolean ans = false;

		if (currWord.contains(DATE_SEPARATOR)) { // Date is 11/2/2016
			ans = true;
		} else {
			int numEntries = 0;
			for (int j = i; j < splitInput.length; j++) {
				if (numEntries > 2) {
					break;
				} else {
					String splitWord = splitInput[j]; // splitWord is "feb" of
														// "feb 2015"
					if (!isNumber(splitWord) && isMonth(splitWord)) {
						ans = true;
						break;
					}
				}
				numEntries++;
			}
		}
		return ans;
	}

	private boolean isMonth(String nextWord) {
		boolean ans = false;
		for (int i = 1; i < monthsInYear.length; i++) {
			if (nextWord.contains(monthsInYear[i])) {
				ans = true;
				break;
			}
		}
		return ans;
	}

	private boolean isNumber(String currWord) {
		return currWord.matches("[0-9]+");
	}

	private boolean isToday(String currWord) {
		return currWord.equals("today") || currWord.equals("tdy");
	}

	private int convertMonthStrToInt(String mm) {
		int monthInt = -1;
		for (int j = 1; j < monthsInYear.length; j++) {
			if (mm.contains(monthsInYear[j])) {
				monthInt = j;
				break;
			}
		}
		return monthInt;
	}

	private int getNextDayInt() {
		int temp = currDayInWeekInt + NEXT_DAY;
		temp %= 7;
		return temp;
	}

	private boolean hasPassed(int currTime2, int time, int[] date) {
		return currTime2 > time && date[DATE_INDEX_OF_DD] == currDD && date[DATE_INDEX_OF_MM] == currMM
				&& date[DATE_INDEX_OF_YY] == currYY && time != -1;
	}

	// Input number of days to fastforward from today
	// Output the day of the week
	private int get_Int_Day_From(int daysLater) {
		int dayOfWeek = daysLater + currDayInWeekInt;
		dayOfWeek %= 7;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	// Input desired day of the week in int
	// Output days to fastForward from today
	private int getFastForward(int desiredDay) {
		int fastForward = 0;
		if (currDayInWeekInt > desiredDay) {
			fastForward = NUM_DAYS_IN_WEEK - currDayInWeekInt + desiredDay;
		} else if (currDayInWeekInt < desiredDay) {
			fastForward = desiredDay - currDayInWeekInt;
		} else { // if(currDay == day)
			fastForward = 0;
		}
		return fastForward;
	}

	private int[] getDate(int fastForward) {
		int newDay = currDD + fastForward;
		int newMonth = currMM;
		int newYear = currYY;

		while (newDay > daysInMonth[newMonth]) {
			newDay -= daysInMonth[newMonth];
			newMonth += 1;
		}

		if (newMonth > 12) {
			newMonth = 1;
			newYear = incrementByOne(newYear);
		}

		int[] ans = new int[] { newDay, newMonth, newYear };
		return ans;
	}

	private int[] getTime(String[] splitInput, int i, int[] combined) throws IncorrectInputException {
		String timeString = splitInput[i].replace(".", "").replace(":", "");
		boolean isAm = timeString.contains(AM);
		boolean isPm = timeString.contains(PM);
		int timeInt = UNINITIALIZED_INT;
		boolean isExactlyMidnight = timeString.equals("12am");

		if (isAm && isPm) {
			throw new IncorrectInputException("\"" + timeString + "\"" + " cannot be am and pm!");
		}

		if (isAm ^ isPm) {
			timeString = timeString.replace(AM, "").trim();
			timeString = timeString.replace(PM, "").trim();
		}

		timeInt = Integer.parseInt(timeString);

		if (timeInt % 100 > 59) {
			throw new IncorrectInputException("Invalid time! Minutes must be greater than 0 and smaller than 60");
		}

		if (isMidnight(timeInt, timeString, isAm) || isExactlyMidnight) {
			if (isExactlyMidnight) {
				timeInt = 0;
			} else {
				timeInt = timeInt % 100;
			}
		} else if (timeInt < 100) { // this is for the 12h format
			timeInt = timeInt * 100;
		}

		if (isPm) {
			timeInt = timeInt + TWELVE_HOURS;
		}

		if (incrementByOne(i) < splitInput.length) {
			String indicator = splitInput[incrementByOne(i)];
			if (indicator.equals(PM)) {
				timeInt = timeInt + TWELVE_HOURS;
			}
			if (timeInt == 1200 && indicator.equals(AM)) {
				timeInt = 0;
			}
		}
		if (timeInt >= 2400) {
			throw new IncorrectInputException("Invalid Time! Time must not exceed 24 hours!");
		}

		if (hasAWordAfterCurrWord(splitInput, i)) {
			if (splitInput[incrementByOne(i)].equals(PM) || splitInput[incrementByOne(i)].equals(AM)) {
				i += 1; // To skip the next word
			}
		}
		combined[COMBINED_INDEX_TIME] = timeInt;
		combined[COMBINED_INDEX_COUNTER] = i;
		return combined;
	}

	private boolean isMidnight(int timeInt, String timeString, boolean isAm) {
		boolean isMidnight24H = timeInt >= 0 && timeInt <= 59 && timeString.contains("00");
		boolean isMidnight12H = timeInt >= 1200 && timeInt <= 1259 && isAm;
		return isMidnight24H || isMidnight12H;
	}

	private int convertDayStrToInt(String string) {
		int day = -1;
		for (int i = 0; i < daysInWeek.length; i++) {
			if (string.contains(daysInWeek[i])) {
				day = i;
				break;
			}
		}
		return day;
	}
}
